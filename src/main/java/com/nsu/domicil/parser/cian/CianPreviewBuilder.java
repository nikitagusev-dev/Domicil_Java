package com.nsu.domicil.parser.cian;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.PreviewAd;
import com.nsu.domicil.dto.enums.SourceType;
import com.nsu.domicil.dto.enums.TransactionType;
import com.nsu.domicil.parser.common.AdPreviewBuilder;
import com.nsu.domicil.parser.common.AdPreviewMapper;
import org.jsoup.nodes.Element;

import java.util.Properties;

public class CianPreviewBuilder implements AdPreviewBuilder {
    @Override
    public PreviewAd buildPreviewFrom(Element ad, Properties properties, AdFilters adFilters) {
        AdPreviewMapper adPreviewMapper = new CianPreviewMapper(properties, ad, adFilters);
        PreviewAd previewAd = new PreviewAd();
        previewAd.setAccommodationType(adPreviewMapper.mapAccommodationType());
        previewAd.setTransactionType(adFilters.getTransactionType());
        previewAd.setSourceType(SourceType.CIAN);
        var roomsCount = adPreviewMapper.mapRoomsCount();
        previewAd.setRoomsCount(roomsCount);
        previewAd.setAddress(adPreviewMapper.mapAddress());
        previewAd.setPartOfTown(adPreviewMapper.mapPartOfTown());
        var totalArea = adPreviewMapper.mapTotalArea();
        previewAd.setTotalArea(totalArea);
        var floor = adPreviewMapper.mapFloor();
        previewAd.setFloor(floor);
        var numberOfFloors = adPreviewMapper.mapNumberOfFloors();
        previewAd.setNumberOfFloors(numberOfFloors);
        previewAd.setPrice(adPreviewMapper.mapPrice());
        previewAd.setImageUrl(adPreviewMapper.mapImageUrl());
        previewAd.setSourceUrl(adPreviewMapper.mapSourceUrl());
        previewAd.setLocality(adFilters.getLocality());
        if (roomsCount == null && totalArea == null && floor == null && numberOfFloors == null) {
            return null;
        }
        if (adFilters.getTransactionType() == TransactionType.RENT) {
            previewAd.setLongTerm(adFilters.getLongTerm());
        }
        return previewAd;
    }
}
