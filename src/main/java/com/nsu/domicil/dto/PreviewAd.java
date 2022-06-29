package com.nsu.domicil.dto;

import com.nsu.domicil.dto.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviewAd {
    private String locality;
    private AccommodationType accommodationType;
    private TransactionType transactionType;
    private SourceType sourceType;
    private Boolean longTerm;
    private Integer roomsCount;
    private AddressDto address;
    private String partOfTown;
    private Integer totalArea;
    private Integer floor;
    private Integer numberOfFloors;
    private Integer price;
    private String imageUrl;
    private String sourceUrl;
}
