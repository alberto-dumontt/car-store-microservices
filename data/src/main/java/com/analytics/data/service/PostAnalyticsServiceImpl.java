package com.analytics.data.service;

import com.analytics.data.dto.CarPostDTO;
import com.analytics.data.entity.BrandAnalyticsEntity;
import com.analytics.data.entity.CarModelAnalyticsEntity;
import com.analytics.data.entity.CarModelPriceEntity;
import com.analytics.data.repository.BrandAnalyticsRespository;
import com.analytics.data.repository.CarModelAnalyticsRepository;
import com.analytics.data.repository.CarPriceAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostAnalyticsServiceImpl implements PostAnalyticsService {

    @Autowired
    private BrandAnalyticsRespository brandAnalyticsRespository;

    @Autowired
    private CarPriceAnalyticsRepository carPriceAnalyticsRepository;

    @Autowired
    private CarModelAnalyticsRepository carModelAnalyticsRepository;

    /**
     * {@inheritDoc}
     * <p>
     * Delegates to the private helper methods to independently update brand analytics,
     * car model analytics, and car model price analytics.
     * </p>
     */
    @Override
    public void saveDataAnalytics(CarPostDTO carPostDTO) {
        saveBrandAnalytics(carPostDTO.getBrand());
        saveCarModelAnalytics(carPostDTO.getModel());
        saveCarModelPriceAnalytics(carPostDTO.getModel(), carPostDTO.getPrice());
    }

    /**
     * Increments the post count for the given brand, or creates a new analytics record
     * with a count of 1 if the brand does not yet exist in the database.
     *
     * @param brand the car brand name extracted from the car post
     */
    private void saveBrandAnalytics(String brand) {

        BrandAnalyticsEntity brandAnalyticsEntity = new BrandAnalyticsEntity();

        brandAnalyticsRespository.findByBrand(brand).ifPresentOrElse(item -> {
            item.setPosts(item.getPosts() + 1);
            brandAnalyticsRespository.save(item);
        }, () -> {
            brandAnalyticsEntity.setBrand(brand);
            brandAnalyticsEntity.setPosts(1L);
            brandAnalyticsRespository.save(brandAnalyticsEntity);
        });
    }

    /**
     * Increments the post count for the given car model, or creates a new analytics record
     * with a count of 1 if the model does not yet exist in the database.
     *
     * @param carModel the car model name extracted from the car post
     */
    private void saveCarModelAnalytics (String carModel) {
        CarModelAnalyticsEntity carModelAnalyticsEntity = new CarModelAnalyticsEntity();

        carModelAnalyticsRepository.findByModel(carModel).ifPresentOrElse(item -> {
            item.setPosts(item.getPosts() + 1);
            carModelAnalyticsRepository.save(item);
        }, () -> {
            carModelAnalyticsEntity.setModel(carModel);
            carModelAnalyticsEntity.setPosts(1L);
            carModelAnalyticsRepository.save(carModelAnalyticsEntity);
        });
    }

    /**
     * Persists a price record for the given car model.
     * <p>
     * Each call creates a new {@link CarModelPriceEntity}, allowing historical price data
     * to be retained over time.
     * </p>
     *
     * @param carModel the car model name extracted from the car post
     * @param price    the listed price of the car post
     */
    private void saveCarModelPriceAnalytics(
            String carModel,
            Double price
    ) {
        CarModelPriceEntity carModelPriceEntity = new CarModelPriceEntity();

        carModelPriceEntity.setModel(carModel);
        carModelPriceEntity.setPrice(price);
        carPriceAnalyticsRepository.save(carModelPriceEntity);
    }
}
