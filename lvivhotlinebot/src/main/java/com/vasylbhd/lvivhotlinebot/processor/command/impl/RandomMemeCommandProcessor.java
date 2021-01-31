package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.config.MemeApiClient;
import com.vasylbhd.lvivhotlinebot.model.RedditResponse;
import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.function.Consumer;

import static java.lang.String.format;

@Singleton
@RequiredArgsConstructor
public class RandomMemeCommandProcessor extends CommandProcessor {

    private final MemeApiClient memeApiClient;

    @Override
    public Command getCommand() {
        return Command.RANDOM_MEME;
    }

    @Override
    protected void process(Consumer<String> execute) {
        memeApiClient.getRandomMeme()
                .map(this::getMessage)
                .doOnSuccess(execute::accept)
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
