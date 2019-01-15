package com.netty.im.command;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by timma on 2019/1/14.
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);
}
