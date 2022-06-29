package com.nsu.domicil.parser.cian;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.AddressDto;
import com.nsu.domicil.dto.enums.AccommodationType;
import com.nsu.domicil.parser.common.AdFilterMapper;
import com.nsu.domicil.parser.common.AdPreviewMapper;
import com.nsu.domicil.parser.n1.N1FilterMapper;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class CianPreviewMapper implements AdPreviewMapper {
    private final Properties properties;
    private final Element ad;
    private final AdFilters adFilters;

    public CianPreviewMapper(Properties properties, Element ad, AdFilters adFilters) {
        this.properties = properties;
        this.ad = ad;
        this.adFilters = adFilters;
    }

    @Override
    public AccommodationType mapAccommodationType() {
        return adFilters.getAccommodationType();
    }

    @Override
    public Integer mapRoomsCount() {
        if (adFilters.getAccommodationType() == AccommodationType.APARTMENT) {
            var roomsText = ad.select(properties.getProperty("preview_ad_rooms_count_query")).text();
            var indexOfDash = roomsText.indexOf("-");
            if (indexOfDash == -1) {
                return 1;
            }
            try {
                return Integer.parseInt(roomsText.substring(0, indexOfDash));
            } catch (NumberFormatException ex) {
                return 1;
            }
        } else if (adFilters.getAccommodationType() == AccommodationType.ROOM) {
            return 1;
        } else {
            return null;
        }
    }

    @Override
    public AddressDto mapAddress() {
        var addresses = ad.select(properties.getProperty("preview_ad_address_query")).select("a");
        var addressTextsList = addresses
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());

        String street = null;
        String houseNumber = null;
        switch (adFilters.getAccommodationType()) {
            case ROOM, APARTMENT -> {
                if (addressTextsList.size() >= 1) {
                    street = addressTextsList.get(addressTextsList.size() - 1);
                }
                if (addressTextsList.size() >= 2) {
                    houseNumber = addressTextsList.get(addressTextsList.size() - 2);
                }
            }
            case HOUSE, COTTAGE -> {
                if (addressTextsList.size() >= 1) {
                    street = addressTextsList.get(addressTextsList.size() - 1);
                }
            }
        }
        return new AddressDto(street, houseNumber);
    }

    @Override
    public String mapPartOfTown() {
        var addresses = ad.select(properties.getProperty("preview_ad_address_query")).select("a");
        var addressTextsList = addresses
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());

        String partOfTown = null;
        switch (adFilters.getAccommodationType()) {
            case ROOM, APARTMENT -> {
                if (addressTextsList.size() >= 2) {
                    addressTextsList.remove(addressTextsList.size() - 1);
                    addressTextsList.remove(addressTextsList.size() - 1);
                } else {
                    break;
                }
                partOfTown = String.join(",", addressTextsList);
            }
            case HOUSE, COTTAGE -> {
                if (addressTextsList.size() >= 1) {
                    addressTextsList.remove(addressTextsList.size() - 1);
                } else {
                    break;
                }
                partOfTown = String.join(",", addressTextsList);
            }
        }
        return partOfTown;
    }

    @Override
    public Integer mapTotalArea() {
        String totalAreaText = ad.select(properties.getProperty("preview_ad_total_area_query")).text();
        if (!totalAreaText.contains(" м")) {
            return null;
        }
        var totalArea = totalAreaText.substring(totalAreaText.indexOf(", ") + 2, totalAreaText.indexOf(" м"))
                .replaceAll(",", ".");
        try {
            return (int) Float.parseFloat(totalArea);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public Integer mapFloor() {
        if (adFilters.getAccommodationType() == AccommodationType.APARTMENT
                || adFilters.getAccommodationType() == AccommodationType.ROOM) {
            String floorText = ad.select(properties.getProperty("preview_ad_floor_query")).text();
            var floorOptionalText = Arrays.stream(floorText.split(",")).filter(element -> element.contains("/")).findFirst();
            if (floorOptionalText.isPresent()) {
                floorText = floorOptionalText.get();
            } else {
                return null;
            }
            try {
                return Integer.parseInt(floorText.substring(1, floorText.indexOf("/")));
            } catch(NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Integer mapNumberOfFloors() {
        if (adFilters.getAccommodationType() == AccommodationType.APARTMENT
                || adFilters.getAccommodationType() == AccommodationType.ROOM) {
            String numberOfFloorsText = ad.select(properties.getProperty("preview_ad_number_of_floors_query")).text();
            var numberOfFloorsTextOptional = Arrays.stream(numberOfFloorsText.split(",")).filter(element -> element.contains("/")).findFirst();
            if (numberOfFloorsTextOptional.isPresent()) {
                numberOfFloorsText = numberOfFloorsTextOptional.get();
            } else {
                return null;
            }
            try {
                return Integer.parseInt(numberOfFloorsText.substring(numberOfFloorsText.indexOf("/") + 1, numberOfFloorsText.indexOf(" э")));
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Integer mapPrice() {
        var priceText = ad.select(properties.getProperty("preview_ad_price_query")).text();
        return Integer.parseInt(priceText.replaceAll("\\D+",""));
    }

    @Override
    public String mapImageUrl() {
        var imageElement = ad.select(properties.getProperty("preview_ad_image_query")).first();
        if (imageElement == null) {
            return null;
        }
        return imageElement.attr("src");
    }

    @Override
    public String mapSourceUrl() {
        return ad.select(properties.getProperty("preview_ad_source_query")).attr("href");
    }
}
