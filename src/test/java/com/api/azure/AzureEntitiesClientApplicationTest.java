package com.api.azure;

import org.junit.jupiter.api.Test;
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
    @Test
    public void getEntities() throws IOException, InterruptedException {

        // Create a http client
        HttpClient client = HttpClient.newHttpClient();

        // Create a request to the Azure endpoint path
        HttpRequest request = HttpRequest.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(API_KEY_NAME, langServiceKey)
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                .POST(HttpRequest.BodyPublishers.ofString(TEST_JSON))
                .build();

        // Handle the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert response received and print to console
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }
}