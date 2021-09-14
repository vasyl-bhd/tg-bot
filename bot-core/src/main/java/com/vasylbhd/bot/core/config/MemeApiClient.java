package com.vasylbhd.bot.core.config;

import com.vasylbhd.bot.core.model.RedditResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Flux;

@Client("${telegram.bot.meme-api-host-url}")
public interface MemeApiClient {

    @Get("/api/v1/memes/random")
    Flux<RedditResponse> getRandomMeme();
}
