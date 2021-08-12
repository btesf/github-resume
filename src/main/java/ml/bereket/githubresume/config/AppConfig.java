package ml.bereket.githubresume.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${api.username}")
    private String apiUsername;
    @Value("${api.key}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //if apiUsername and apiKey are not set, it tries to use access the apis unauthenticated - subjected to limited rate
        return builder.basicAuthentication(apiUsername, apiKey).build();
    }
}
