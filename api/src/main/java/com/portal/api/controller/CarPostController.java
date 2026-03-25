package com.portal.api.controller;

import com.portal.api.dto.CarPostDTO;
import com.portal.api.message.KafkaProducerMessage;
import com.portal.api.service.CarPostStoreSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarPostController {

    private final Logger LOG = LoggerFactory.getLogger(CarPostController.class);

    @Autowired
    private CarPostStoreSerivce carPostStoreSerivce;

    @Autowired
    private KafkaProducerMessage kafkaProducerMessage;

    @PostMapping("/post")
    public ResponseEntity postCarForSale(
            @RequestBody CarPostDTO carPostDTO
    ) {
        LOG.info("MAIN REST API - Producer Car Post information: {}", carPostDTO);

        kafkaProducerMessage.sendMessage(carPostDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<CarPostDTO>> getCarSales() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(carPostStoreSerivce.getCarForSale());
    }

    @PutMapping("/{id}")
    public ResponseEntity changeCarSale(
            @RequestBody CarPostDTO carPostDTO,
            @PathVariable("id") String id
    ) {
        carPostStoreSerivce.changeCarForSale(carPostDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCarForSale(
            @PathVariable("id") String id
    ) {
        carPostStoreSerivce.removeCarForSale(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
