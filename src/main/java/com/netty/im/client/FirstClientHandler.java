package com.netty.im.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by timma on 2018/12/7.
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println(new Date() + "客户端写出数据");

        //1.获取数据
        ByteBuf buffer = getByteBuf(ctx);

        //2.写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf)msg;

        System.out.println(new Date() + "客户端读到数据："+byteBuf.toString(Charset.forName("utf-8")));
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        //1.获取二进制抽象的ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        //2.准备数据，设置字符串的字符集为utf-8
        byte[] bytes = "你好，netty server".getBytes(Charset.forName("utf-8"));

        //3.将数据填充到ByteBuf
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
