package com.nsu.domicil.parser.cian;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.parser.common.AdFilterMapper;
import com.nsu.domicil.parser.common.AdUrlBuilder;

import java.util.Properties;

public class CianUrlBuilder implements AdUrlBuilder {
    @Override
    public String buildUrlUsing(AdFilters adFilters, Properties properties) {
        AdFilterMapper adFilterMapper = new CianFilterMapper(properties);
        String domain = adFilterMapper.mapDomain(adFilters.getLocality());
        String accommodation = adFilterMapper.mapAccommodation(adFilters.getAccommodationType());
        String transaction = adFilterMapper.mapTransaction(adFilters.getTransactionType());
        String sorting = adFilterMapper.mapSorting(adFilters.getSortingType());
        String page = adFilterMapper.mapPage(adFilters.getPage());
        return domain + accommodation + transaction + sorting + page;
    }
}
