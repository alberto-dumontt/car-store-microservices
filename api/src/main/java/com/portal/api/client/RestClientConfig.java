package com.portal.api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.JdkClientHttpRequestFactory;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Configuration class to initialize and configure the {@link RestClient} bean.
 * This class sets up the global HTTP client settings used across the application.
 */
@Configuration
public class RestClientConfig {

    /**
     * Configures and creates a {@link RestClient} bean using the JDK-based HTTP client.
     * * <p>The client is configured with a connection timeout of 300,000 milliseconds (5 minutes)
     * and uses the {@link JdkClientHttpRequestFactory} for synchronous request execution.</p>
     * * @return a configured instance of {@link RestClient}.
     */
    @Bean
    public RestClient restClient() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(300000))
                .build();

        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .build();
    }
}