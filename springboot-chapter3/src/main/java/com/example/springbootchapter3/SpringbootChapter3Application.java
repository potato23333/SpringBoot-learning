package com.example.springbootchapter3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootChapter3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootChapter3Application.class, args);
	}

}
