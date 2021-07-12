package com.vasylbhd.bot.core.scheduler;

import com.vasylbhd.bot.core.bot.LvivHotlineBot;
import com.vasylbhd.bot.core.dao.InMemoryDao;
import com.vasylbhd.bot.core.dao.TgMetadataDao;
import com.vasylbhd.bot.core.model.LvivHotlineResponse;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.vasylbhd.model.Action;
import com.vasylbhd.parser.LvivHotlineIssuesParser;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class LvivHotlineIssuesScheduler {

    private static final String CLEANUP_CRON = "0 0 5 ? * 2/7";

    private final LvivHotlineBot lvivHotlineBot;
    private final LvivHotlineIssuesParser parser;
    private final TgMetadataDao metadataDao;

    @Scheduled(fixedDelay = "30m")
    void parseAndSend() {
        log.info("Starting 1580 crawling job...");
        try {
            checkForIssues(lvivHotlineBot::send);
            log.info("Crawling job has finished");
        } catch (Exception e) {
            lvivHotlineBot.send("Error while parsing 1580: " + e.getMessage());

            log.error("", e);
        }
    }

    private boolean notContainsAction(Action action) {
        String actionId = action.id();
        boolean notContains = !metadataDao.contains(actionId);
        if (notContains) {
            log.info("Saved id {}", actionId);

            metadataDao.save(action);
        }
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
                metadataDao.getAll().size());
        var keysToDelete = metadataDao.getAll().entrySet()
                .stream()
                .filter(entry -> LocalDateTime.now().isAfter(entry.getValue()))
                .map(Map.Entry::getKey)
                .toList();

        long removed = 0;
        if (!keysToDelete.isEmpty()) {
            removed = metadataDao.remove(keysToDelete);
        }

        log.info("There were removed items: {}", removed);
    }
}
