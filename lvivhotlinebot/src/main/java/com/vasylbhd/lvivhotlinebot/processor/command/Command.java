package com.vasylbhd.lvivhotlinebot.processor.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum Command {
    GET_INFO("getinfo"),
    START("start"),
    RANDOM_MEME("gimme"),
    DEFAULT_COMMAND("");

    Command(String value) {
        this.value = value;
    }

    private String value;
    private static Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap = stream(values()).collect(toMap(Command::getValue, Function.identity()));
    }

    public static Optional<Command> fromString(String telegramCommand) {
        return Optional.ofNullable(commandMap.get(telegramCommand.substring(1)));
    }

    private String getValue() {
        return value;
    }
}
