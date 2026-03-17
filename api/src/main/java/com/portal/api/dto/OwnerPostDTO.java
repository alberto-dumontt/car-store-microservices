package com.portal.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// Lombok annotation that implements the Builder Pattern for this class.
// It automatically generates a builder() method that allows creating
// objects in a fluent and readable way, avoiding large constructors
// and making object instantiation clearer and safer.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class OwnerPostDTO {
    private String model;
    private String brand;
    private String price;
    private String description;
    private String engineVersion;
    private String city;
    private String createdDate;
    private Long ownerId;
    private String ownerName;
    private String ownerType;
    private String contact;
}
