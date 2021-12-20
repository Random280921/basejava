package ru.topjava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String fullName;

    private final Map<ContactType, Contact> header = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> body = new EnumMap<>(SectionType.class);

    public Resume() {
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

    public Map<ContactType, Contact> getHeader() {
        return header;
    }

    public Map<SectionType, AbstractSection> getBody() {
        return body;
    }

    public void addContact(ContactType type, Contact contact) {
        header.put(type, contact);
    }

    public void addSection(SectionType type, AbstractSection section) {
        body.put(type, section);
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
        StringBuilder resumeStr = new StringBuilder("ФИО: ").append(fullName).append("; UUID: ").append(uuid).append("\n");
        for (Map.Entry<ContactType, Contact> entry : getHeader().entrySet()) {
            resumeStr.append(entry.getKey()).append(" |= val: ").append(entry.getValue().getValue())
                    .append("; url:").append(entry.getValue().getUrl()).append("\n");
        }
        for (Map.Entry<SectionType, AbstractSection> entry : getBody().entrySet()) {
            SectionType type = entry.getKey();
            resumeStr.append("\n").append(type).append(" |= ");
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resumeStr.append("\n").append(((TextBlockSection) entry.getValue()).getBlockPosition());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> listString = ((TextListSection) entry.getValue()).getListPosition();
                    for (String text : listString) {
                        resumeStr.append("\n").append(text);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION: {
                    List<Company> listCompany = ((CompanySection) entry.getValue()).getListPosition();
                    for (Company company : listCompany) {
                        resumeStr.append("\n").append(company.toString());
                    }
                    break;
                }
            }
        }
        return resumeStr.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int compareName = fullName.compareTo(o.fullName);
        return compareName != 0 ? compareName : uuid.compareTo(o.uuid);
    }
}
