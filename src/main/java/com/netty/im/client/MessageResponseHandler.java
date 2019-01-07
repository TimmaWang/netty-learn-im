package com.netty.im.client;

import com.netty.im.common.MessageRequestPacket;
import com.netty.im.common.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by timma on 2018/12/14.
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) {

        //获取服务端的数据
        System.out.println("获取服务端的响应数据：" + responsePacket.getMessage());

    }
}
