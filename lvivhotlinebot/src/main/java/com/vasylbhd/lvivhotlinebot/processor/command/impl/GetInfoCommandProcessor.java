package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import model.Action;
import org.springframework.stereotype.Service;
import parser.LvivHotlineIssuesParserImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class GetInfoCommandProcessor extends CommandProcessor {

    @Override
    public Command getCommand() {
        return Command.GET_INFO;
    }

    @Override
    public void process(Consumer<String> execute) {
        List<String> messages = new LvivHotlineIssuesParserImpl()
                .parse(LocalDate.now(), LocalDate.now().plus(1, DAYS))
                .stream()
                .map(LvivHotlineResponse::fromAction)
                .map(LvivHotlineResponse::toTelegramResponse)
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            execute.accept("No new issues with water");
            return;
        }

        messages.forEach(execute);
    }
}
