package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.enums.AccommodationType;
import com.nsu.domicil.dto.enums.SortingType;
import com.nsu.domicil.dto.enums.TransactionType;

public interface AdFilterMapper {
    String mapDomain(String locality);
    String mapAccommodation(AccommodationType accommodationType);
    String mapTransaction(TransactionType transactionType);
    String mapSorting(SortingType sortingType);
    String mapPage(Integer page);
}
