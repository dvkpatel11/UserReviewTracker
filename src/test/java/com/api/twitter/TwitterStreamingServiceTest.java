package com.api.twitter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@SpringBootTest
public class TwitterStreamingServiceTest {

    @Value("${TWITTER_BEARER_TOKEN}")
    private String twitterBearerToken;

    private final static String TWITTER_API_ENDPOINT = "https://api.twitter.com";

    private final static String TWITTER_API_STREAM_PATH = "/2/tweets/search/stream";

    private final static String TWITTER_API_STREAM_RULES_PATH = "/2/tweets/search/stream/rules";

    @Autowired
    private WebClient.Builder builder;

    @Test
    void webClientTest() throws IOException {

        WebClient client = builder
                .baseUrl(TWITTER_API_ENDPOINT)
                .defaultHeaders(headers -> headers.setBearerAuth(twitterBearerToken))
                .build();

        StreamRuleRequest ruleRequest = new StreamRuleRequest();
        ruleRequest.addRule("LinkedIn", "LinkedIn Tag");

        client.post()
                .uri(TWITTER_API_STREAM_RULES_PATH)
                .bodyValue(ruleRequest)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> {
                    client.get()
                            .uri(TWITTER_API_STREAM_PATH)
                            .retrieve()
                            .bodyToFlux(String.class)
                            .filter(body -> !body.isBlank())
                            .subscribe(json -> {
                                System.out.println(json);
                            });
                });

        System.in.read();

    }

}
