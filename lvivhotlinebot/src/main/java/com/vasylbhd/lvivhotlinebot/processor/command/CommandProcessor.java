package com.vasylbhd.lvivhotlinebot.processor.command;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

public abstract class CommandProcessor implements Processor {
    @Value("${telegram.bot.chatid}")
    protected Long chatId;

    public abstract Command getCommand();

    protected abstract void process(Consumer<String> execute);

    @Override
    public void process(Message text, Consumer<String> execute) {
        if (isChatIdAcceptable(text.getChatId()) && text.isCommand()) {
            Command executedCommand = Command.fromString(text.getText())
                    .orElse(Command.DEFAULT_COMMAND);
            if (executedCommand == getCommand()) {
                process(execute);
            }
        }
    }

    protected boolean isChatIdAcceptable(Long chatId) {
        return this.chatId.equals(chatId);
    }
}
