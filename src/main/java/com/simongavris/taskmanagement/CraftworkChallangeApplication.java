package com.simongavris.taskmanagement;

import com.simongavris.taskmanagement.util.DataLoader;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CraftworkChallangeApplication {

	public static void main(String[] args) {

		SpringApplication.run(CraftworkChallangeApplication.class, args);


	}

	@Bean
	ApplicationRunner dataLoader() {
		return new DataLoader();
	}
}
