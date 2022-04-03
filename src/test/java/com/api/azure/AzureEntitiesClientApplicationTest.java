package com.api.azure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
public class AzureEntitiesClientApplicationTest {

    @Value("${LANG_SERVICE_KEY}")
    private String langServiceKey;

    private static final String AZURE_ENDPOINT = "https://user-reviews.cognitiveservices.azure.com";
    private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.1/sentiment";
    private static final String API_KEY_NAME = "Ocp-Apim-Subscription-Key";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    public static final String TEST_JSON =
                    "{"
                    +"	\"documents\": ["
                            +"	{"
                            +"		\"language\": \"en\","
                            +"			\"id\": \"1\","
                            +"			\"text\": \"Mr. Ox : Selling collectibles since 1922\""
                            +"	}, "
                            +" {"
                            +"    \"language\": \"en\","
                            +"    \"id\": \"2\","
                            +"   \"text\": \"Wonderful watch being sold at Mr. Ox\""
                            +"  }, "
                            +" {"
                            +"    \"language\": \"en\","
                            +"    \"id\": \"3\","
                            +"   \"text\": \"Mr. Ox crockery is very rusty\""
                            +"  }"
                        +"]"
                    +"}\"";

    private static final String sampleText = "Mr. Ox crockery is very rusty";

    @Autowired
    // Objectmapper to read bodies of the mapped objects.
    public ObjectMapper mapper;

    @Test
    public void getEntities() throws IOException, InterruptedException {

        // Create a http client
        HttpClient client = HttpClient.newHttpClient();

        // Sync call
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(API_KEY_NAME, this.langServiceKey)
                .POST(HttpRequest.BodyPublishers.ofString(TEST_JSON))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert response received and print to console
        System.out.println(response.statusCode());
        System.out.println(response.body());

//        Test for async calls
//        TextDocument document = new TextDocument("1", sampleText, "en");
//        TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
//        requestBody.getDocuments().add(document);

//        // Create a request to the Azure endpoint path
//        HttpRequest request = HttpRequest.newBuilder()
//                .header(CONTENT_TYPE, APPLICATION_JSON)
//                .header(API_KEY_NAME, langServiceKey)
//                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
//                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody))))
//                .build();

//        // Async implementation
//        // Pass the response asynchronously to the client. Returns a CompletableFuture
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                // Handle the completable future
//                .thenApply(response -> response.body())
//                .thenAccept(body -> {
//                    // JsonNode object to better work with json bodies
//                    JsonNode jsonNode;
//
//                    try {
//                        jsonNode = mapper.readValue(body, JsonNode.class);
//
//                        // Extract keyPhrases from the json body
//                        String value = jsonNode.get("documents")
//                                .get(0)
//                                .get("confidenceScores")
//                                .asText();
//
//                        System.out.println(("This is the first key phrase: " + value));
//                    } catch (JsonProcessingException e) {
//                        // Auto generated catch block
//                        e.printStackTrace();
//                    }
//
//                });
//
//        System.out.println(("This will be print first because the call is async"));
//        Thread.sleep(5000);
    }
}