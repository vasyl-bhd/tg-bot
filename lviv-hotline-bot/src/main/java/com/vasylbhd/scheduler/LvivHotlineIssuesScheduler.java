package com.vasylbhd.scheduler;

import com.vasylbhd.bot.LvivHotlineBot;
import com.vasylbhd.dao.InMemoryDao;
import com.vasylbhd.model.ResponseMessage;
import io.quarkus.scheduler.Scheduled;
import model.Action;
import parser.LvivHotlineIssuesParser;
import parser.LvivHotlineIssuesParserImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Singleton
public class LvivHotlineIssuesScheduler {

    private final InMemoryDao inMemoryDao;
    private final LvivHotlineBot lvivHotlineBot;

    @Inject
    public LvivHotlineIssuesScheduler(
            InMemoryDao inMemoryDao,
            LvivHotlineBot lvivHotlineBot) {
        this.inMemoryDao = inMemoryDao;
        this.lvivHotlineBot = lvivHotlineBot;
    }

    @Scheduled(every = "60M")
    void parseAndSend() {
        LvivHotlineIssuesParser parser = new LvivHotlineIssuesParserImpl();
        List<Action> parse = parser.parse(LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        parse
                .stream()
                .filter(i -> !inMemoryDao.contains(i.getId()))
                .map(ResponseMessage::fromAction)
                .map(ResponseMessage::toTelegramResponse)
                .forEach(lvivHotlineBot::sendMessage);
    }

    @Scheduled(every = "65M")
    void cleanUpDb() {
        inMemoryDao.getAll().entrySet()
                .removeIf(entry -> entry.getValue().isAfter(LocalDateTime.now()));
    }
}
