package com.netty.im.util;

import com.netty.im.common.Attributes;
import com.netty.im.common.LoginRequestPacket;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * Created by timma on 2018/12/11.
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {

        channel.attr(Attributes.LOGIN).set(true);
    }


    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        if (null != loginAttr && loginAttr.get() != null) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isValid(LoginRequestPacket loginRequestPacket) {

        if (null == loginRequestPacket
                || null == loginRequestPacket.getUserName()
                || null == loginRequestPacket.getPassword()) {
            return false;
        }

        if ("123456".equals(loginRequestPacket.getPassword())) {
            return true;
        }

        return false;
    }


    public static String randomUserId(String userName) {
        return userName;
    }
}
