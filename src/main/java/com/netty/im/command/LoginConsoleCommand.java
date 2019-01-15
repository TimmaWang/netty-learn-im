package com.netty.im.command;

import com.netty.im.common.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by timma on 2019/1/14.
 */
public class LoginConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入用户名登录：");
        String name = scanner.next();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserName(name);
        loginRequestPacket.setPassword("123456");

        channel.writeAndFlush(loginRequestPacket);
    }
}
