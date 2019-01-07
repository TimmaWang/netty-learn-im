package com.netty.im.client;

import com.netty.im.codec.PacketCodeC;
import com.netty.im.common.LoginRequestPacket;
import com.netty.im.common.LoginResponsePacket;
import com.netty.im.util.LoginUtil;
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
        System.out.println(new Date() + " 客户端开始登陆");

        //创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("timma");
        loginRequestPacket.setPassword("123456");

        //写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {

        System.out.println("客户端根据服务端的返回确认下自己有没有登录成功");

        if (responsePacket.isSucess()) {

            LoginUtil.markAsLogin(ctx.channel());

            System.out.println(new Date() + ": 客户端登录成功");
        }  else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + responsePacket.getReason());
        }
    }
}
