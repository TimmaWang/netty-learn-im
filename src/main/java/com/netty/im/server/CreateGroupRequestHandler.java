package com.netty.im.server;

import com.netty.im.common.CreateGroupRequestPacket;
import com.netty.im.common.CreateGroupResponsePacket;
import com.netty.im.util.IDUtil;
import com.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timma on 2019/1/14.
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) throws Exception {

        List<String> userIdList = requestPacket.getUserIdList();
        List<String> userNameList = new ArrayList<>();

        //创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        //筛选出用户的channel和用户姓名信息
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (null != channel) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 创建群聊结果的响应
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setGroupId(IDUtil.randomId());
        responsePacket.setUserNameList(userNameList);
        responsePacket.setSuccess(true);

        //将结果发给每个群聊里面的客户端
        channelGroup.writeAndFlush(responsePacket);

        System.out.print("群创建成功，id 为[" + responsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + responsePacket.getUserNameList());

    }
}
