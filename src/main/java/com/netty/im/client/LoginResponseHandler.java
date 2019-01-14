package com.netty.im.client;

import com.netty.im.auth.Session;
import com.netty.im.codec.PacketCodeC;
import com.netty.im.common.LoginRequestPacket;
import com.netty.im.common.LoginResponsePacket;
import com.netty.im.util.LoginUtil;
import com.netty.im.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * Created by timma on 2018/12/14.
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {

        if (responsePacket.isSucess()) {

            SessionUtil.bindSession(new Session(responsePacket.getUserId(), responsePacket.getUserName()), ctx.channel());

            System.out.println(new Date() + ": 客户端登录成功");
        }  else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + responsePacket.getReason());
        }
    }
}
