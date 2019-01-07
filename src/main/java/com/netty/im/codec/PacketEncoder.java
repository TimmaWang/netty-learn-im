package com.netty.im.codec;

import com.netty.im.common.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by timma on 2019/1/3.
 */
public class PacketEncoder extends MessageToByteEncoder<Packet>{

    @Override
    public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {

        PacketCodeC.INSTANCE.encode(out, packet);
    }
}
