package com.vasylbhd.lvivhotlinebot.processor.command;

import com.vasylbhd.lvivhotlinebot.processor.Processor;
import io.micronaut.context.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

public abstract class CommandProcessor implements Processor {
    @Value("${telegram.bot.chat-id}")
    protected Long chatId;

    public abstract Command getCommandName();

    protected abstract void processCommand(Consumer<String> execute);

    public void processMessage(Message message, Consumer<? super BotApiMethod<Message>> action) {
        if (isChatIdAcceptable(message.getChatId())
                && message.isCommand()
                && passedCommandIsTheOne(message)) {
            processCommand(cmdResponse -> sendMessageFromConsumer(message.getChatId().toString(), action, cmdResponse));
        }
    }

    private void sendMessageFromConsumer(String chatId, Consumer<? super BotApiMethod<Message>> action, String cmdResponse) {
        action.accept(new SendMessage(chatId, cmdResponse));
    }

    private boolean passedCommandIsTheOne(Message message) {
        return getCommandForExecution(message) == getCommandName();
    }

    private Command getCommandForExecution(Message message) {
        return Command.fromString(message.getText()).orElse(Command.DEFAULT_COMMAND);
    }

    protected boolean isChatIdAcceptable(Long chatId) {
        return this.chatId.equals(chatId);
    }
}
