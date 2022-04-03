package com.api.twilio;
import com.api.azure.*;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SentimentClientTest {

    @Value("${LANG_SERVICE_KEY}")
    private String langServiceKey;

    private static final String AZURE_ENDPOINT = "https://user-reviews.cognitiveservices.azure.com";

    @Test
    void positiveSentimentTest() {

        TextDocument document = new TextDocument("1", "I like this car!", "en");
        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.getDocumentList().add(document);

        SentimentAnalysisResponse analysisResponse = null;

        SentimentClient client = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(SentimentClient.class, AZURE_ENDPOINT);

        analysisResponse = client.analyze(langServiceKey, requestBody);

        System.out.println("Analysis to: 'I like this car!' is: ");
        System.out.println(analysisResponse.getDocumentScoreList().get(0));
        assertNotNull(analysisResponse);
        assertEquals("positive", analysisResponse.getDocumentScoreList().get(0).getSentiment());

    }

}
