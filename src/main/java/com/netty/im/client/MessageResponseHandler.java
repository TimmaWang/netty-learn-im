package com.netty.im.client;

import com.netty.im.common.MessageRequestPacket;
import com.netty.im.common.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

/**
 * Created by timma on 2018/12/14.
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) {

        //获取发送端的数据
        String fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        String message = responsePacket.getMessage();

        System.out.println(fromUserName+"对我说：" + message);

    }
}
