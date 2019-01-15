package com.netty.im.client;

import com.netty.im.common.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by timma on 2019/1/15.
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        if (null != msg && msg.isSuccess()) {

            System.out.print("群创建成功，id 为[" + msg.getGroupId() + "], ");
            System.out.println("群里面有：" + msg.getUserNameList());

        } else {
            System.out.println("创建群聊失败");
        }
    }
}
