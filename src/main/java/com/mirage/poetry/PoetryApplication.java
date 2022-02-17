package com.mirage.poetry;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableEncryptableProperties
@EnableJpaAuditing
@SpringBootApplication
public class PoetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoetryApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	 	return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE").allowedOrigins("*");
			}
		};
	}

}
