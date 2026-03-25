package com.store.car.service;

import com.store.car.dto.OwnerPostDTO;
import com.store.car.entity.OwnerPostEntity;
import com.store.car.repository.OwnerPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerPostServiceImpl implements OwnerPostService {

    @Autowired
    private OwnerPostRepository ownerPostRepository;

    /**
     * {@inheritDoc}
     * <p>
     * Maps the given {@link OwnerPostDTO} fields (name, type, and contact number)
     * to a new {@link OwnerPostEntity} and persists it to the database.
     * </p>
     */
    @Override
    public void createOwnerPost(OwnerPostDTO ownerPostDTO) {
        OwnerPostEntity ownerPostEntity = new OwnerPostEntity();
        ownerPostEntity.setName(ownerPostDTO.getName());
        ownerPostEntity.setType(ownerPostDTO.getType());
        ownerPostEntity.setContactNumber(ownerPostDTO.getContactNumber());

        ownerPostRepository.save(ownerPostEntity);
    }
}
