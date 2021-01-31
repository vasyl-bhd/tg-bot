package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
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
