package com.vasylbhd.lvivhotlinebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class LvivhotlinebotApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(LvivhotlinebotApplication.class, args);
	}

}
