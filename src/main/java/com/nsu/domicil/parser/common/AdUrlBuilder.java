package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.AdFilters;

import java.util.Properties;

public interface AdUrlBuilder {
    String buildUrlUsing(AdFilters adFilters, Properties properties);
}
