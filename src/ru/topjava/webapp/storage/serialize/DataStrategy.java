package ru.topjava.webapp.storage.serialize;

import ru.topjava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Класс  DataStrategy -- стратегия сериализации DataStream
 *
 * @author KAIvanov
 * created by 15.12.2021 21:10
 * @version 1.0
 */
public class DataStrategy implements Strategy {
    private static final DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, Contact> contacts = resume.getHeader();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeContact(entry.getValue(), dos);
            }
            Map<SectionType, AbstractSection> sections = resume.getBody();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType typeSect = entry.getKey();
                dos.writeUTF(typeSect.name());
                writeSection(typeSect, entry.getValue(), dos);
            }
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), readContact(dis.readUTF(), dis.readUTF()));
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                readSection(SectionType.valueOf(dis.readUTF()), resume, dis);
            }
            return resume;
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * запись контакта
     */
    private void writeContact(Contact contact, DataOutputStream dos) throws IOException {
        dos.writeUTF(contact.getValue());
        String url = contact.getUrl();
        dos.writeUTF((url == null) ? "NULL" : url);
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение контакта
     */
    private Contact readContact(String contact, String url) {
        return new Contact(contact, ("NULL".equals(url)) ? null : url);
    }

    /**
     * вспомогательный метод для сокращения кода
     * запись секции
     */
    private void writeSection(SectionType sectionType, AbstractSection section, DataOutputStream dos) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                writeTextBlock(section, dos);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeTextList(section, dos);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCompanyList(section, dos);
                break;
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение секции
     */
    private void readSection(SectionType sectionType, Resume resume, DataInputStream dis) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                readTextBlock(sectionType, resume, dis);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                readTextList(sectionType, resume, dis);
                break;
            case EXPERIENCE:
            case EDUCATION:
                readCompanyList(sectionType, resume, dis);
                break;
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * запись списка текста
     */
    private void writeTextBlock(AbstractSection section, DataOutputStream dos) throws IOException {
        String blockPosition = ((TextBlockSection) section).getBlockPosition();
        dos.writeUTF(blockPosition);
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение списка текста
     */
    private void readTextBlock(SectionType sectionType, Resume resume, DataInputStream dis) throws IOException {
        resume.addSection(sectionType, new TextBlockSection());
        ((TextBlockSection) resume.getBody().get(sectionType)).addBlockPosition(dis.readUTF());
    }

    /**
     * вспомогательный метод для сокращения кода
     * запись списка текста
     */
    private void writeTextList(AbstractSection section, DataOutputStream dos) throws IOException {
        List<String> list = ((TextListSection) section).getListPosition();
        dos.writeInt(list.size());
        for (String s : list) {
            dos.writeUTF(s);
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение списка текста
     */
    private void readTextList(SectionType sectionType, Resume resume, DataInputStream dis) throws IOException {
        int cnt = dis.readInt();
        if (cnt > 0) {
            resume.addSection(sectionType, new TextListSection());
            for (int i = 0; i < cnt; i++) {
                ((TextListSection) resume.getBody().get(sectionType)).addListPosition(dis.readUTF());
            }
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * запись списка компаний
     */
    private void writeCompanyList(AbstractSection section, DataOutputStream dos) throws IOException {
        List<Company> list = ((CompanySection) section).getListPosition();
        dos.writeInt(list.size());
        for (Company company : list) {
            writeContact(company.getCompanyName(), dos);
            List<Company.Experience> setExp = company.getExperienceList();
            dos.writeInt(setExp.size());
            for (Company.Experience exp : setExp) {
                writeDate(dos, exp.getDateFrom());
                writeDate(dos, exp.getDateTo());
                dos.writeUTF(exp.getPositionTitle());
                String positionText = exp.getPositionText();
                dos.writeUTF((positionText == null) ? "NULL" : positionText);
            }
        }
    }

    /**
     * вспомогательный метод для уникальности кода
     * запись даты
     */
    private void writeDate(DataOutputStream dos, LocalDate dt) throws IOException {
        dos.writeUTF(dt.format(PATTERN_DATE));
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение списка компаний
     */
    private void readCompanyList(SectionType sectionType, Resume resume, DataInputStream dis) throws IOException {
        int cnt = dis.readInt();
        if (cnt > 0) {
            int cntEx;
            resume.addSection(sectionType, new CompanySection());
            for (int i = 0; i < cnt; i++) {
                Contact contact = readContact(dis.readUTF(), dis.readUTF());
                Company company = new Company(contact.getValue(), contact.getUrl());
                cntEx = dis.readInt();
                for (int j = 0; j < cntEx; j++) {
                    LocalDate dateFrom = readDate(dis.readUTF());
                    LocalDate dateTo = readDate(dis.readUTF());
                    String posTitle = dis.readUTF();
                    String posText = dis.readUTF();
                    company.addExperience(dateFrom, dateTo, posTitle, ("NULL".equals(posText)) ? null : posText);
                }
                ((CompanySection) resume.getBody().get(sectionType)).addListPosition(company);
            }
        }
    }

    /**
     * вспомогательный метод для уникальности кода
     * конвертация строки в дату
     */
    private LocalDate readDate(String read) {
        return LocalDate.parse(read, PATTERN_DATE);
    }
}
