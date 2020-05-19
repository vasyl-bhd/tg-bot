package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
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
public class LvivHotlineIssuesScheduler {

    private static final int CLEAN_UP_DELAY = 3900000;
    private static final long PARSING_DELAY = 3600000;

    private final InMemoryDao inMemoryDao;
    private final LvivHotlineBot lvivHotlineBot;

    public LvivHotlineIssuesScheduler(
            InMemoryDao inMemoryDao,
            LvivHotlineBot lvivHotlineBot) {
        this.inMemoryDao = inMemoryDao;
        this.lvivHotlineBot = lvivHotlineBot;
    }

    @Scheduled(fixedDelay = PARSING_DELAY)
    void parseAndSend() {
        new LvivHotlineIssuesParserImpl()
                .parse(LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS))
                .stream()
                .filter(this::containAction)
                .map(LvivHotlineResponse::fromAction)
                .map(LvivHotlineResponse::toTelegramResponse)
                .forEach(lvivHotlineBot::sendMessage);
    }

    private boolean containAction(Action action) {
        String actionId = action.getId();
        boolean contains = inMemoryDao.contains(actionId);
        if (!contains) {
            inMemoryDao.save(action);
        }
        log.info("Saved id {}", actionId);
        return contains;
    }

    @Scheduled(fixedDelay = CLEAN_UP_DELAY)
    void cleanUpDb() {
        boolean removed = inMemoryDao.getAll().entrySet()
                .removeIf(entry -> LocalDateTime.now().isAfter(entry.getValue()));
        log.info("Is there were removed items: {}", removed);

    }
}
