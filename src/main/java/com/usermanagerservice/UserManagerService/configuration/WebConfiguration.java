package com.usermanagerservice.UserManagerService.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration

public class WebConfiguration {
    @Value("${devicemicroservice.port}")
    private int device_port;
    @Value("${devicemicroservice.host}")
    private String device_host;
    @Bean
    public WebClient webClient(){
        String baseURL = UriComponentsBuilder.fromUriString("http://" + device_host + ":")
                .port(device_port)
                .path("/user")
                .build()
                .toUriString();
        return WebClient.builder().baseUrl(baseURL).build();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
