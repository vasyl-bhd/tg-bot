package com.vasylbhd.lvivhotlinebot.command;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

public interface Processor {
   void process(Message text, Consumer<String> action);
}
