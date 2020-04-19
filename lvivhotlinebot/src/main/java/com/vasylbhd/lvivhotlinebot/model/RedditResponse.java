package com.vasylbhd.lvivhotlinebot.model;

import lombok.Data;

@Data
public class RedditResponse {
    String postUrl;
    String title;
    String message;
    String author;
}
