package com.vasylbhd.lvivhotlinebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LvivhotlinebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LvivhotlinebotApplication.class, args);
	}

}
