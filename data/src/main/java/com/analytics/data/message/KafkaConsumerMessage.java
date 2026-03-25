package com.analytics.data.message;

import com.analytics.data.dto.CarPostDTO;
import com.analytics.data.service.PostAnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerMessage {

    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerConfigs.class);

    @Autowired
    private PostAnalyticsService postAnalyticsService;

    /**
     * Kafka listener that consumes messages from the {@code car-post-topic} topic.
     * <p>
     * Logs the received payload and delegates persistence of analytics data
     * to {@link PostAnalyticsService#saveDataAnalytics(CarPostDTO)}.
     * </p>
     *
     * @param carPostDTO the car post data transferred from the producer via Kafka
     */
    @KafkaListener(topics = "car-post-topic", groupId = "analytics-posts-group")
    public void listening(CarPostDTO carPostDTO) {

        LOG.info("ANALYTICS DATA - Received Car Post information: {}", carPostDTO);
        postAnalyticsService.saveDataAnalytics(carPostDTO);
    }
}
