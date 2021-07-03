package pers.ocean.rpc.demo.provider.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import pers.ocean.api.RpcRequest;
import pers.ocean.api.RpcResponse;
import pers.ocean.server.RpcInvoker;

import java.nio.charset.Charset;

/**
 * netty服务端代码
 *
 * @author : ocean_wll
 * @since 2021/7/3
 */
public class NettyServer {

    private RpcInvoker rpcInvoker;

    public NettyServer(RpcInvoker rpcInvoker) {
        this.rpcInvoker = rpcInvoker;
    }

    public void run() throws InterruptedException {
        // 首先，netty通过ServerBootstrap启动服务端
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        //第1步定义两个线程组，用来处理客户端通道的accept和读写事件
        //parentGroup用来处理accept事件，childgroup用来处理通道的读写事件
        //parentGroup获取客户端连接，连接接收到之后再将连接转发给childgroup去处理
        server.group(parentGroup, childGroup);

        //用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
        //用来初始化服务端可连接队列
        //服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
        server.option(ChannelOption.SO_BACKLOG, 128);

        //第2步绑定服务端通道
        server.channel(NioServerSocketChannel.class);

        //第3步绑定handler，处理读写事件，ChannelInitializer是给通道初始化
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //解码器，接收的数据进行解码，一定要加在SimpleServerHandler 的上面
                //maxFrameLength表示这一贞最大的大小
                //delimiter表示分隔符，我们需要先将分割符写入到ByteBuf中，然后当做参数传入；
                //需要注意的是，netty并没有提供一个DelimiterBasedFrameDecoder对应的编码器实现(笔者没有找到)，因此在发送端需要自行编码添加分隔符，如 \r \n分隔符
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                //把传过来的数据 转换成byteBuf
                ch.pipeline().addLast(new SimpleServerHandler(rpcInvoker));
            }
        });

        //第4步绑定8080端口
        ChannelFuture future = server.bind(9000).sync();
        //当通道关闭了，就继续往下走
        future.channel().closeFuture().sync();
    }

}

class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    private RpcInvoker rpcInvoker;

    public SimpleServerHandler(RpcInvoker rpcInvoker) {
        this.rpcInvoker = rpcInvoker;
    }

    /**
     * 读取客户端通道的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //可以在这里面写一套类似SpringMVC的框架
        //让SimpleServerHandler不跟任何业务有关，可以封装一套框架
        RpcResponse response = new RpcResponse();
        if (msg instanceof ByteBuf) {
            RpcRequest request = JSON.parseObject(((ByteBuf) msg).toString(Charset.defaultCharset()), RpcRequest.class);
            response = rpcInvoker.invoke(request);
        }

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(JSON.toJSONString(response).getBytes());
        ctx.channel().writeAndFlush(buf);

        // TODO ocean_wll 这里不是很清楚为什么必须加上 \r\n 才行，不加是不能通信的，网上的资料好像说加了这个才能去编码解码
        ByteBuf buf2 = Unpooled.buffer();
        buf2.writeBytes("\r\n".getBytes());
        ctx.channel().writeAndFlush(buf2);
    }

}
