package com.nsu.domicil.dto.requestParams;

import com.nsu.domicil.dto.enums.AccommodationType;
import com.nsu.domicil.dto.enums.SourceType;
import com.nsu.domicil.dto.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviewAdRequestParam {
    private AccommodationType accommodationType;
    private SourceType sourceType;
    private TransactionType transactionType;
    private Integer roomsCount;
    private String street;
    private String houseNumber;
    private String partOfTown;
    private Integer totalArea;
    private Integer floor;
    private Integer numberOfFloors;
    private Integer price;
    private String imageUrl;
    private String sourceUrl;
}
