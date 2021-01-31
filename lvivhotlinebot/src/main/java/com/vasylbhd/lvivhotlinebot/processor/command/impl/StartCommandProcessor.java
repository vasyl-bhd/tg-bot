package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import lombok.NoArgsConstructor;

import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
@NoArgsConstructor
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
