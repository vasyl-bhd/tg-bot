package com.vasylbhd.bot.core.processor.command.impl;

import com.vasylbhd.bot.core.dao.TgMetadataDao;
import com.vasylbhd.bot.core.model.LvivHotlineResponse;
import com.vasylbhd.bot.core.processor.command.Command;
import com.vasylbhd.bot.core.processor.command.CommandProcessor;
import com.vasylbhd.model.Action;
import com.vasylbhd.parser.LvivHotlineIssuesParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class GetInfoCommandProcessor extends CommandProcessor {

    private final LvivHotlineIssuesParser parser;
    private final TgMetadataDao metadataDao;

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
        var actions = parser.parse(LocalDate.now().minus(1, YEARS), LocalDate.now().plus(2, DAYS));
        actions.forEach(this::upsertAction);

        List<String> messages = actions
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

    private void upsertAction(Action a) {
        if (!metadataDao.contains(a.id())) {
            metadataDao.save(a);
        }
    }

}
