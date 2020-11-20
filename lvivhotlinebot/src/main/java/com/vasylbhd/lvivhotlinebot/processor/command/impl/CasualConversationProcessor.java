package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

@Service
public class CasualConversationProcessor implements Processor {

    @Override
    public void process(Message text, Consumer<String> action) { }
}
