package com.vasylbhd.lvivhotlinebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LvivhotlinebotApplication {

	public static void main(String[] args) {
		//System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		SpringApplication.run(LvivhotlinebotApplication.class, args);
	}

}
