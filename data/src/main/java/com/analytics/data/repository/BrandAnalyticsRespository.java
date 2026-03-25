package com.analytics.data.repository;

import com.analytics.data.entity.BrandAnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandAnalyticsRespository extends JpaRepository<BrandAnalyticsEntity, Long> {

    /**
     * Retrieves a {@link BrandAnalyticsEntity} by its brand name.
     *
     * @param brand the brand name to search for
     * @return an {@link Optional} containing the matching entity, or empty if not found
     */
    Optional<BrandAnalyticsEntity> findByBrand(String brand);
}
