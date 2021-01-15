package com.vasylbhd.lvivhotlinebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public static final String MEMES_PREFIX_URL = "/api/v1/memes/";
    private final String memeApiHostUrl;

    public WebClientConfig(TelegramBotConfigurationProperties properties) {
        this.memeApiHostUrl = properties.getMemeApiHostUrl();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(memeApiHostUrl + MEMES_PREFIX_URL)
                .build();
    }
}
