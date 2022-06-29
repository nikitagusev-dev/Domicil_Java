package com.nsu.domicil.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@Entity(name = "accommodations")
public class AccommodationType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accommodationTypeId;

    @Column(name = "accommodation_type")
    private String accommodationType;

    public AccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }
}
