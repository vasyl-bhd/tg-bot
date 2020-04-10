package com.vasylbhd.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.vasylbhd.command.Command;
import com.vasylbhd.command.CommandProcessor;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Singleton
@Slf4j
public class LvivHotlineBot {

    @ConfigProperty(name = "telegram.bot.token")
    String token;
    @ConfigProperty(name = "telegram.bot.chatid")
    Long chatId;

    private TelegramBot bot;
    private final Map<Command, CommandProcessor> commandProcessorMap;

    @Inject
    public LvivHotlineBot(Instance<CommandProcessor> commandProcessors) {
        commandProcessorMap = commandProcessors
                .stream()
                .collect(toMap(CommandProcessor::getCommand, Function.identity()));
    }

    void onBeanDestroy(@Observes ShutdownEvent shutdownEvent) {
        log.info("Shutting down...");
        bot.removeGetUpdatesListener();
    }

    void onBeanCreate(@Observes StartupEvent shutdownEvent) {
        bot = new TelegramBot(token);
        bot.setUpdatesListener(this::onSuccess, this::onFailure);
    }

    private int onSuccess(List<Update> updates) {
        log.debug("{}",updates);
        updates.stream()
                .filter(this::isSuperSecuredChatId)
                .forEach(this::processUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private boolean isSuperSecuredChatId(Update update) {
        return Optional.ofNullable(update.message())
                .map(message -> message.chat().id().equals(chatId))
                .orElse(false);
    }

    private void onFailure(TelegramException exception) {
        if (exception.response() != null) {
            // got bad response from telegram
            log.error("Code: {}, Error: {}", exception.response().errorCode(), exception.response().description());
        } else {
            // probably network error
           log.error("Non telegram error: {}", exception.getMessage(), exception);
        }
    }

    private void processUpdate(Update update) {
        Command command = Command.fromString(update.message().text())
                .orElse(Command.DEFAULT_COMMAND);
        List<String> process = commandProcessorMap.get(command).process();
        process.forEach(this::sendMessage);
    }

    public void sendMessage(String message) {
        bot.execute(new SendMessage(chatId, message));
    }
}
