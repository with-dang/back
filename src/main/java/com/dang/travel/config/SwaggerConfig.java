package com.dang.travel.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Value("${server.url}")
	private String serverUrl;

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
			.servers(List.of(new Server().url(serverUrl)));
	}
}
