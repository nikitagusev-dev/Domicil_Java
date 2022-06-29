package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.AddressDto;
import com.nsu.domicil.dto.enums.AccommodationType;

public interface AdPreviewMapper {
    AccommodationType mapAccommodationType();
    Integer mapRoomsCount();
    AddressDto mapAddress();
    String mapPartOfTown();
    Integer mapTotalArea();
    Integer mapFloor();
    Integer mapNumberOfFloors();
    Integer mapPrice();
    String mapImageUrl();
    String mapSourceUrl();
}
