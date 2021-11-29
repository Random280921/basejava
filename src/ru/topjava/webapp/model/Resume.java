package ru.topjava.webapp.model;

import ru.topjava.webapp.exception.PhoneNumberException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    private final Map<ContactType, Contact> header = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> body = new EnumMap<>(SectionType.class);

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

    public Map<ContactType, Contact> getHeader() {
        return header;
    }

    public Map<SectionType, Section> getBody() {
        return body;
    }

    public void addContact(ContactType type, Contact contact) {
        if (!header.containsKey(ContactType.PHONE_DEFAULT) && (type.ordinal() == 1 || type.ordinal() == 2))
            type = ContactType.PHONE_DEFAULT;
        if (!header.containsKey(ContactType.PHONE_ADD1) && type.ordinal() == 2)
            type = ContactType.PHONE_ADD1;
        if (!header.containsKey(ContactType.MESSENGER_DEFAULT) && (type.ordinal() == 4 || type.ordinal() == 5))
            type = ContactType.MESSENGER_DEFAULT;
        if (!header.containsKey(ContactType.MESSENGER_ADD1) && type.ordinal() == 5)
            type = ContactType.MESSENGER_ADD1;
        if (!header.containsKey(ContactType.NETWORK_DEFAULT) && (type.ordinal() == 8 || type.ordinal() == 9))
            type = ContactType.NETWORK_DEFAULT;
        if (!header.containsKey(ContactType.NETWORK_ADD1) && type.ordinal() == 9)
            type = ContactType.NETWORK_ADD1;
        if (type.ordinal() < 3 && contact.getValue().replaceAll("[^0-9]+", "").length() < 10)
            throw new PhoneNumberException(String.format("Phone number %s is incorrect", contact.getValue()));
        header.put(type, contact);
    }
    //TODO addBody

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return fullName.equals(resume.fullName) && uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() * 31 + fullName.hashCode();
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
