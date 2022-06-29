package com.nsu.domicil.mappers;

import com.nsu.domicil.dto.AddressDto;
import com.nsu.domicil.dto.PreviewAd;
import com.nsu.domicil.dto.enums.SourceType;
import com.nsu.domicil.entities.AccommodationType;
import com.nsu.domicil.entities.Ad;
import com.nsu.domicil.entities.Address;
import com.nsu.domicil.entities.TransactionType;

public class AdMapper {
    public Ad mapToAd(PreviewAd previewAd) {
        var ad = new Ad();

        ad.setLocality(previewAd.getLocality());

        switch (previewAd.getAccommodationType()) {
            case APARTMENT -> ad.setAccommodationType(new AccommodationType("APARTMENT"));
            case HOUSE -> ad.setAccommodationType(new AccommodationType("HOUSE"));
            case COTTAGE -> ad.setAccommodationType(new AccommodationType("COTTAGE"));
            case ROOM -> ad.setAccommodationType(new AccommodationType("ROOM"));
        }

        switch (previewAd.getTransactionType()) {
            case SELL -> ad.setTransactionType(new TransactionType("SELL"));
            case RENT -> ad.setTransactionType(new TransactionType("RENT"));
        }

        switch (previewAd.getSourceType()) {
            case N1 -> ad.setSourceType("N1");
            case CIAN -> ad.setSourceType("Cian");
        }

        ad.setRoomsCount(previewAd.getRoomsCount());
        ad.setPartOfTown(previewAd.getPartOfTown());
        ad.setTotalArea(previewAd.getTotalArea());
        ad.setFloor(previewAd.getFloor());
        ad.setNumberOfFloors(previewAd.getNumberOfFloors());
        ad.setPrice(previewAd.getPrice());
        ad.setImageUrl(previewAd.getImageUrl());
        ad.setSourceUrl(previewAd.getSourceUrl());
        ad.setLongTerm(previewAd.getLongTerm());

        var address = new Address();
        address.setAd(ad);
        ad.setAddress(address);

        if (previewAd.getAddress() != null) {
            address.setStreet(previewAd.getAddress().getStreet());
            address.setHouseNumber(previewAd.getAddress().getHouseNumber());
        }

        return ad;
    }

    public PreviewAd mapToPreview(Ad ad) {
        var previewAd = new PreviewAd();

        previewAd.setLocality(ad.getLocality());

        switch (ad.getAccommodationType().getAccommodationType()) {
            case "APARTMENT" -> previewAd.setAccommodationType(com.nsu.domicil.dto.enums.AccommodationType.APARTMENT);
            case "HOUSE" -> previewAd.setAccommodationType(com.nsu.domicil.dto.enums.AccommodationType.HOUSE);
            case "COTTAGE" -> previewAd.setAccommodationType(com.nsu.domicil.dto.enums.AccommodationType.COTTAGE);
            case "ROOM" -> previewAd.setAccommodationType(com.nsu.domicil.dto.enums.AccommodationType.ROOM);
            default -> {}
        }

        switch (ad.getTransactionType().getTransactionType()) {
            case "SELL" -> previewAd.setTransactionType(com.nsu.domicil.dto.enums.TransactionType.SELL);
            case "RENT" -> previewAd.setTransactionType(com.nsu.domicil.dto.enums.TransactionType.RENT);
            default -> {}
        }

        switch (ad.getSourceType()) {
            case "N1" -> previewAd.setSourceType(SourceType.N1);
            case "Cian" -> previewAd.setSourceType(SourceType.CIAN);
        }

        previewAd.setRoomsCount(ad.getRoomsCount());
        previewAd.setAddress(new AddressDto(ad.getAddress().getStreet(), ad.getAddress().getHouseNumber()));
        previewAd.setPartOfTown(ad.getPartOfTown());
        previewAd.setTotalArea(ad.getTotalArea());
        previewAd.setFloor(ad.getFloor());
        previewAd.setNumberOfFloors(ad.getNumberOfFloors());
        previewAd.setPrice(ad.getPrice());
        previewAd.setImageUrl(ad.getImageUrl());
        previewAd.setSourceUrl(ad.getSourceUrl());
        previewAd.setLongTerm(ad.getLongTerm());

        return previewAd;
    }
}
