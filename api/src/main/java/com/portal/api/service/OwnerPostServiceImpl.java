package com.portal.api.service;

import com.portal.api.client.CarPostStoreClient;
import com.portal.api.dto.OwnerPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OwnerPostService} interface.
 * This service handles business operations related to car owners,
 * specifically their registration and data persistence via the API client.
 */
@Service
public class OwnerPostServiceImpl implements OwnerPostService {

    @Autowired
    private CarPostStoreClient carPostStoreClient;

    /**
     * Forwards the owner creation request to the store service.
     * This method prepares and sends the owner's data to be persisted in the
     * underlying user storage system.
     * * @param ownerPostDTO the {@link OwnerPostDTO} object containing the new owner's registration details.
     */
    @Override
    public void createOwnerCar(OwnerPostDTO ownerPostDTO) {
        carPostStoreClient.ownerPostsClient(ownerPostDTO);
    }
}
