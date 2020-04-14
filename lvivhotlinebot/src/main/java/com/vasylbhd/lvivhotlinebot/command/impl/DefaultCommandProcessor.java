package com.vasylbhd.lvivhotlinebot.command.impl;

import com.vasylbhd.lvivhotlinebot.command.Command;
import com.vasylbhd.lvivhotlinebot.command.CommandProcessor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class DefaultCommandProcessor extends CommandProcessor {

    @Override
    public Command getCommand() {
        return Command.DEFAULT_COMMAND;
    }

    @Override
    protected void process(Consumer<String> execute) {
        execute.accept("Why are we still here? Just to suffer?");
    }
}
