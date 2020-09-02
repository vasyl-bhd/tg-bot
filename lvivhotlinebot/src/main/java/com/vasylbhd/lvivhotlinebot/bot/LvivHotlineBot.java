package com.vasylbhd.lvivhotlinebot.bot;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${telegram.bot.token}")
    protected String token;
    @Value("${telegram.bot.chatid}")
    protected Long chatId;
    @Value("${telegram.bot.username}")
    protected String username;

    private final List<Processor> processors;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("{}", update);

        if (update.hasMessage()) {
            setTyping(update.getMessage().getChatId());
            processors.forEach(processor -> process(processor, update.getMessage()));
        }
    }

    @SneakyThrows
    public void sendMessage(String message) {
        execute(new SendMessage(chatId, message));
    }

    private void process(Processor processor, Message message) {
        Long chatId = message.getChatId();
        processor.process(message, processedMessage -> executeMessageSending(chatId, processedMessage));
    }

    @SneakyThrows
    private void executeMessageSending(Long chatId, String message) {
        super.execute(new SendMessage(chatId, message));
    }

    @SneakyThrows
    private void setTyping(Long chatId) {
        super.execute(new SendChatAction(chatId, ActionType.TYPING.toString()));
    }

    @PostConstruct
    private void onBeanCreate() {
        sendMessage(String.format("Started on: %s", LocalDateTime.now()));
    }
}
