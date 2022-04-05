package com.api.azure;

import java.util.ArrayList;
import java.util.List;

public class TextAnalyticsRequest {

    private List<TextDocument> documentList = new ArrayList<>();

    public List<TextDocument> getDocumentList() {
        return this.documentList;
    }

    public void setDocumentList (List<TextDocument> documentList) {
        this.documentList = documentList;
    }

}
