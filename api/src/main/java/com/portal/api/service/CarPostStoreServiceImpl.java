package com.portal.api.service;

import com.portal.api.client.CarPostStoreClient;
import com.portal.api.dto.CarPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CarPostStoreSerivce} interface.
 * This service acts as a middle layer between the controller and the API client,
 * handling the business logic for car sales listings.
 */
@Service
public class CarPostStoreServiceImpl implements CarPostStoreSerivce {

    @Autowired
    private CarPostStoreClient carPostStoreClient;

    /**
     * Retrieves all available car posts for sale by calling the external store client.
     * * @return a {@link List} of {@link CarPostDTO} representing the current inventory.
     */
    @Override
    public List<CarPostDTO> getCarForSale() {
        return carPostStoreClient.carForSaleClient();
    }

    /**
     * Updates the details of a specific car listing.
     * * @param carPost the {@link CarPostDTO} containing the updated information.
     * @param id the unique identifier of the car post to be updated.
     */
    @Override
    public void changeCarForSale(CarPostDTO carPost, String id) {
        carPostStoreClient.changeCarForSaleClient(carPost, id);
    }

    /**
     * Removes a car listing from the sale records.
     * * @param id the unique identifier of the car post to be deleted.
     */
    @Override
    public void removeCarForSale(String id) {
        carPostStoreClient.deleteCarForSaleClient(id);
    }
}
