package com.nsu.domicil.parser.n1;

import com.nsu.domicil.dto.AdFilters;
import com.nsu.domicil.dto.DetailedAd;
import com.nsu.domicil.dto.PreviewAd;
import com.nsu.domicil.parser.common.AdDetailsBuilder;
import com.nsu.domicil.parser.common.AdParser;
import com.nsu.domicil.parser.common.AdPreviewBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class N1Parser implements AdParser {
    @Override
    public ArrayList<PreviewAd> parsePreviewsUsing(AdFilters adFilters) {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("n1.properties"));
            String url = new N1UrlBuilder().buildUrlUsing(adFilters, properties);
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            Elements adElements = document.select(properties.getProperty("preview_ad_query"));
            ArrayList<PreviewAd> previewAds = new ArrayList<>();
            AdPreviewBuilder adPreviewBuilder = new N1PreviewBuilder();
            if (adElements.size() != 0) {
                for (Element ad : adElements) {
                    PreviewAd previewAd = adPreviewBuilder.buildPreviewFrom(ad, properties, adFilters);
                    previewAds.add(previewAd);
                }
                return previewAds;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public DetailedAd parseDetailsOf(PreviewAd previewAd) {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("n1.properties"));
            String url = previewAd.getSourceUrl();
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            AdDetailsBuilder adDetailsBuilder = new N1DetailsBuilder();
            return adDetailsBuilder.buildDetailedAdFrom(document, properties, previewAd);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
