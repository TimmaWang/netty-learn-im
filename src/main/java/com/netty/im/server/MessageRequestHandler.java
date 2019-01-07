package com.netty.im.server;

import com.netty.im.common.MessageRequestPacket;
import com.netty.im.common.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by timma on 2018/12/14.
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket) {

        //收到来自客户端的请求
        System.out.println("收到来自客户端的请求：" + requestPacket.getMessage());

        //发送信息给客户端
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("既然客户端给我传了信息，我也给你返回下，说明我收到了你的信息");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
