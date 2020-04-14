package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
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
