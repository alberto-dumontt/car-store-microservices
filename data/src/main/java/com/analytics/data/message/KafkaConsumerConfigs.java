package com.analytics.data.message;

import com.analytics.data.dto.CarPostDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfigs {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    /**
     * Creates and configures the Kafka {@link ConsumerFactory} used to deserialize messages
     * from the {@code car-post-topic} topic.
     * <p>
     * Configures the bootstrap server address, consumer group ID ({@code analytics-posts-group}),
     * and JSON deserialization of {@link CarPostDTO} values via Jackson.
     * </p>
     *
     * @return a {@link ConsumerFactory} with {@link String} keys and {@link CarPostDTO} values
     */
    @Bean
    public ConsumerFactory<String, CarPostDTO> consumerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "analytics-posts-group");
        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JacksonJsonDeserializer<>(CarPostDTO.class, false));
    }

    /**
     * Creates the {@link ConcurrentKafkaListenerContainerFactory} used by {@code @KafkaListener}
     * annotated methods to receive messages concurrently.
     * <p>
     * Associates the factory with the {@link #consumerFactory()} configured for {@link CarPostDTO}.
     * </p>
     *
     * @return a {@link ConcurrentKafkaListenerContainerFactory} with {@link String} keys and {@link CarPostDTO} values
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CarPostDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CarPostDTO>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
