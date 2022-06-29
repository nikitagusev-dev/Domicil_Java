package com.nsu.domicil.dto;

import com.nsu.domicil.dto.enums.AccommodationType;
import com.nsu.domicil.dto.enums.SortingType;
import com.nsu.domicil.dto.enums.TransactionType;
import lombok.Data;

@Data
public class AdFilters {
    private String locality;
    private AccommodationType accommodationType;
    private TransactionType transactionType;
    private Integer[] roomsCount;
    private Integer minTotalArea;
    private Integer maxTotalArea;
    private Integer minFloor;
    private Integer maxFloor;
    private Integer minPrice;
    private Integer maxPrice;
    private SortingType sortingType;
    private Boolean longTerm;
    private Integer page;
}
