package com.vasylbhd.bot.core.processor.command.impl;

import com.vasylbhd.bot.core.processor.command.CommandProcessor;
import com.vasylbhd.bot.core.processor.command.Command;
import lombok.NoArgsConstructor;

import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
@NoArgsConstructor
public class DefaultCommandProcessor extends CommandProcessor {

    @Override
    public Command getCommandName() {
        return Command.DEFAULT_COMMAND;
    }

    @Override
    protected void processCommand(Consumer<String> execute) {
        execute.accept("Why are we still here? Just to suffer?");
    }
}
