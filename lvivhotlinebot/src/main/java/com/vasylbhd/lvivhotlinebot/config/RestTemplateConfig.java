package com.vasylbhd.lvivhotlinebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {

    public static final String MEMES_PREFIX_URL = "/api/v1/memes/";
    private final String memeApiHostUrl;

    public RestTemplateConfig(TelegramBotConfigurationProperties properties) {
        this.memeApiHostUrl = properties.getMemeApiHostUrl();
    }

    @Bean
    public RestTemplate webClient() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(memeApiHostUrl + MEMES_PREFIX_URL));
        return restTemplate;
    }
}
