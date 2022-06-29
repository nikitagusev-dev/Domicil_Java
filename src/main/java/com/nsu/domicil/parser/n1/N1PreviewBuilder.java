package com.nsu.domicil.parser.n1;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.PreviewAd;

import com.nsu.domicil.dto.enums.SourceType;
import com.nsu.domicil.dto.enums.TransactionType;
import com.nsu.domicil.parser.common.AdPreviewBuilder;
import com.nsu.domicil.parser.common.AdPreviewMapper;
import org.jsoup.nodes.Element;

import java.util.Properties;

public class N1PreviewBuilder implements AdPreviewBuilder {
    @Override
    public PreviewAd buildPreviewFrom(Element ad, Properties properties, AdFilters adFilters) {
        AdPreviewMapper adPreviewMapper = new N1PreviewMapper(properties, ad, adFilters);
        PreviewAd previewAd = new PreviewAd();
        previewAd.setAccommodationType(adPreviewMapper.mapAccommodationType());
        previewAd.setTransactionType(adFilters.getTransactionType());
        previewAd.setSourceType(SourceType.N1);
        previewAd.setRoomsCount(adPreviewMapper.mapRoomsCount());
        previewAd.setAddress(adPreviewMapper.mapAddress());
        previewAd.setPartOfTown(adPreviewMapper.mapPartOfTown());
        previewAd.setTotalArea(adPreviewMapper.mapTotalArea());
        previewAd.setFloor(adPreviewMapper.mapFloor());
        previewAd.setNumberOfFloors(adPreviewMapper.mapNumberOfFloors());
        previewAd.setPrice(adPreviewMapper.mapPrice());
        previewAd.setImageUrl(adPreviewMapper.mapImageUrl());
        previewAd.setSourceUrl(adPreviewMapper.mapSourceUrl());
        previewAd.setLocality(adFilters.getLocality());
        if (adFilters.getTransactionType() == TransactionType.RENT) {
            previewAd.setLongTerm(adFilters.getLongTerm());
        }
        return previewAd;
    }
}
