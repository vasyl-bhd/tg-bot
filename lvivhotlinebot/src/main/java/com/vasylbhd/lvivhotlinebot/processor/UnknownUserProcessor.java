package com.vasylbhd.lvivhotlinebot.processor;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.String.format;

public class UnknownUserProcessor implements Processor {

    @Value("${telegram.bot.chatid}")
    protected Long chatId;

    @Override
    public void process(Message text, Consumer<? super BotApiMethod<Message>> action) {
        if (!chatId.equals(text.getChatId())) {
            List<String> message = getMessage(text);
            message.forEach(msg -> action.accept(new SendMessage(text.getChatId().toString(), msg)));
        }
    }

    private List<String> getMessage(Message text) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(format("You are not Vasyl. And definitely not B. So %s, please go away!",
                text.getChat().getFirstName()));
        if (text.getChat().getFirstName().endsWith("B")) {
            strings.add("Ok, you are B, but it doesn't matter. You are not Vasyl!");
        }
        if (text.getChat().getFirstName().startsWith("Vasyl")) {
            strings.add("Ok, you ARE Vasyl...Now I'm confused.......");
        }
        return strings;
    }

}
