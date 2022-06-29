package com.nsu.domicil.parser.common;

import com.nsu.domicil.dto.DetailedAd;
import com.nsu.domicil.dto.PreviewAd;
import org.jsoup.nodes.Document;

import java.util.Properties;

public interface AdDetailsBuilder {
    DetailedAd buildDetailedAdFrom(Document document, Properties properties, PreviewAd previewAd);
}
