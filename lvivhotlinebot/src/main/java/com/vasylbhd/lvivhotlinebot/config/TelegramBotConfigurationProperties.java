package com.vasylbhd.lvivhotlinebot.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("telegram.bot")
public interface TelegramBotConfigurationProperties {

    String getToken();
    Long getChatId();
    String getBotName();
    String getMemeApiHostUrl();
}
