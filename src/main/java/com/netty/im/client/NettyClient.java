package com.netty.im.client;

import com.netty.im.codec.PacketCodeC;
import com.netty.im.codec.PacketDecoder;
import com.netty.im.codec.PacketEncoder;
import com.netty.im.common.LoginRequestPacket;
import com.netty.im.common.MessageRequestPacket;
import com.netty.im.util.LoginUtil;
import com.netty.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by timma on 2018/11/21.
 */
public class NettyClient {

    public static final int MAX_RETRY = 10;

    public static void main(String[] args) throws InterruptedException{
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                .channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline()
//                                .addLast(new StringEncoder())
//                                .addLast(new ClientCommunicationHandler());

                        .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });


        connect(bootstrap, "127.0.0.1", 1024, 10);


    }


    public static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接成功！");

                    Channel channel = ((ChannelFuture)future).channel();

                    startConsoleThread(channel);

                } else if (retry == 0){
                    System.out.println("重试次数已经用完，放弃连接！");
                } else {
                    int order = (MAX_RETRY - retry) + 1;
                    int delay = 1 << order;

                    System.err.println(new Date() + "：连接失败，第"+order+"次重试...");


                    //schedule设置定时任务
                    bootstrap.config().group().schedule(new Runnable() {
                        @Override
                        public void run() {
                            connect(bootstrap, host, port, retry - 1);
                        }
                    }, delay, TimeUnit.SECONDS);
                }
            }
        });
    }


    public static void startConsoleThread(final Channel channel) {

        final Scanner scanner = new Scanner(System.in);
        final LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (!SessionUtil.hasLogin(channel)) {
                        System.out.print("输入用户名登录：");

                        String userName = scanner.nextLine();


                        loginRequestPacket.setUserName(userName);
                        loginRequestPacket.setPassword("123456");

                        channel.writeAndFlush(loginRequestPacket);

                        waitForLoginResponse();
                    } else {

                        System.out.print("输入发送对象姓名：");
                        String userName = scanner.next();
                        System.out.print("输入消息：");
                        String message = scanner.next();
                        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                        messageRequestPacket.setMessage(message);
                        messageRequestPacket.setToUserId(LoginUtil.randomUserId(userName));
                        messageRequestPacket.setToUserName(userName);

                        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), messageRequestPacket);

                        channel.writeAndFlush(byteBuf);
                    }
                }
            }
        }).start();
    }

    public static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
