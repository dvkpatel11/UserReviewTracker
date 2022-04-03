package com.api.azure;

import java.util.Locale;

public class SentimentAnalysis {

    private TextDocument document;
    private String sentiment;

    public SentimentAnalysis () {}

    public SentimentAnalysis (TextDocument document) {
        this.document = document;
    }

    public SentimentAnalysis (TextDocument document, String sentiment) {
        this.document = document;
        this.sentiment = sentiment.toLowerCase().trim();
    }

    public TextDocument getDocument () {
        return this.document;
    }

    public void setDocument (TextDocument document) {
        this.document = document;
    }

    public String getSentiment () {
        return this.sentiment;
    }

    public void setSentiment (String sentiment) {
        this.sentiment = sentiment;
    }
}