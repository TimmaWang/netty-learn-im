package com.netty.im.common;

import com.netty.im.command.Command;

import java.util.List;

/**
 * Created by timma on 2019/1/14.
 */
public class CreateGroupRequestPacket extends Packet{

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
