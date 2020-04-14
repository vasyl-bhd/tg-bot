package com.vasylbhd.lvivhotlinebot.command.impl;

import com.vasylbhd.lvivhotlinebot.command.Command;
import com.vasylbhd.lvivhotlinebot.command.CommandProcessor;
import com.vasylbhd.lvivhotlinebot.model.ResponseMessage;
import model.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import parser.LvivHotlineIssuesParserImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class GetInfoCommandProcessor extends CommandProcessor {

    @Value("${telegram.bot.chatid}")
    String chatId;

    @Override
    public Command getCommand() {
        return Command.GET_INFO;
    }

    @Override
    public void process(Consumer<String> execute) {
        List<Action> parse = new LvivHotlineIssuesParserImpl()
                .parse(LocalDate.now(), LocalDate.now().plus(1, DAYS));
        List<String> messages = parse.stream()
                .map(ResponseMessage::fromAction)
                .map(ResponseMessage::toTelegramResponse)
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            execute.accept("No new issues with water");
            return;
        }

        messages.forEach(execute);
    }
}
