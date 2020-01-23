package com.sonuswaves.moviecatalogservice;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
//    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000); // 3 seconds
        //3 seconds time out if the request is not processed.
        return new RestTemplate(clientHttpRequestFactory);

    }


//    @Bean
//    //Reactive way (new way)
//    //use webclient to build a client
//    public WebClient.Builder getWebClientBuilder() {
//        return WebClient.builder();
//    }
}
