package com.sonuswaves.moviecatalogservice;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpringConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
       return new RestTemplate();
    }


//    @Bean
//    //Reactive way (new way)
//    //use webclient to build a client
//    public WebClient.Builder getWebClientBuilder() {
//        return WebClient.builder();
//    }
}
