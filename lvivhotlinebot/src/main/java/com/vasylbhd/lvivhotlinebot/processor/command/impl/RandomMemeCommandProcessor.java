package com.vasylbhd.lvivhotlinebot.processor.command.impl;

import com.vasylbhd.lvivhotlinebot.model.RedditResponse;
import com.vasylbhd.lvivhotlinebot.processor.command.Command;
import com.vasylbhd.lvivhotlinebot.processor.command.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RandomMemeCommandProcessor extends CommandProcessor {

    private final RestTemplate restTemplate;

    @Override
    public Command getCommand() {
        return Command.RANDOM_MEME;
    }

    @Override
    protected void process(Consumer<String> execute) {
        try {
            RedditResponse forObject = restTemplate.getForObject("/random", RedditResponse.class);
            execute.accept(this.getMessage(forObject));
        } catch (Exception e) {
            execute.accept(e.getMessage());
        }
    }

    public String getMessage(RedditResponse redditResponse) {
        return format("%s\nBy: %s\nFrom: %s\n%s",
                redditResponse.title(),
                redditResponse.author(),
                redditResponse.postUrl(),
                redditResponse.message());
    }
}
