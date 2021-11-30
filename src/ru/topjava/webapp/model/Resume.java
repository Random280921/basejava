package ru.topjava.webapp.model;

import ru.topjava.webapp.exception.PhoneNumberException;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    private final Map<Integer, Contact> header = new TreeMap<>();
    private final Map<Integer, Section> body = new TreeMap<>();

    {
        int o;
        Section s;
        for (SectionType bodyType :
                SectionType.values()) {
            o = bodyType.ordinal();
            s = (o < 4) ? new SectionText() : new SectionCompany();
            body.put(o, s);
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

    public Map<Integer, Contact> getHeader() {
        return header;
    }

    public String getBodyText(int sectionOrdinal) {
        return ((SectionText) body.get(sectionOrdinal)).getBlockPosition();
    }

    public List<String> getListText(int sectionOrdinal) {
        return ((SectionText) body.get(sectionOrdinal)).getListPosition();
    }

    public List<Company> getListCompany(int sectionOrdinal) {
        List<Company> willBeenSorted = ((SectionCompany) body.get(sectionOrdinal)).getListPosition();
        willBeenSorted.sort(Company::compareTo);
        return willBeenSorted;
    }

    public void addContact(ContactType type, Contact contact) {
        final int o = type.ordinal();
        final String num = contact.getValue();
        if (!header.containsKey(0) && (o == 1 || o == 2))
            type = ContactType.PHONE_DEFAULT;
        if (!header.containsKey(1) && o == 2)
            type = ContactType.PHONE_ADD1;
        if (!header.containsKey(3) && (o == 4 || o == 5))
            type = ContactType.MESSENGER_DEFAULT;
        if (!header.containsKey(4) && o == 5)
            type = ContactType.MESSENGER_ADD1;
        if (!header.containsKey(7) && (o == 8 || o == 9))
            type = ContactType.NETWORK_DEFAULT;
        if (!header.containsKey(8) && o == 9)
            type = ContactType.NETWORK_ADD1;
        if (o < 3 &&
                (num.replaceAll("[0-9]+", "").length() > 0
                        || num.replaceAll("[^0-9]+", "").length() < 10))
            throw new PhoneNumberException(contact.getValue());
        header.put(type.ordinal(), contact);
    }

    public void addBodyText(int sectionOrdinal, String blockText) {
        ((SectionText) body.get(sectionOrdinal)).addBlockPosition(blockText);
    }

    public void addListText(int sectionOrdinal, String blockText) {
        ((SectionText) body.get(sectionOrdinal)).getListPosition().add(blockText);
    }

    public void addListCompany(int sectionOrdinal, Company company) {
        ((SectionCompany) body.get(sectionOrdinal)).addListPosition(company);
    }

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
