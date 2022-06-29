package com.nsu.domicil.controllers;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.DetailedAd;
import com.nsu.domicil.dto.PreviewAd;
import com.nsu.domicil.dto.requestParams.PreviewAdRequestParam;
import com.nsu.domicil.services.IAdService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AdController {
    private final IAdService adService;

    @GetMapping("/ads")
    public ResponseEntity<List<PreviewAd>> getAdsByFilters(AdFilters adFilters) {
        return ResponseEntity.ok(adService.findAdsByFilters(adFilters));
    }

    @GetMapping("/rec")
    public ResponseEntity<List<PreviewAd>> getRecommendedAdsByAd(PreviewAdRequestParam previewAd) {
        return ResponseEntity.ok(adService.findRecommendedAdsByAd(previewAd));
    }

    @GetMapping("/details")
    public ResponseEntity<DetailedAd> getDetailsForAd(PreviewAdRequestParam previewAd) {
        return ResponseEntity.ok(adService.getDetailsForAd(previewAd));
    }
}
