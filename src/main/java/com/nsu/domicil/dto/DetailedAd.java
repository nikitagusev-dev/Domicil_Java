package com.nsu.domicil.dto;

import com.nsu.domicil.dto.enums.AccommodationType;
import lombok.Data;

import java.util.List;

@Data
public class DetailedAd {
    private AccommodationType accommodationType;
    private Integer roomsCount;
    private AddressDto address;
    private String partOfTown;
    private Integer totalArea;
    private Integer floor;
    private Integer numberOfFloors;
    private Integer price;
    private List<String> imageUrls;
    private String description;
    private String sourceUrl;
}
