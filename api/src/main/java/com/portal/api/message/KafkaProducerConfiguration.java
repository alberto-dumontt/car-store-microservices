package com.portal.api.config;

import com.portal.api.dto.CarPostDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for the Kafka Producer using modern Jackson serializers.
 * This class sets up the infrastructure to send car sale data to Kafka topics.
 */
@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    /**
     * Configures the Producer Factory with modern Jackson serialization.
     * Uses {@link JacksonJsonSerializer} to handle JSON conversion, replacing
     * the deprecated JsonSerializer.
     * * @return a configured {@link ProducerFactory} instance.
     */
    @Bean
    public ProducerFactory<String, CarPostDTO> userProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates the {@link KafkaTemplate} used by the application to execute Kafka operations.
     * * @return a high-level abstraction for sending messages to Kafka.
     */
    @Bean
    public KafkaTemplate<String, CarPostDTO> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }
}