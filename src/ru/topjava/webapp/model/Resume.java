package ru.topjava.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    private final Map<TypeContact, Contact> header = new EnumMap<>(TypeContact.class);
    private final Map<TypeSection, Section> body = new EnumMap<>(TypeSection.class);

    {
        Section s;
        for (TypeSection bodyType :
                TypeSection.values()) {
            s = (bodyType.ordinal() < 4) ? new TextSection() : new CompanySection();
            body.put(bodyType, s);
        }
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Resume.uuid must not be null");
        Objects.requireNonNull(fullName, "Resume.fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<TypeContact, Contact> getHeader() {
        return header;
    }

    public Map<TypeSection, Section> getBody() {
        return body;
    }

    public void addContact(TypeContact type, Contact contact) {
        header.put(type, contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && header.equals(resume.header) && body.equals(resume.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, header, body);
    }

    @Override
    public String toString() {
        return "ФИО: " + fullName + "; UUID: " + uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int compareName = fullName.compareTo(o.fullName);
        return compareName != 0 ? compareName : uuid.compareTo(o.uuid);
    }
}
