package com.netty.im.common;

import static com.netty.im.common.Command.LOGIN_REQUEST;
import static com.netty.im.common.Command.LOGIN_RESPONSE;

/**
 * Created by timma on 2018/12/10.
 */
public class LoginResponsePacket extends Packet{

    private boolean sucess;

    private String reason;

    private String userId;

    private String userName;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;

    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
