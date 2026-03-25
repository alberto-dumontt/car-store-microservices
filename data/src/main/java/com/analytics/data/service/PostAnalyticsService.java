package com.analytics.data.service;

import com.analytics.data.dto.CarPostDTO;
import org.springframework.stereotype.Service;

@Service
public interface PostAnalyticsService {

    /**
     * Processes and persists analytics data extracted from the given car post.
     * <p>
     * Implementations are expected to update brand analytics, car model analytics,
     * and car model price analytics accordingly.
     * </p>
     *
     * @param carPostDTO the car post data received from the Kafka topic
     */
    void saveDataAnalytics(CarPostDTO carPostDTO);
}
