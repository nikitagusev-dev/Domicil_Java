package com.nsu.domicil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {
    private final String street;
    private final String houseNumber;
}
