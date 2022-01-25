package ru.javaonline.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable, Comparable<Contact> {
    private static final long serialVersionUID = 1L;

    private String value;
    private String url;

    public Contact() {
    }

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

    @Override
    public int compareTo(Contact o) {
        return this.compareTo(o);
    }
}
