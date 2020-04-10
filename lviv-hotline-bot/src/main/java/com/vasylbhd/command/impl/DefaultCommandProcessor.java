package com.vasylbhd.command.impl;

import com.vasylbhd.command.Command;
import com.vasylbhd.command.CommandProcessor;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
public class DefaultCommandProcessor implements CommandProcessor {
    @Override
    public List<String> process() {
        return Collections.singletonList("Why we are still here? Just to suffer?");
    }

    @Override
    public Command getCommand() {
        return Command.DEFAULT_COMMAND;
    }
}
