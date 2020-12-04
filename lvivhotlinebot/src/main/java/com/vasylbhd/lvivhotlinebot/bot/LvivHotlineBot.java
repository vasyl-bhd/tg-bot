package com.vasylbhd.lvivhotlinebot.bot;

import com.vasylbhd.lvivhotlinebot.config.TelegramBotConfigurationProperties;
import com.vasylbhd.lvivhotlinebot.processor.Processor;
import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

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
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        log.debug("{}", update);

        if (update.hasMessage()) {
            setTyping(update.getMessage().getChatId());
            Message message = update.getMessage();

            processors.forEach(processor ->
                    processor.process(message, this::send));
        }
    }

    public void send(String message) {
        send(new SendMessage(properties.getChatId().toString(), message));
    }

    @SneakyThrows
    private void send(BotApiMethod<Message> message) {
        execute(message);
    }

    @SneakyThrows
    private void setTyping(Long chatId) {
        execute(new SendChatAction(Long.toString(chatId), ActionType.TYPING.toString()));
    }

    public void onRegister() {
        super.onRegister();

        //send(String.format("Started on: %s", LocalDateTime.now()));
    }
}
