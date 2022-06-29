package com.nsu.domicil.dto;

public class RecommendationResult {
    private Integer numberOfMatches;
    private PreviewAd previewAd;

    public RecommendationResult(Integer numberOfMatches, PreviewAd previewAd) {
        this.numberOfMatches = numberOfMatches;
        this.previewAd = previewAd;
    }

    public Integer getNumberOfMatches() {
        return numberOfMatches;
    }

    public PreviewAd getPreviewAd() {
        return previewAd;
    }
}

