package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.DetailedAd;
import com.nsu.domicil.dto.PreviewAd;

import java.util.ArrayList;

public interface AdParser {
    ArrayList<PreviewAd> parsePreviewsUsing(AdFilters adFilters);
    DetailedAd parseDetailsOf(PreviewAd previewAd);
}
