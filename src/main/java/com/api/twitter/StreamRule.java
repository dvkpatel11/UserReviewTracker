package com.api.twitter;

public class StreamRule {

    private String value, tag;

    public StreamRule (String value, String tag) {
        this.value = value;
        this.tag = tag;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
