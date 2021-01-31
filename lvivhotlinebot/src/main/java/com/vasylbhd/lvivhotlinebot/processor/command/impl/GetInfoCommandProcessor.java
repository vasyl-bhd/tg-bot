package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import parser.LvivHotlineIssuesParser;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class GetInfoCommandProcessor extends CommandProcessor {

    private final LvivHotlineIssuesParser parser;
    private final InMemoryDao inMemoryDao;

    @Override
    public Command getCommandName() {
        return Command.GET_INFO;
    }

    @Override
    public void processCommand(Consumer<String> execute) {
        try {
            checkForIssues(execute);
        } catch (Exception e) {
            execute.accept("Error while parsing 1580: " + e.getMessage());

            log.error("", e);
        }
    }

    private void checkForIssues(Consumer<String> onIssue) {
        List<String> messages = parser.parse(LocalDate.now(), LocalDate.now().plus(1, DAYS))
                .stream()
                .map(LvivHotlineResponse::fromAction)
                .map(LvivHotlineResponse::toTelegramResponse)
                .collect(Collectors.toList());

        if (messages.isEmpty()) {
            onIssue.accept("No new issues with water");
            return;
        }

        messages.forEach(onIssue);
    }

}
