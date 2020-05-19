package com.vasylbhd.lvivhotlinebot.scheduler;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import com.vasylbhd.lvivhotlinebot.dao.InMemoryDao;
import com.vasylbhd.lvivhotlinebot.model.LvivHotlineResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import parser.LvivHotlineIssuesParserImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

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

    @Scheduled(fixedRate = 60 * 60 * 1000)
    void parseAndSend() {
        lvivHotlineBot.sendMessage("Last id: " + inMemoryDao.getAll());
        new LvivHotlineIssuesParserImpl()
                .parse(LocalDate.now(), LocalDate.now().plus(1, DAYS))
                .stream()
                .map(LvivHotlineResponse::fromAction)
                .map(LvivHotlineResponse::toTelegramResponse)
                .forEach(lvivHotlineBot::sendMessage);
    }

    @Scheduled(fixedRate = 65 * 60 * 1000)
    void cleanUpDb() {
        inMemoryDao.getAll().entrySet()
                .removeIf(entry -> entry.getValue().isAfter(LocalDateTime.now()));
    }
}
