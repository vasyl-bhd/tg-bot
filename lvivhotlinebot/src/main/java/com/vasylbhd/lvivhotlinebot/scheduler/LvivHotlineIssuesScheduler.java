package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Action;
import parser.LvivHotlineIssuesParser;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class LvivHotlineIssuesScheduler {

    private static final String CLEANUP_CRON = "0 0 6 * * MON";

    private final LvivHotlineBot lvivHotlineBot;
    private final LvivHotlineIssuesParser parser;
    private final InMemoryDao inMemoryDao;

    @Scheduled(fixedDelay = "60m")
    void parseAndSend() {
        log.info("Starting 1580 crawling job...");
        checkForIssues(lvivHotlineBot::send);
        log.info("Crawling job has finished");
    }

    private boolean notContainsAction(Action action) {
        String actionId = action.id();
        boolean notContains = !inMemoryDao.contains(actionId);
        if (notContains) {
            inMemoryDao.save(action);
        }
        log.info("Saved id {}", actionId);
        return notContains;
    }


    private void checkForIssues(Consumer<String> onIssue) {
        List<String> messages = parser.parse(LocalDate.now(), LocalDate.now().plus(1, DAYS))
                .stream()
                .filter(this::notContainsAction)
                .map(LvivHotlineResponse::fromAction)
                .map(LvivHotlineResponse::toTelegramResponse)
                .collect(Collectors.toList());

        messages.forEach(onIssue);
    }

    @Scheduled(cron = CLEANUP_CRON)
    void cleanUpDb() {
        log.info("Starting cleaning up job. About to remove {} records",
                inMemoryDao.getAll().size());
        boolean removed = inMemoryDao.getAll().entrySet()
                .removeIf(entry -> LocalDateTime.now().isAfter(entry.getValue()));
        log.info("Is there were removed items: {}", removed);
    }
}
