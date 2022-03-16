package com.touchtunes.jukeboxesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class JukeboxesAppApplication {

	/**
	 * This method is used to load configuration for WebClient
	 * @return
	 */
	@Bean
	public WebClient.Builder getWebClient() {
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(JukeboxesAppApplication.class, args);
	}

}
