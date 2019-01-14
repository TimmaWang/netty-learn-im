package com.netty.im.server;

import com.netty.im.auth.Session;
import com.netty.im.common.MessageRequestPacket;
import com.netty.im.common.MessageResponsePacket;
import com.netty.im.util.LoginUtil;
import com.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by timma on 2018/12/14.
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket) {

        // 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        //根据消息发送方的会话信息，构造要发送的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(requestPacket.getMessage());

        //拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(requestPacket.getToUserId());
        if (null != toUserChannel && SessionUtil.hasLogin(toUserChannel)) {
            //将消息发送给消息接收方
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {

        }System.err.println("[" + requestPacket.getToUserId() + "] 不在线，发送失败!");


    }
}
