package com.nsu.domicil.services;

import com.nsu.domicil.dto.*;
import com.nsu.domicil.entities.Ad;
import com.nsu.domicil.dto.requestParams.PreviewAdRequestParam;
import com.nsu.domicil.mappers.AdMapper;
import com.nsu.domicil.parser.cian.CianParser;
import com.nsu.domicil.parser.common.AdParser;
import com.nsu.domicil.parser.n1.N1Parser;
import com.nsu.domicil.repositories.AdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdService implements IAdService {
    private static final int PAGE_SIZE = 25;
    private static final int RECOMMENDATIONS_SIZE = 25;
    private static final int STORAGE_SIZE = 5000;

    private final AdRepository adRepository;
    private final AdMapper adMapper = new AdMapper();

    @Override
    public List<PreviewAd> findAdsByFilters(AdFilters adFilters) {
        return adRepository
                .findAll()
                .stream()
                .map(adMapper::mapToPreview)
                .filter(previewAd -> isSuitableAd(previewAd, adFilters))
                .skip((long) (adFilters.getPage() - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());
    }

    @Override
    public List<PreviewAd> findRecommendedAdsByAd(PreviewAdRequestParam requestParam) {
        synchronized (this) {
            return adRepository.findAll()
                    .stream()
                    .map(adMapper::mapToPreview)
                    .map(currentAd -> recommendationResultFrom(previewFrom(requestParam), currentAd))
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(RecommendationResult::getNumberOfMatches).reversed())
                    .limit(RECOMMENDATIONS_SIZE)
                    .map(RecommendationResult::getPreviewAd)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public DetailedAd getDetailsForAd(PreviewAdRequestParam requestParam) {
        AdParser adParser = null;

        switch (requestParam.getSourceType()) {
            case N1 -> adParser = new N1Parser();
            case CIAN -> adParser = new CianParser();
        }

        return Objects.requireNonNull(adParser).parseDetailsOf(previewFrom(requestParam));
    }

    @Override
    public void create(ArrayList<PreviewAd> previewAds) {
        synchronized (this) {
            var newAds = previewAds
                    .stream()
                    .map(adMapper::mapToAd)
                    .filter(ad -> !adRepository.existsBySourceUrl(ad.getSourceUrl()))
                    .collect(Collectors.toList());

            var currentAds = adRepository.findAll();

            if (currentAds.size() + newAds.size() > STORAGE_SIZE) {
                var adsToRemoveCount = (currentAds.size() + newAds.size()) - STORAGE_SIZE;
                var adsToRemove = currentAds.subList(0, adsToRemoveCount);
                adRepository.deleteAllById(adsToRemove.stream().map(Ad::getAdId).collect(Collectors.toList()));
            }

            adRepository.saveAll(newAds);
        }
    }

    private boolean isSuitableAd(PreviewAd previewAd, AdFilters adFilters) {
        if (adFilters.getLocality() != null) {
            if (!previewAd.getLocality().equals(adFilters.getLocality())) {
                return false;
            }
        }
        if (adFilters.getAccommodationType() != null) {
            if (!previewAd.getAccommodationType().equals(adFilters.getAccommodationType())) {
                return false;
            }
        }
        if (adFilters.getTransactionType() != null) {
            if (!previewAd.getTransactionType().equals(adFilters.getTransactionType())) {
                return false;
            }
        }
        if (adFilters.getRoomsCount() != null) {
            if (!Arrays.asList(adFilters.getRoomsCount()).contains(previewAd.getRoomsCount())) {
                return false;
            }
        }
        if (adFilters.getMinTotalArea() != null) {
            if (!(previewAd.getTotalArea() >= adFilters.getMinTotalArea())) {
                return false;
            }
        }
        if (adFilters.getMaxTotalArea() != null) {
            if (!(previewAd.getTotalArea() <= adFilters.getMaxTotalArea())) {
                return false;
            }
        }
        if (adFilters.getMinFloor() != null) {
            if (!(previewAd.getFloor() >= adFilters.getMinFloor())) {
                return false;
            }
        }
        if (adFilters.getMaxFloor() != null) {
            if (!(previewAd.getFloor() <= adFilters.getMaxFloor())) {
                return false;
            }
        }
        if (adFilters.getMinPrice() != null) {
            if (!(previewAd.getPrice() >= adFilters.getMinPrice())) {
                return false;
            }
        }
        if (adFilters.getMaxPrice() != null) {
            if (!(previewAd.getPrice() >= adFilters.getMaxPrice())) {
                return false;
            }
        }
        if (adFilters.getLongTerm() != null) {
            if (!previewAd.getLongTerm().equals(adFilters.getLongTerm())) {
                return false;
            }
        }
        return true;
    }

    private RecommendationResult recommendationResultFrom(PreviewAd baseAd, PreviewAd comparedAd) {
        if (baseAd.getSourceUrl().equals(comparedAd.getSourceUrl())) {
            return null;
        }
        if (baseAd.getAccommodationType() != comparedAd.getAccommodationType()) {
            return null;
        }
        if (baseAd.getTransactionType() != comparedAd.getTransactionType()) {
            return null;
        }

        var numberOfMatches = 0;

        if (baseAd.getRoomsCount().equals(comparedAd.getRoomsCount())) {
            numberOfMatches += 1;
        }
        if (baseAd.getAddress().getStreet().equals(comparedAd.getAddress().getStreet())) {
            numberOfMatches += 1;
        }
        if (Math.abs(baseAd.getTotalArea() - comparedAd.getTotalArea()) <= 10) {
            numberOfMatches += 1;
        }
        if (baseAd.getFloor().equals(comparedAd.getFloor())) {
            numberOfMatches += 1;
        }
        if (Math.abs(baseAd.getPrice() - comparedAd.getPrice()) <= 0.1 * baseAd.getPrice()) {
            numberOfMatches += 1;
        }

        if (numberOfMatches == 0) {
            return null;
        } else {
            return new RecommendationResult(numberOfMatches, comparedAd);
        }
    }

    private PreviewAd previewFrom(PreviewAdRequestParam requestParam) {
        PreviewAd previewAd = new PreviewAd();
        previewAd.setAccommodationType(requestParam.getAccommodationType());
        previewAd.setTransactionType(requestParam.getTransactionType());
        previewAd.setSourceType(requestParam.getSourceType());
        previewAd.setRoomsCount(requestParam.getRoomsCount());
        previewAd.setPartOfTown(requestParam.getPartOfTown());
        previewAd.setTotalArea(requestParam.getTotalArea());
        previewAd.setFloor(requestParam.getFloor());
        previewAd.setNumberOfFloors(requestParam.getNumberOfFloors());
        previewAd.setPrice(requestParam.getPrice());
        previewAd.setImageUrl(requestParam.getImageUrl());
        previewAd.setSourceUrl(requestParam.getSourceUrl());
        previewAd.setAddress(new AddressDto(requestParam.getStreet(), requestParam.getHouseNumber()));
        return previewAd;
    }
}
