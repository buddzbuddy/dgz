package com.webdatabase.dgz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DgzApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgzApplication.class, args);
	}

}
