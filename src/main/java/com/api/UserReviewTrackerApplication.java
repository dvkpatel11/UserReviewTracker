package com.api;

import com.api.azure.SentimentAnalysis;
import com.api.azure.SentimentService;
import com.api.twilio.TwilioClient;
import com.api.twitter.StreamResponse;
import com.api.twitter.Tweet;
import com.api.twitter.TwitterStreamingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class UserReviewTrackerApplication implements CommandLineRunner {

    @Value("${TWILIO_SID}")
    private String twilioSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twiolioAuthToken;

    @Value("${TO_NUMBER}")
    private String toNumber;

    private static final String FROM_NUMBER = "+12183180829";

    private static final String TWILIO_API_DOMAIN = "https://api.twilio.com";

    @Autowired
    private TwitterStreamingService twitterStreamingService;

    @Autowired
    private SentimentService sentimentService;

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public TwilioClient twilioClient() {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(twilioSid, twiolioAuthToken);

        return Feign.builder()
                .requestInterceptor(interceptor)
                .encoder(new FormEncoder())
                .target(TwilioClient.class, TWILIO_API_DOMAIN);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserReviewTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) {

        this.twitterStreamingService.stream().subscribe(tweet -> {

            //1. Subscribe a lambda function to Twitter's StreamingService
            System.out.println("The tweet says: " + tweet);

            try {

                //2. Deserialize the json from Twitter to StreamResponse
                StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);

                //3. Pass the tweet to Azure's Cognitive Services for Sentiment analysis
                SentimentAnalysis analysis = this.sentimentService
                        .requestAnalysis(response.getData().getClass().toString(), "en");

                String message = analysis.getSentiment().equalsIgnoreCase("positive")
                        ? "We got positive feedback on Twitter!"
                        : "We got negative feedback on Twitter!";

                //4. Print feedback message to console
                System.out.println(message);

                //5. Use TwilioClient to send message regarding the feedback to owner cellphone device
                this.twilioClient().sendTextMessage(
                        twilioSid,
                        toNumber,
                        FROM_NUMBER,
                        message);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });

    }
}
