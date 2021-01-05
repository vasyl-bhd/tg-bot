package com.vasylbhd.lvivhotlinebot.processor.common;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

public class UnknownUserProcessor implements Processor {

    @Value("${telegram.bot.chatid}")
    protected Long chatId;

    @Override
    public void process(Message text, Consumer<? super BotApiMethod<Message>> action) {
        Long fromChatId = text.getChatId();
        if (text.hasText() && !this.chatId.equals(fromChatId)) {
            action.accept(new SendMessage(fromChatId.toString(), "You are not my master. Please go away!"));
        }
    }
}
