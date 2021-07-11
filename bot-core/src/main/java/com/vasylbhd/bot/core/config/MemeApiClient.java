package com.vasylbhd.bot.core.config;

import com.vasylbhd.bot.core.model.RedditResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;

@Client("${telegram.bot.meme-api-host-url}")
public interface MemeApiClient {

    @Get("/api/v1/memes/random")
    Maybe<RedditResponse> getRandomMeme();
}
