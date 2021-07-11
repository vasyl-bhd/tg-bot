package com.vasylbhd.bot.core.processor.common;

import com.vasylbhd.bot.core.processor.Processor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class CasualConversationProcessor implements Processor {

    @Override
    public void processMessage(Message text, Consumer<? super BotApiMethod<Message>> action) {

    }
}
