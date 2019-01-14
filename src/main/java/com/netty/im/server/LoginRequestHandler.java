package com.netty.im.server;

import com.netty.im.auth.Session;
import com.netty.im.common.LoginRequestPacket;
import com.netty.im.common.LoginResponsePacket;
import com.netty.im.util.LoginUtil;
import com.netty.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by timma on 2019/1/3.
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    public void channelRead0(ChannelHandlerContext cht, LoginRequestPacket loginRequestPacket) {


        //返回结果
        LoginResponsePacket responsePacket = new LoginResponsePacket();


        if (null != loginRequestPacket) {

            responsePacket.setVersion(loginRequestPacket.getVersion());

            //登录校验
            if (LoginUtil.isValid(loginRequestPacket)) {

                System.out.println("登录成功！");
                responsePacket.setSucess(true);

                String userId = LoginUtil.randomUserId(loginRequestPacket.getUserName());
                responsePacket.setUserId(userId);
                responsePacket.setUserName(loginRequestPacket.getUserName());

                SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), cht.channel());

            } else {

                responsePacket.setSucess(false);
                responsePacket.setReason("校验账号密码失败");

            }
        }

        //返回
        cht.channel().writeAndFlush(responsePacket);
    }
}
