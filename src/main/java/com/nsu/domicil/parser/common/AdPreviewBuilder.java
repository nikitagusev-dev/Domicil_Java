package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.PreviewAd;
import org.jsoup.nodes.Element;

import java.util.Properties;

public interface AdPreviewBuilder {
    PreviewAd buildPreviewFrom(Element ad, Properties properties, AdFilters adFilters);
}
