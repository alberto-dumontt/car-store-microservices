package com.store.car.service;

import com.store.car.dto.OwnerPostDTO;
import org.springframework.stereotype.Service;

@Service
public interface OwnerPostService {

    /**
     * Persists a new owner post using the data provided in the given DTO.
     *
     * @param ownerPostDTO the owner post data to be saved
     */
    void createOwnerPost(OwnerPostDTO ownerPostDTO);
}
