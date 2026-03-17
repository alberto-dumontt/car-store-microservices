package com.portal.api.service;

import com.portal.api.dto.OwnerPostDTO;
import org.springframework.stereotype.Service;

/**
 * Service interface defining the business contract for owner-related operations.
 * <p>
 * This interface provides the abstraction layer for managing car owners
 * within the portal system, decoupled from specific implementation details.
 * </p>
 */
@Service
public interface OwnerPostService {

    /**
     * Creates and persists a new owner record in the system.
     * * <p>This operation typically involves validating the owner's data
     * and communicating with a persistent store or external microservice.</p>
     * * @param ownerPostDTO the data transfer object containing the owner's details.
     */
    void createOwnerCar(OwnerPostDTO ownerPostDTO);
}
