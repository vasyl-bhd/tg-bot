package com.vasylbhd.lvivhotlinebot.model;

public record RedditResponse(
        String postUrl,
        String title,
        String message,
        String author
) { }
