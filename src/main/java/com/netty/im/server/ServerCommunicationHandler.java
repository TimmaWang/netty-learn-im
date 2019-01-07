package com.netty.im.server;

import com.netty.im.codec.PacketCodeC;
import com.netty.im.common.*;
import com.netty.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by timma on 2018/12/11.
 */
public class ServerCommunicationHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext chx, Object msg) {

        ByteBuf byteBuf = (ByteBuf)msg;

        //转码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {

            System.out.println("登录验证");

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());

            //登录校验
            if (LoginUtil.isValid((LoginRequestPacket) packet)) {

                responsePacket.setSucess(true);
            } else {

                responsePacket.setSucess(false);
                responsePacket.setReason("校验账号密码失败");

            }

            //编码
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(chx.alloc(), responsePacket);
            chx.channel().writeAndFlush(responseByteBuf);


        } else if (packet instanceof MessageRequestPacket) {

            String message = ((MessageRequestPacket) packet).getMessage();
            System.out.println("收到来自客户端的消息：" + message);


            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("回复客户端：服务端的回复" + message);

            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(chx.alloc(), messageResponsePacket);
            chx.channel().writeAndFlush(responseByteBuf);
        }

    }
}
