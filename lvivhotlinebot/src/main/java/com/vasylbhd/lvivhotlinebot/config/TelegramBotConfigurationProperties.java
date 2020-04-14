package com.vasylbhd.lvivhotlinebot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("telegram.bot")
@Configuration
public class TelegramBotConfigurationProperties {

    private String token;
    private String chatId;
    private String botName;
}
