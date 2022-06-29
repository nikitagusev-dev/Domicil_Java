package com.nsu.domicil.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@Entity(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer adId;

    private String locality;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "accommodation_type_id")
    private AccommodationType accommodationType;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @Column(name = "source_type")
    private String sourceType;

    @Column(name = "rooms_count")
    private Integer roomsCount;

    @OneToOne(mappedBy = "ad", cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    private Address address;

    @Column(name = "part_of_town")
    private String partOfTown;

    @Column(name = "total_area")
    private Integer totalArea;

    private Integer floor;

    @Column(name = "number_of_floors")
    private Integer numberOfFloors;

    private Integer price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "long_term")
    private Boolean longTerm;
}
