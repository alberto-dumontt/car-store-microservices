package com.analytics.data.repository;

import com.analytics.data.entity.CarModelAnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelAnalyticsRepository extends JpaRepository<CarModelAnalyticsEntity, Long> {

    /**
     * Retrieves a {@link CarModelAnalyticsEntity} by its car model name.
     *
     * @param model the car model name to search for
     * @return an {@link Optional} containing the matching entity, or empty if not found
     */
    Optional<CarModelAnalyticsEntity> findByModel(String model);
}
