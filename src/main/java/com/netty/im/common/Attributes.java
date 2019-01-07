package com.netty.im.common;

import io.netty.util.AttributeKey;

/**
 * Created by timma on 2018/12/11.
 */
public interface Attributes {

    public static final AttributeKey<Boolean> LOGIN = AttributeKey.valueOf("login");
}
