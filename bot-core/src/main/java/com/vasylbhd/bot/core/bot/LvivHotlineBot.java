package com.vasylbhd.bot.core.bot;

import com.vasylbhd.bot.core.config.TelegramBotConfigurationProperties;
import com.vasylbhd.bot.core.processor.Processor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
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
        if (update.hasMessage()) {

            Message message = update.getMessage();
            setTyping(message.getChatId());

            processors.forEach(processor -> processor.processMessage(message, this::send));
        }
    }

    public void send(String message) {
        send(new SendMessage(properties.getChatId().toString(), message));
    }

    @SneakyThrows
    private void send(BotApiMethod<? extends BotApiObject> message) {
        execute(message);
    }

    @SneakyThrows
    private void setTyping(Long chatId) {
        execute(new SendChatAction(Long.toString(chatId), ActionType.TYPING.toString()));
    }

    public void onRegister() {
        super.onRegister();

        send(String.format("Started on: %s", LocalDateTime.now()));
    }
}
