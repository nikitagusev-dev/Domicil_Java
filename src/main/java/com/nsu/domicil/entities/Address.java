package com.nsu.domicil.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity(name = "addresses")
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer addressId;

    @OneToOne
    @MapsId
    private Ad ad;

    private String street;

    @Column(name = "house_number")
    private String houseNumber;
}
