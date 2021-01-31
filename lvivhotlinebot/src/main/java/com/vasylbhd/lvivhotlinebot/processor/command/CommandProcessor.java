package com.vasylbhd.lvivhotlinebot.processor.command;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

public abstract class CommandProcessor implements Processor {
    @Value("${telegram.bot.chat-id}")
    protected Long chatId;

    public abstract Command getCommand();

    protected abstract void process(Consumer<String> execute);

    public void process(Message message, Consumer<? super BotApiMethod<Message>> action) {
        if (isChatIdAcceptable(message.getChatId()) && message.isCommand()) {
            Command executedCommand = Command.fromString(message.getText())
                    .orElse(Command.DEFAULT_COMMAND);
            if (executedCommand == getCommand()) {
                process(msg -> action.accept(new SendMessage(message.getChatId().toString(), msg)));
            }
        }
    }

    protected boolean isChatIdAcceptable(Long chatId) {
        return this.chatId.equals(chatId);
    }
}
