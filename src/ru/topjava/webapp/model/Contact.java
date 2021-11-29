package ru.topjava.webapp.model;

public class Contact {
    private final String value;
    private final String url;

    public Contact(String value, String url) {
        this.value = value;
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public String getUrl() {
        return url;
    }
}
