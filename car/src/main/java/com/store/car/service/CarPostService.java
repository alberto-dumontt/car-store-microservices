package com.store.car.service;

import com.store.car.dto.CarPostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarPostService {

    /**
     * Persists a new car sale post using the data provided in the given DTO.
     *
     * @param carPostDTO the car post data to be saved
     */
    void newPostDetails(CarPostDTO carPostDTO);

    /**
     * Retrieves all existing car sale posts.
     *
     * @return a list of {@link CarPostDTO} representing all car posts in the database
     */
    List<CarPostDTO> getCarSales();

    /**
     * Updates an existing car sale post identified by {@code postId} with the data
     * from the given DTO.
     *
     * @param carPostDTO the new data to apply to the post
     * @param postId     the ID of the post to update
     */
    void changeCarSale(
            CarPostDTO carPostDTO,
            Long postId
    );

    /**
     * Deletes the car sale post identified by the given {@code postId}.
     *
     * @param postId the ID of the post to delete
     */
    void removeCarSale(Long postId);

}
