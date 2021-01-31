package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import io.micronaut.context.annotation.Prototype;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@Prototype
@NoArgsConstructor
public class StartCommandProcessor extends CommandProcessor {

    private boolean isMasterChatId;

    @Override
    public Command getCommandName() {
        return Command.START;
    }

    @Override
    public void processCommand(Consumer<String> execute) {
        execute.accept("Greetings, my master!");
        if (!isMasterChatId) {
            execute.accept("Oh no, you are not my master...");
            sleep(1000);
            execute.accept("Well, I guess... then I must politely ask you to fuck off.");
            sleep(1000);
            execute.accept("Thanks in advance");
        }
    }

    @Override
    protected boolean isChatIdAcceptable(Long chatId) {
        this.isMasterChatId = super.isChatIdAcceptable(chatId);
        return true;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms + 1000 * ThreadLocalRandom.current().nextLong(1, 3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
