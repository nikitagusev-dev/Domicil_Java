package com.nsu.domicil.parser.cian;

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

public class CianParser implements AdParser {
    @Override
    public ArrayList<PreviewAd> parsePreviewsUsing(AdFilters adFilters) {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("cian.properties"));
            String url = new CianUrlBuilder().buildUrlUsing(adFilters, properties);
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .header("cookie", " _CIAN_GK=887791d9-1d54-42ae-92e1-4f6ff2a722a0; adb=1; login_mro_popup=1; _gcl_au=1.1.421983426.1642092289; uxfb_usertype=searcher; uxs_uid=1fc74b30-7490-11ec-8964-61e8cb929887; _gid=GA1.2.1202045058.1642092290; tmr_lvid=56a22d4c59e20d92164082f2db92a535; tmr_lvidTS=1642092289966; afUserId=e730f3f5-4075-4059-9e62-4bbf50a09669-p; AF_SYNC=1642092290962; first_visit_time=1642092377379; serp_registration_trigger_popup=1; countCallNowPopupShowed=0%3A1642094943968; serp_stalker_banner=1; _ga_3369S417EL=GS1.1.1642094413.1.1.1642096882.60; _ga=GA1.2.218617346.1642092290; jview=1; newobject_scount=1; newobject_active=1; newobject_all=1; _ym_uid=1642097141559792826; _ym_d=1642097141; utm_source=google; utm_campaign=b2c_novosibirsk_brand_cian_all_mix_search; utm_medium=cpc; utm_term=%2Bcian; gclid=CjwKCAiA24SPBhB0EiwAjBgkhk4U7U3dI6ZLP9Vxn8nELAc46cBMN2USCJS2F7piPSi-KO6MWSjNaBoCJogQAvD_BwE; sopr_utm=%7B%22utm_source%22%3A+%22google%22%2C+%22utm_medium%22%3A+%22cpc%22%2C+%22utm_campaign%22%3A+%22b2c_novosibirsk_brand_cian_all_mix_search%22%2C+%22utm_term%22%3A+%22%2Bcian%22%7D; lastSource=google; google_paid=1; _gac_UA-30374201-1=1.1642178258.CjwKCAiA24SPBhB0EiwAjBgkhk4U7U3dI6ZLP9Vxn8nELAc46cBMN2USCJS2F7piPSi-KO6MWSjNaBoCJogQAvD_BwE; session_region_id=4668; session_main_town_region_id=4668; __cf_bm=9OmiVpSfs6GGPfViPvBIf9Di2EvmLrul7_l2Zm53cvY-1642242306-0-AUCp+cGkcWNkTAAmR/TXPVhj8QpCeMoOS6pVzlaJH3Jce3RUNngL4zCtZYy17wQYuQ3jr7nv9+f79bdRPxpVNy4=; anti_bot=\"2|1:0|10:1642242375|8:anti_bot|40:eyJyZW1vdGVfaXAiOiAiODQuMjM3LjU0LjE1In0=|1eac711b20d74afe37ca5deb60bd1f0bb0e69f13a1be1f5160aff1862a4402bc\"; sopr_session=dd3f52ba598a47f0; tmr_detect=1%7C1642242384038; cto_bundle=rUcro190b3EyQWRXWjd4OW5leGNnUUVyUGZpd01yenpRV0pMOEtlVDVjNkJzN2U1ZDY4N1Y4dWd6RnZqNDBsQXhVbmhEVnZ3JTJGJTJCcHBDenpRQlcwVGNLOUxZQTNQTFB6ZktLc2E0MEJqUnQ1N09ucm5QUlBHRjV4SElPRjRjZ2I4Q1VLcEtsbG1YMVBUSEY1aTQxJTJCRm5KTURVeG1YdkJKSlVnaWt2eFBha0RxMzgyRVlWM3E2ak1ya2NxVjY2WHJLUnp3cEQ; tmr_reqNum=252")
                    .get();
            Elements adElements = document.select(properties.getProperty("preview_ad_query"));
            ArrayList<PreviewAd> previewAds = new ArrayList<>();
            AdPreviewBuilder adPreviewBuilder = new CianPreviewBuilder();
            if (adElements.size() != 0) {
                for (Element ad : adElements) {
                    PreviewAd previewAd = adPreviewBuilder.buildPreviewFrom(ad, properties, adFilters);
                    if (previewAd != null) {
                        previewAds.add(previewAd);
                    }
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
            properties.load(this.getClass().getClassLoader().getResourceAsStream("cian.properties"));
            String url = previewAd.getSourceUrl();
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            AdDetailsBuilder adDetailsBuilder = new CianDetailsBuilder();
            return adDetailsBuilder.buildDetailedAdFrom(document, properties, previewAd);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
