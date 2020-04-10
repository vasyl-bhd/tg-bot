package com.vasylbhd.command.impl;

import com.vasylbhd.command.Command;
import com.vasylbhd.command.CommandProcessor;
import com.vasylbhd.model.ResponseMessage;
import model.Action;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import parser.LvivHotlineIssuesParserImpl;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Singleton
public class GetInfoCommandProcessor implements CommandProcessor {

    @ConfigProperty(name = "telegram.bot.chatid")
    String chatId;

    @Override
    public List<String> process() {
        List<Action> parse = new LvivHotlineIssuesParserImpl()
                .parse(LocalDate.now(), LocalDate.now().plus(1, DAYS));
        List<String> messages = parse.stream()
                .map(ResponseMessage::fromAction)
                .map(ResponseMessage::toTelegramResponse)
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            return Collections.singletonList("No new issues with water");
        }
        return messages;
    }

    @Override
    public Command getCommand() {
        return Command.GET_INFO;
    }
}
