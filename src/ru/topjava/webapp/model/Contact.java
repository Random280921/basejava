package ru.topjava.webapp.model;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return value.equals(contact.value) && Objects.equals(url, contact.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, url);
    }
}
