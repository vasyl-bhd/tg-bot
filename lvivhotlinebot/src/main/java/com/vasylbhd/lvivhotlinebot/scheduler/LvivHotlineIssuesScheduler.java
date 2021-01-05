package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Action;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import parser.LvivHotlineIssuesParserImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class LvivHotlineIssuesScheduler {

    private static final String CLEANUP_CRON = "0 0 6 * * MON";
    private static final long PARSING_DELAY = 3600000;

    private final InMemoryDao inMemoryDao;
    private final LvivHotlineBot lvivHotlineBot;

    @Scheduled(fixedDelay = PARSING_DELAY)
    void parseAndSend() {
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
        boolean removed = inMemoryDao.getAll().entrySet()
                .removeIf(entry -> LocalDateTime.now().isAfter(entry.getValue()));
        log.info("Is there were removed items: {}", removed);
    }
}
