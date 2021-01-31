package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.processor.command.impl.GetInfoCommandProcessor;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.time.LocalDateTime;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class LvivHotlineIssuesScheduler {

    private static final String CLEANUP_CRON = "0 0 6 * * MON";

    private final InMemoryDao inMemoryDao;
    private final LvivHotlineBot lvivHotlineBot;
    private final GetInfoCommandProcessor processor;

    @Scheduled(fixedDelay = "60m")
    void parseAndSend() {
        log.info("Starting 1580 crawling job...");
        processor.process(lvivHotlineBot::send);
        log.info("Crawling job has finished");
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
