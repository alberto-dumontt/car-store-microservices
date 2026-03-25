package com.store.car.message;

import com.store.car.dto.CarPostDTO;
import com.store.car.service.CarPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerMessage {

    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerConfigs.class);

    @Autowired
    private CarPostService carPostService;

    /**
     * Kafka listener that consumes messages from the {@code car-post-topic} topic.
     * <p>
     * Logs the received payload and delegates persistence of the car post
     * to {@link CarPostService#newPostDetails(CarPostDTO)}.
     * </p>
     *
     * @param carPostDTO the car post data transferred from the producer via Kafka
     */
    @KafkaListener(topics = "car-post-topic", groupId = "store-posts-group")
    public void listening(CarPostDTO carPostDTO) {

        LOG.info("CAR STORE - Received Car Post information: {}", carPostDTO);
        carPostService.newPostDetails(carPostDTO);
    }
}
