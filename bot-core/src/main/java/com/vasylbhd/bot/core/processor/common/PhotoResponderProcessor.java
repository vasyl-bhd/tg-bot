package com.vasylbhd.bot.core.processor.common;

import com.vasylbhd.bot.core.processor.Processor;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Singleton;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Singleton
@Slf4j
public class PhotoResponderProcessor implements Processor {

    @Value("${telegram.bot.chat-id}")
    protected Long chatId;

    private static final List<String> phrases = List.of(
            "Amazing photo!",
            "WOW!",
            "No way you send me this!",
            "I mean...thanks?",
            "Йосі досі.",
            "Soo cool!",
            "Now that's getting weird..",
            "What a beautiful photo!",
            "私は死にたい",
            "Did you know that gods of death love apples?",
            "The cake is a lie",
            "ТА ШО ТИ ДЄЛАЇШ ЗІ МНОУ!");

    @Override
    public void processMessage(Message message, Consumer<? super BotApiMethod<Message>> action) {
        if (message.hasPhoto() && !chatId.equals(message.getChatId())) {
            action.accept(new SendMessage(message.getChatId().toString(), getRandomElementFromList()));
            action.accept(new ForwardMessage(chatId.toString(), message.getChatId().toString(), message.getMessageId()));

            log.info("Message received from: {}", message.getFrom().getUserName());
        }
    }

    private static String getRandomElementFromList() {
        int index = new Random().nextInt(phrases.size());

        return phrases.get(index);
    }
}
