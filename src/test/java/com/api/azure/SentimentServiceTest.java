package com.api.azure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SentimentServiceTest {

    @Autowired
    private SentimentService sentimentService;

    @Test
    void positiveSentimentTest() throws IOException, InterruptedException {

        SentimentAnalysis analysis = this.sentimentService.requestAnalysis("1", "I relish sweet food", "en");
        assertNotNull(analysis);
        assertEquals("positive", analysis.getSentiment());

    }

    @Test
    void negativeSentimentTest() throws IOException, InterruptedException {

        SentimentAnalysis analysis = this.sentimentService.requestAnalysis("I cannot stand sweet food", "en");
        assertNotNull(analysis);
        assertEquals("negative", analysis.getSentiment());

    }

}
