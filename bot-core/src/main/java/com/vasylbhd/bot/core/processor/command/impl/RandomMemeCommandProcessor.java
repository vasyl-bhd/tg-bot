package com.vasylbhd.bot.core.processor.command.impl;

import com.vasylbhd.bot.core.config.MemeApiClient;
import com.vasylbhd.bot.core.model.RedditResponse;
import com.vasylbhd.bot.core.processor.command.CommandProcessor;
import com.vasylbhd.bot.core.processor.command.Command;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.function.Consumer;

import static java.lang.String.format;

@Singleton
@RequiredArgsConstructor
public class RandomMemeCommandProcessor extends CommandProcessor {

    private final MemeApiClient memeApiClient;

    @Override
    public Command getCommandName() {
        return Command.RANDOM_MEME;
    }

    @Override
    protected void processCommand(Consumer<String> execute) {
        memeApiClient.getRandomMeme()
                .map(this::getMessage)
                .doOnNext(execute)
                .doOnError(e -> execute.accept(e.getMessage()))
                .subscribe();
    }

    public String getMessage(RedditResponse redditResponse) {
        return format("%s\nBy: %s\nFrom: %s\n%s",
                redditResponse.title(),
                redditResponse.author(),
                redditResponse.postUrl(),
                redditResponse.message());
    }
}
