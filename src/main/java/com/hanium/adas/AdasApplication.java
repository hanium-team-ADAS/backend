package com.hanium.adas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AdasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdasApplication.class, args);
	}

}
