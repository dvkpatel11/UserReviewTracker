package com.api.azure;

public class TextDocument {
    private String id;
    private String text;
    private String language;

    public TextDocument(String id, String text, String language) {
        this.id = id;
        this.text = text;
        this.language = language;
    }

    public void setLanguage (String language) {
        this.language = language;
    }

    public String getLanguage () {
        return this.language;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getId () { return this.id; }

    public void setTest (String text) {
        this.text = text;
    }

    public String getText () {
        return this.text;
    }
}
