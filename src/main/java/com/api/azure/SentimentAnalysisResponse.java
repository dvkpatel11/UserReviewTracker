package com.api.azure;

import java.util.ArrayList;
import java.util.List;

public class SentimentAnalysisResponse {

    private List<TextDocumentScore> documentScoreList = new ArrayList<>();

    public List<TextDocumentScore> getDocumentScoreList() {
        return documentScoreList;
    }

    public void setDocumentScoreList (List<TextDocumentScore> documentScoreList) {
        this.documentScoreList = documentScoreList;
    }

    public static class TextDocumentScore {

        private String id;

        private String sentiment;

        private String getId() {
            return this.id;
        }

        public void setId (String id) {
            this.id = id;
        }

        public String getSentiment() {
            return this.sentiment;
        }

        public void setSentiment(String sentiment) {
            this.sentiment = sentiment;
        }
    }
}
