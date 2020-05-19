package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.model.RedditResponse;
import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.String.format;

@Service
public class RandomMemeCommandProcessor extends CommandProcessor {

    private final WebClient webClient;

    public RandomMemeCommandProcessor(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Command getCommand() {
        return Command.RANDOM_MEME;
    }

    @Override
    protected void process(Consumer<String> execute) {
        webClient.get().uri("/random")
                .retrieve()
                .bodyToMono(RedditResponse[].class)
                .log()
                .flatMapIterable(Arrays::asList)
                .map(this::getMessage)
                .doOnNext(execute)
                .blockLast();
    }

    public String getMessage(RedditResponse redditResponse) {
        return format("%s\nBy: %s\nFrom: %s\n%s",
                redditResponse.getTitle(),
                redditResponse.getAuthor(),
                redditResponse.getPostUrl(),
                redditResponse.getMessage());
    }
}