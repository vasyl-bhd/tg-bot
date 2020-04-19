package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import model.Action;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import parser.LvivHotlineIssuesParser;
import parser.LvivHotlineIssuesParserImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LvivHotlineIssuesScheduler {

    private final InMemoryDao inMemoryDao;
    private final LvivHotlineBot lvivHotlineBot;


    public LvivHotlineIssuesScheduler(
            InMemoryDao inMemoryDao,
            LvivHotlineBot lvivHotlineBot) {
        this.inMemoryDao = inMemoryDao;
        this.lvivHotlineBot = lvivHotlineBot;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    void parseAndSend() {
        LvivHotlineIssuesParser parser = new LvivHotlineIssuesParserImpl();
        List<Action> parse = parser.parse(LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.DAYS));
        parse
                .stream()
                .filter(i -> !inMemoryDao.contains(i.getId()))
                .map(LvivHotlineResponse::fromAction)
                .map(LvivHotlineResponse::toTelegramResponse)
                .forEach(lvivHotlineBot::sendMessage);
    }

    @Scheduled(fixedDelay = 65 * 60 * 1000)
    void cleanUpDb() {
        inMemoryDao.getAll().entrySet()
                .removeIf(entry -> entry.getValue().isAfter(LocalDateTime.now()));
    }
}
