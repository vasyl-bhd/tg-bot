package com.vasylbhd.lvivhotlinebot.processor.common;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class CasualConversationProcessor implements Processor {

    @Override
    public void process(Message text, Consumer<? super BotApiMethod<Message>> action) {

    }
}
