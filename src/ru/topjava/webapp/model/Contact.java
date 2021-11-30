package ru.topjava.webapp.model;

import static java.util.Objects.requireNonNull;

public class Contact {
    private final String value;

    private String url;

    public Contact(String value) {
        this(value, null);
    }

    public Contact(String value, String url) {
        requireNonNull(value, "Contact.value must not be null");
        this.value = value;
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
