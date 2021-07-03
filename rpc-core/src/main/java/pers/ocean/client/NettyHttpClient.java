package pers.ocean.client;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author : ocean_wll
 * @since 2021/7/3
 */
public class NettyHttpClient {

    /**
     * 发起http请求
     *
     * @param url 请求的url路径
     * @return 请求结果
     */
    public Object sendRequest(String url, Object param) throws InterruptedException,
            MalformedURLException {
        // 首先，netty通过ServerBootstrap启动服务端
        Bootstrap client = new Bootstrap();

        //第1步 定义线程组，处理读写和链接事件，没有了accept事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        //通道是NioSocketChannel
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //字符串编码器，一定要加在SimpleClientHandler 的上面
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(
                        Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                //找到他的管道 增加他的handler
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });
        URL url1 = new URL(url);
        //连接服务器
        ChannelFuture future = client.connect(url1.getHost(), url1.getPort()).sync();

        future.channel().writeAndFlush(JSON.toJSONString(param) + "\r\n");

        //当通道关闭了，就继续往下走
        future.channel().closeFuture().sync();

        //接收服务端返回的数据
        AttributeKey<String> key = AttributeKey.valueOf("ServerData");
        return future.channel().attr(key).get();
    }
}

class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            String value = ((ByteBuf) msg).toString(Charset.defaultCharset());
            AttributeKey<String> key = AttributeKey.valueOf("ServerData");
            ctx.channel().attr(key).set(value);
        }

        //把客户端的通道关闭
        ctx.channel().close();
    }
}
