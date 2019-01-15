package com.netty.im.manager;

import com.netty.im.command.ConsoleCommand;
import com.netty.im.command.CreateGroupConsoleCommand;
import com.netty.im.command.LogoutConsoleCommand;
import com.netty.im.command.SendToUserConsoleCommand;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by timma on 2019/1/14.
 */
public class ConsoleCommandManager implements ConsoleCommand{

    private static Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();

    public ConsoleCommandManager() {
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());

    }

    @Override
    public void exec(Scanner scanner, Channel channel) {

        //  获取第一个指令
        System.out.print("请输入指令（sendToUser,logout,createGroup）：");
        String command = scanner.next();

        ConsoleCommand consoleCommand =  consoleCommandMap.get(command);

        if (null != consoleCommand) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
