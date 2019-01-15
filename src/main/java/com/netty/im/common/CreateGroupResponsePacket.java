package com.netty.im.common;

import com.netty.im.command.Command;

import java.util.List;

/**
 * Created by timma on 2019/1/14.
 */
public class CreateGroupResponsePacket extends Packet{

    private String groupId; //群聊分组id

    private List<String> userNameList;

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
