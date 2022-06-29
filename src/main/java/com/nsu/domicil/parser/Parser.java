package com.nsu.domicil.parser;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.enums.AccommodationType;
import com.nsu.domicil.dto.enums.SortingType;
import com.nsu.domicil.dto.enums.TransactionType;
import com.nsu.domicil.parser.cian.CianParser;
import com.nsu.domicil.parser.common.AdParser;
import com.nsu.domicil.parser.n1.N1Parser;
import com.nsu.domicil.services.IAdService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Parser {
    private final IAdService adService;
    private final AdParser n1Parser = new N1Parser();
    private final AdParser cianParser = new CianParser();

    public Parser(IAdService adService) {
        this.adService = adService;
    }

//    // MARK: - N1
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseN1ApartmentsSell() {
//        try {
//            Thread.sleep(0);
//            parseAds("N1", n1Parser, AccommodationType.APARTMENT, TransactionType.SELL, "novosibirsk", 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseN1CottagesSell() {
//        try {
//            Thread.sleep(100);
//            parseAds("N1", n1Parser, AccommodationType.COTTAGE, TransactionType.SELL, "novosibirsk", 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseN1RoomsSell() {
//        try {
//            Thread.sleep(200);
//            parseAds("N1", n1Parser, AccommodationType.ROOM, TransactionType.SELL, "novosibirsk", 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseN1ApartmentsRent() {
//        try {
//            Thread.sleep(300);
//            parseAds("N1", n1Parser, AccommodationType.APARTMENT, TransactionType.RENT, "novosibirsk", 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseN1CottagesRent() {
//        try {
//            Thread.sleep(400);
//            parseAds("N1", n1Parser, AccommodationType.COTTAGE, TransactionType.RENT, "novosibirsk", 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseN1RoomsRent() {
//        try {
//            Thread.sleep(500);
//            parseAds("N1", n1Parser, AccommodationType.ROOM, TransactionType.RENT, "novosibirsk", 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // MARK: - Cian
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseCianApartmentsSell() {
//        try {
//            Thread.sleep(500);
//            parseAds("Cian", cianParser, AccommodationType.APARTMENT, TransactionType.SELL, "novosibirsk", 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseCianCottagesSell() {
//        try {
//            Thread.sleep(500);
//            parseAds("Cian", cianParser, AccommodationType.COTTAGE, TransactionType.SELL, "novosibirsk", 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseCianRoomsSell() {
//        try {
//            Thread.sleep(500);
//            parseAds("Cian", cianParser, AccommodationType.ROOM, TransactionType.SELL, "novosibirsk", 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseCianApartmentsRent() {
//        try {
//            Thread.sleep(500);
//            parseAds("Cian", cianParser, AccommodationType.APARTMENT, TransactionType.RENT, "novosibirsk", 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseCianCottagesRent() {
//        try {
//            Thread.sleep(500);
//            parseAds("Cian", cianParser, AccommodationType.COTTAGE, TransactionType.RENT, "novosibirsk", 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Scheduled(fixedDelay = 1000 * 60 * 10)
//    public void parseCianRoomsRent() {
//        try {
//            Thread.sleep(500);
//            parseAds("Cian", cianParser, AccommodationType.ROOM, TransactionType.RENT, "novosibirsk", 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
    private void parseAds(
            String parserName,
            AdParser adParser,
            AccommodationType accommodationType,
            TransactionType transactionType,
            String locality,
            Integer sleep
    ) {
        AdFilters adFilters = new AdFilters();

        adFilters.setLocality(locality);
        adFilters.setAccommodationType(accommodationType);
        adFilters.setTransactionType(transactionType);
        adFilters.setSortingType(SortingType.DATE_FROM_NEW);

        var page = 1;

        adFilters.setPage(page);

        var ads = adParser.parsePreviewsUsing(adFilters);
        adService.create(ads);;

        while (!ads.isEmpty()) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            page++;
            adFilters.setPage(page);
            ads = adParser.parsePreviewsUsing(adFilters);
            adService.create(ads);
            System.out.println(parserName + " parsed " + adFilters.getAccommodationType() + " " + adFilters.getTransactionType());
        }
    }
}
