package com.vasylbhd.lvivhotlinebot.config;

import com.vasylbhd.lvivhotlinebot.model.RedditResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;

@Client("${telegram.bot.meme-api-host-url}")
public interface MemeApiClient {

    @Get("/api/v1/memes/random")
    Maybe<RedditResponse> getRandomMeme();
}
