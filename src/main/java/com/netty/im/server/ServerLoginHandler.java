package com.netty.im.server;

import com.netty.im.codec.PacketCodeC;
import com.netty.im.common.LoginRequestPacket;
import com.netty.im.common.LoginResponsePacket;
import com.netty.im.common.Packet;
import com.netty.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by timma on 2018/12/10.
 */
public class ServerLoginHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("服务端获取用户登录信息成功");

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        //返回结果
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());


        if (null != packet
                && packet instanceof LoginRequestPacket) {

            //登录校验
            if (LoginUtil.isValid((LoginRequestPacket) packet)) {

                responsePacket.setSucess(true);
            } else {

                responsePacket.setSucess(false);
                responsePacket.setReason("校验账号密码失败");

            }
        }

        //编码
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);

        ctx.channel().writeAndFlush(responseByteBuf);
    }

}
