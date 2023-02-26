package com.jack.shoppingapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfiguration {

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				registry.addMapping("/**").allowedMethods("GET", "POST", "DELETE", "PUT").allowedHeaders("*")
						.allowedOriginPatterns("*").allowCredentials(true);
				WebMvcConfigurer.super.addCorsMappings(registry);
			}
		};
	}
}
