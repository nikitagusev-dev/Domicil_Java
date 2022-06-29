package com.nsu.domicil.services;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.DetailedAd;
import com.nsu.domicil.dto.PreviewAd;
import com.nsu.domicil.dto.requestParams.PreviewAdRequestParam;

import java.util.ArrayList;
import java.util.List;

public interface IAdService {
    List<PreviewAd> findAdsByFilters(AdFilters adFilters);

    List<PreviewAd> findRecommendedAdsByAd(PreviewAdRequestParam requestParam);

    DetailedAd getDetailsForAd(PreviewAdRequestParam requestParam);

    void create(ArrayList<PreviewAd> previewAds);
}
