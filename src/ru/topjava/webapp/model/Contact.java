package ru.topjava.webapp.model;

import java.util.Objects;

public class Contact {
    private final String value;
    private final String url;

    public Contact(String value, String url) {
        Objects.requireNonNull(value, "Contact.value must not be null");
        Objects.requireNonNull(url, "Contact.url must not be null");
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
