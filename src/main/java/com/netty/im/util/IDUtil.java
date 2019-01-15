package com.netty.im.util;

import java.util.Random;

/**
 * Created by timma on 2019/1/15.
 */
public class IDUtil {

    public static String randomId() {

        Random random = new Random(100000);
        return String.valueOf(random.nextInt(10000));
    }
}
