package com.vasylbhd.lvivhotlinebot.command.impl;

import com.vasylbhd.lvivhotlinebot.command.Command;
import com.vasylbhd.lvivhotlinebot.command.CommandProcessor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class StartCommandProcessor extends CommandProcessor {

    @Override
    public Command getCommand() {
        return Command.START;
    }

    @Override
    public void process(Consumer<String> execute) {
        execute.accept("I must serve my master. But not you. Fuck off");
    }
}
