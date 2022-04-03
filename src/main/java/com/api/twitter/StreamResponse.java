package com.api.twitter;

import com.api.azure.TextDocument;

public class StreamResponse<Tweet> {

    private Tweet data;

    public static StreamResponse nullRespose() {
        return new StreamResponse();
    }

    public Tweet getData() {
        return this.data;
    }

    public void setData (Tweet data) {
        this.data = data;
    }
}
