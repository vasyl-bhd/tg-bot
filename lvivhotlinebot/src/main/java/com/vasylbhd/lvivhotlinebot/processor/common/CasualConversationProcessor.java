package com.vasylbhd.lvivhotlinebot.processor.common;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

@Service
public class CasualConversationProcessor implements Processor {

    @Override
    public void process(Message text, Consumer<? super BotApiMethod<Message>> action) {

    }
}
