package com.vasylbhd.lvivhotlinebot.bot;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class LvivHotlineBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    protected String token;
    @Value("${telegram.bot.chatid}")
    protected Long chatId;
    @Value("${telegram.bot.username}")
    protected String username;

    private final List<Processor> processors;

    public LvivHotlineBot(List<Processor> processors) {
        this.processors = processors;
    }

    @PostConstruct
    void onBeanCreate() {
        sendMessage(String.format("Started on: %s", LocalDateTime.now()));
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("{}", update);
        if (update.hasMessage()) {
            processors.forEach(processor ->
                    processor.process(update.getMessage(),
                    message -> doExecute(update.getMessage().getChatId(), message)));
        }
    }

    @SneakyThrows
    private void doExecute(Long chatId, String message) {
        super.execute(new SendMessage(chatId, message));
    }

    @SneakyThrows
    public void sendMessage(String message) {
        execute(new SendMessage(chatId, message));
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
