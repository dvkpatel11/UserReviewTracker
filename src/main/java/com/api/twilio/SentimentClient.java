package com.api.twilio;
import com.api.azure.SentimentAnalysisResponse;
import com.api.azure.TextAnalyticsRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SentimentClient {

    @RequestLine("POST /text/analytics/v3.1/sentiment")
    @Headers({"Ocp-Apim-Subscription-Key: {langServiceKey}","Content-Type: application/json"})
    public SentimentAnalysisResponse analyze(@Param("langServiceKey") String langServiceKey, TextAnalyticsRequest request);

}
