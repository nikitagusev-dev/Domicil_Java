package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.DetailedAd;
import com.nsu.domicil.dto.PreviewAd;

import java.util.List;

public interface AdDetailsMapper {
    DetailedAd mapPreviewAdToDetailedAd(PreviewAd previewAd);
    List<String> mapImageUrls();
    String mapDescription();
}
