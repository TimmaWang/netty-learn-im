package com.netty.im.codec;

import com.netty.im.command.Command;
import com.netty.im.common.*;
import com.netty.im.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by timma on 2018/12/10.
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;


    public static final PacketCodeC INSTANCE = new PacketCodeC();


    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = Serializer.DEFAULT;
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {

        //1、创建byteBuf对象
        ByteBuf byteBuf = byteBufAllocator.buffer();

        //2、序列化
        // TODO: 2018/12/10 疑问，里面没有数据，为啥序列化了整个packet！
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3、实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }



    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {


        //2、序列化
        // TODO: 2018/12/10 疑问，里面没有数据，为啥序列化了整个packet！
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3、实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {

        //跳过魔数
        byteBuf.skipBytes(4);

        //跳过版本
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializeAlgorithom = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据包长度
        int dataLength = byteBuf.readInt();

        //真正的数据
        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithom);
        if (null != requestType && null != serializer) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }


    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
