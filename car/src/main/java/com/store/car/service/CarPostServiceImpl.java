package com.store.car.service;

import com.store.car.dto.CarPostDTO;
import com.store.car.entity.CarPostEntity;
import com.store.car.repository.CarPostRepository;
import com.store.car.repository.OwnerPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarPostServiceImpl implements CarPostService {

    @Autowired
    private CarPostRepository carPostRepository;

    @Autowired
    OwnerPostRepository ownerPostRepository;

    /**
     * {@inheritDoc}
     * <p>
     * Maps the given {@link CarPostDTO} to a {@link CarPostEntity} and persists it.
     * </p>
     */
    @Override
    public void newPostDetails(CarPostDTO carPostDTO) {
        CarPostEntity carPostEntity = mapCarDTOtoEntity(carPostDTO);
        carPostRepository.save(carPostEntity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Fetches all car post records from the database and maps each {@link CarPostEntity}
     * to a {@link CarPostDTO} before returning the list.
     * </p>
     */
    @Override
    public List<CarPostDTO> getCarSales() {
        List<CarPostDTO> listCarsSales = new ArrayList<>();

        carPostRepository.findAll().forEach(item-> {
            listCarsSales.add(mapCarEntityToDTO(item));
        });

        return listCarsSales;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Looks up the post by {@code postId} and updates its fields with the values
     * from {@code carPostDTO}. Throws {@link NoSuchElementException} if the post is not found.
     * </p>
     */
    @Override
    public void changeCarSale(
            CarPostDTO carPostDTO,
            Long postId
    ) {

        carPostRepository.findById(postId).ifPresentOrElse(item-> {
         item.setDescription(carPostDTO.getDescription());
         item.setContact(carPostDTO.getContact());
         item.setPrice(carPostDTO.getPrice());
         item.setBrand(carPostDTO.getBrand());
         item.setEngineVersion(carPostDTO.getEngineVersion());
         item.setModel(carPostDTO.getModel());

         carPostRepository.save(item);

        }, ()-> {
            throw new NoSuchElementException();
        });
    }

    /**
     * {@inheritDoc}
     * <p>
     * Deletes the car post identified by {@code postId} from the database.
     * </p>
     */
    @Override
    public void removeCarSale(Long postId) {
        carPostRepository.deleteById(postId);
    }

    /**
     * Maps a {@link CarPostEntity} to a {@link CarPostDTO}.
     *
     * @param carPostEntity the entity to convert
     * @return a {@link CarPostDTO} populated with the entity's data, including the owner's name
     */
    private CarPostDTO mapCarEntityToDTO(CarPostEntity carPostEntity) {

        return CarPostDTO.builder()
                .brand(carPostEntity.getBrand())
                .city(carPostEntity.getCity())
                .model(carPostEntity.getModel())
                .description(carPostEntity.getDescription())
                .engineVersion(carPostEntity.getEngineVersion())
                .createdDate(carPostEntity.getCreatedDate())
                .ownerName(carPostEntity.getOwnerPost().getName())
                .price(carPostEntity.getPrice()).build();

    }

    /**
     * Maps a {@link CarPostDTO} to a {@link CarPostEntity}.
     * <p>
     * Resolves the owner by {@code ownerId} from the repository and sets the contact number
     * accordingly. Throws {@link RuntimeException} if the owner is not found.
     * The creation date is set to the current date and time.
     * </p>
     *
     * @param carPostDTO the DTO containing the car post data
     * @return a fully populated {@link CarPostEntity} ready to be persisted
     */
    private CarPostEntity mapCarDTOtoEntity(CarPostDTO carPostDTO) {
        CarPostEntity carPostEntity = new CarPostEntity();

        ownerPostRepository.findById(carPostDTO.getOwnerId()).ifPresentOrElse(item -> {
            carPostEntity.setOwnerPost(item);
            carPostEntity.setContact(item.getContactNumber());
        }, () -> {
            throw new RuntimeException();
        });

        carPostEntity.setModel(carPostDTO.getModel());
        carPostEntity.setBrand(carPostDTO.getBrand());
        carPostEntity.setPrice(carPostDTO.getPrice());
        carPostEntity.setCity(carPostDTO.getCity());
        carPostEntity.setDescription(carPostDTO.getDescription());
        carPostEntity.setCreatedDate(String.valueOf(new Date()));
        carPostEntity.setEngineVersion(carPostDTO.getEngineVersion());

        return carPostEntity;
    }
}
