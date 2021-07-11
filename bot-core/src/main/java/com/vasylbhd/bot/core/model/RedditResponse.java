package com.vasylbhd.bot.core.model;

public record RedditResponse(
        String postUrl,
        String title,
        String message,
        String author
) { }
