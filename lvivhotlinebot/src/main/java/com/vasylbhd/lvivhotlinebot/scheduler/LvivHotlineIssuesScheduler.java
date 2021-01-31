package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Action;
import parser.LvivHotlineIssuesParserImpl;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class LvivHotlineIssuesScheduler {

    private static final String CLEANUP_CRON = "0 0 6 * * MON";

    private final InMemoryDao inMemoryDao;
    private final LvivHotlineBot lvivHotlineBot;

    @Scheduled(fixedDelay ="60m")
    void parseAndSend() {
        log.info("Starting 1580 crawling job...");
        try {
            new LvivHotlineIssuesParserImpl()
                    .parse(LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS))
                    .stream()
                    .filter(this::notContainsAction)
                    .map(LvivHotlineResponse::fromAction)
                    .map(LvivHotlineResponse::toTelegramResponse)
                    .forEach(lvivHotlineBot::send);
        } catch (Exception e) {
            lvivHotlineBot.send("Error while parsing 1580: " + e.getMessage());
        } finally {
            log.info("Crawling job has finished");
        }
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

    @Scheduled(cron = CLEANUP_CRON)
    void cleanUpDb() {
        log.info("Starting cleaning up job. About to remove {} records",
                inMemoryDao.getAll().size());
        boolean removed = inMemoryDao.getAll().entrySet()
                .removeIf(entry -> LocalDateTime.now().isAfter(entry.getValue()));
        log.info("Is there were removed items: {}", removed);
    }
}
