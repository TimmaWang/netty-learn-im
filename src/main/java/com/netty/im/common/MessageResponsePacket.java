package com.netty.im.common;

import static com.netty.im.common.Command.MESSAGE_RESPONSE;

/**
 * Created by timma on 2018/12/11.
 */
public class MessageResponsePacket extends Packet{

    private String message;

    private String fromUserId;

    private String fromUserName;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
}
