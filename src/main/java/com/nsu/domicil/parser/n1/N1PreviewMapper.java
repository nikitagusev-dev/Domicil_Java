package com.nsu.domicil.parser.n1;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.AddressDto;
import com.nsu.domicil.dto.enums.AccommodationType;
import com.nsu.domicil.parser.common.AdFilterMapper;
import com.nsu.domicil.parser.common.AdPreviewMapper;
import org.jsoup.nodes.Element;

import java.util.Properties;

public class N1PreviewMapper implements AdPreviewMapper {
    private final Properties properties;
    private final Element ad;
    private final AdFilters adFilters;
    private final AdFilterMapper adFilterMapper;

    public N1PreviewMapper(Properties properties, Element ad, AdFilters adFilters) {
        this.properties = properties;
        this.ad = ad;
        this.adFilters = adFilters;
        this.adFilterMapper = new N1FilterMapper(properties);
    }

    @Override
    public AccommodationType mapAccommodationType() {
        return adFilters.getAccommodationType();
    }

    @Override
    public Integer mapRoomsCount() {
        String roomsCountElement = ad.select(properties.getProperty("preview_ad_rooms_count_query")).text();

        if (roomsCountElement.startsWith("Ком")) {
            return 1;
        }
        return Integer.parseInt(roomsCountElement.substring(0, roomsCountElement.indexOf("-")));
    }

    @Override
    public AddressDto mapAddress() {
        String addressElement = ad.select(properties.getProperty("preview_ad_address_query")).text();
        var indexOfWhitespace = addressElement.indexOf(" ");
        if (indexOfWhitespace == -1) {
            return null;
        }
        var fullAddress = addressElement.substring(indexOfWhitespace + 1);
        var indexOfComma = fullAddress.indexOf(",");
        if (indexOfComma == -1) {
            return null;
        }
        var street = fullAddress.substring(0, indexOfComma);
        var houseNumber = fullAddress.substring(indexOfComma + 1);
        return new AddressDto(street, houseNumber);
    }

    @Override
    public String mapPartOfTown() {
        String partOfTownElement = ad.select(properties.getProperty("preview_ad_part_of_town_query")).text();
        if (partOfTownElement.isEmpty()) {
            partOfTownElement = "м. ";
            partOfTownElement += ad.select(properties.getProperty("preview_ad_metro_query")).text();
            partOfTownElement += " " + ad.select(properties.getProperty("preview_ad_metro_time_query")).text();
            if (partOfTownElement.equals("м.  ")) {
                partOfTownElement = ad.select(properties.getProperty("preview_ad_city_query")).text();
            }
        }
        return partOfTownElement;
    }

    @Override
    public Integer mapTotalArea() {
        String totalAreaElement = ad.select(properties.getProperty("preview_ad_total_area_query")).text();
        return Integer.parseInt(totalAreaElement.substring(0, totalAreaElement.indexOf(" м")));
    }

    @Override
    public Integer mapFloor() {
        String floorElement = ad.select(properties.getProperty("preview_ad_floor_query")).text();
        return Integer.parseInt(floorElement.substring(0, floorElement.indexOf(" ")));
    }

    @Override
    public Integer mapNumberOfFloors() {
        String numberOfFloorsElement = ad.select(properties.getProperty("preview_ad_number_of_floors_query")).text();
        var beginIndex = numberOfFloorsElement.indexOf("/ ") + 2;
        var endIndex = numberOfFloorsElement.indexOf(" э");
        if (beginIndex - 2 == -1 || endIndex == -1) {
            return null;
        }
        return Integer.parseInt(numberOfFloorsElement.substring(beginIndex, endIndex));
    }

    @Override
    public Integer mapPrice() {
        String priceElement = ad.select(properties.getProperty("preview_ad_price_query")).text();
        var indexOfDash = priceElement.indexOf("/");

        if (indexOfDash != -1) {
            return Integer.parseInt(priceElement.substring(0, indexOfDash).replaceAll("\\s+",""));
        }
        return Integer.parseInt(priceElement.replaceAll("\\s+",""));
    }

    @Override
    public String mapImageUrl() {
        String imagerUrlElement = ad.select(properties.getProperty("preview_ad_image_query")).attr("style");
        var beginIndex = imagerUrlElement.indexOf("http");
        var endIndex = imagerUrlElement.indexOf(");");
        if (beginIndex == -1 || endIndex == -1) {
            return null;
        }
        return imagerUrlElement.substring(beginIndex, endIndex);
    }

    @Override
    public String mapSourceUrl() {
        String sourceUrlElement = ad.select(properties.getProperty("preview_ad_source_query")).attr("href");
        String domain = adFilterMapper.mapDomain(adFilters.getLocality());
        return domain + sourceUrlElement;
    }
}
