package com.vasylbhd.lvivhotlinebot;

import com.vasylbhd.lvivhotlinebot.bot.LvivHotlineBot;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.inject.Singleton;

@RequiredArgsConstructor
@Singleton
@Slf4j
public class ApplicationRunner implements ApplicationEventListener<ApplicationStartupEvent> {
    private final LvivHotlineBot bot;

    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        log.info("STARTER");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
