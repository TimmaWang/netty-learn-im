package com.netty.im.common;

import com.netty.im.auth.Session;
import io.netty.util.AttributeKey;

/**
 * Created by timma on 2018/12/11.
 */
public interface Attributes {

    public static final AttributeKey<Boolean> LOGIN = AttributeKey.valueOf("login");

    public static final AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
