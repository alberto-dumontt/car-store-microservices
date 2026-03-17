package com.portal.api.service;

import com.portal.api.dto.CarPostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface that defines the business contract for managing car listings.
 * <p>
 * This interface provides methods to interact with the car inventory,
 * allowing for the retrieval, modification, and deletion of posts
 * intended for sale in the portal.
 * </p>
 */
@Service
public interface CarPostStoreSerivce {

     /**
      * Retrieves a complete list of all car posts currently available for sale.
      * * @return a {@link List} of {@link CarPostDTO} representing the available inventory.
      */
     List<CarPostDTO> getCarForSale();

     /**
      * Updates the details of an existing car post.
      * * @param carPost the {@link CarPostDTO} object containing the updated car information.
      * @param id the unique identifier of the specific car post to be modified.
      */
     void changeCarForSale(
             CarPostDTO carPost,
             String id
     );

     /**
      * Removes a car post from the sale listing based on its unique identifier.
      * * @param id the unique identifier of the car post to be removed.
      */
     void removeCarForSale(
             String id
     );
}