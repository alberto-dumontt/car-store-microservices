package com.portal.api.client;

import com.portal.api.dto.CarPostDTO;
import com.portal.api.dto.OwnerPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Client component responsible for communicating with the external Car and User microservices.
 * It provides methods to manage car listings and user registration using Spring's RestClient.
 */
@Component
public class CarPostStoreClient {

    private final String USER_STORE_SERVICE_URI = "http://localhost:8080/user";
    private final String POSTS_STORE_SERVICE_URI = "http://localhost:8080/sales";

    @Autowired
    RestClient restClient;

    /**
     * Retrieves a list of all car posts available for sale from the store service.
     * * @return a {@link List} of {@link CarPostDTO} containing car listing details.
     */
    public List<CarPostDTO> carForSaleClient() {
        return restClient.get()
                .uri(POSTS_STORE_SERVICE_URI + "/cars")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<CarPostDTO>>() {});
    }

    /**
     * Registers a new owner/user in the user store service.
     * * @param newUser the {@link OwnerPostDTO} object containing the user's information to be saved.
     */
    public void ownerPostsClient(OwnerPostDTO newUser) {
        restClient.post()
                .uri(USER_STORE_SERVICE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newUser)
                .retrieve()
                .toBodilessEntity();
    }

    /**
     * Updates an existing car listing in the store service.
     * * @param carPostDTO the updated car data.
     * @param id the unique identifier of the car post to be modified.
     */
    public void changeCarForSaleClient(CarPostDTO carPostDTO, String id) {
        restClient.put()
                .uri(POSTS_STORE_SERVICE_URI + "/car/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(carPostDTO)
                .retrieve()
                .toBodilessEntity();
    }

    /**
     * Deletes a specific car listing from the store service.
     * * @param id the unique identifier of the car post to be removed.
     */
    public void deleteCarForSaleClient(String id) {
        restClient.delete()
                .uri(POSTS_STORE_SERVICE_URI + "/car/" + id)
                .retrieve()
                .toBodilessEntity();
    }
}