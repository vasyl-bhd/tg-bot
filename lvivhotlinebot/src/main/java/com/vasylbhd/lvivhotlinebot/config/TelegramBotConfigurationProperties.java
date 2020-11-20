package com.vasylbhd.lvivhotlinebot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("telegram.bot")
@Configuration
@Data
public class TelegramBotConfigurationProperties {

    private String token;
    private Long chatId;
    private String botName;
}
