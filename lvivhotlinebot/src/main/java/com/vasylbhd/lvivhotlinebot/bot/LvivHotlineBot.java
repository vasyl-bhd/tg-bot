package com.vasylbhd.lvivhotlinebot.bot;

import com.vasylbhd.lvivhotlinebot.config.TelegramBotConfigurationProperties;
import com.vasylbhd.lvivhotlinebot.processor.Processor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LvivHotlineBot extends TelegramLongPollingBot {

    private final TelegramBotConfigurationProperties properties;
    private final List<Processor> processors;

    @Override
    public String getBotUsername() {
        return properties.getBotName();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("{}", update);

        if (update.hasMessage()) {
            setTyping(update.getMessage().getChatId());
            processors.forEach(processor -> process(processor, update.getMessage()));
        }
    }

    public void send(String message) {
        send(properties.getChatId(), message);
    }

    private void process(Processor processor, Message message) {
        Long chatId = message.getChatId();
        processor.process(message, processedMessage -> send(chatId, processedMessage));
    }

    @SneakyThrows
    private void send(Long chatId, String message) {
        super.execute(new SendMessage(chatId, message));
    }

    @SneakyThrows
    private void setTyping(Long chatId) {
        super.execute(new SendChatAction(chatId, ActionType.TYPING.toString()));
    }

    @PostConstruct
    private void onBeanCreate() {
        send(String.format("Started on: %s", LocalDateTime.now()));
    }
}
