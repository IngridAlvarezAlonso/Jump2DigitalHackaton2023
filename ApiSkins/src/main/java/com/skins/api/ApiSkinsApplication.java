package com.skins.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;

@SpringBootApplication
public class ApiSkinsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiSkinsApplication.class, args);
	}
	
}
