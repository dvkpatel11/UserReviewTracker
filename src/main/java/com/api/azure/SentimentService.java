package com.api.azure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.BaseScalarOptionalDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class SentimentService {

    @Value("${LANG_SERVICE_KEY}")
    // API service key stored as an environment variable
    private String langServiceKey;

    @Autowired
    // Object mapper to read bodies of the mapped objects.
    public ObjectMapper mapper;

    private static final String AZURE_ENDPOINT = "https://user-reviews.cognitiveservices.azure.com";
    private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.1/sentiment";
    private static final String API_KEY_NAME = "Ocp-Apim-Subscription-Key";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    public SentimentAnalysis requestAnalysis (String text, String language) throws IOException, InterruptedException {

        TextDocument document = new TextDocument("1", text, language);
        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
        requestBody.getDocumentList().add(document);

        String endpoint = AZURE_ENDPOINT + AZURE_ENDPOINT_PATH;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .proxy(ProxySelector.getDefault())
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header(API_KEY_NAME, this.langServiceKey)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println(response.statusCode());
            System.out.println(response.body());
            throw new RuntimeException("Error making the API call");
        }

        String sentimentValue = this.mapper
                .readValue(response.body(), JsonNode.class)
                .get("documents")
                .get(0)
                .get("sentiment")
                .asText();

        SentimentAnalysis analysis = new SentimentAnalysis(document, sentimentValue);

        return analysis;
    }
}
