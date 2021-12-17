package ru.topjava.webapp.storage.serialize;

import ru.topjava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
            Map<SectionType, AbstractSection> sections = resume.getBody();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeContact(entry.getValue(), dos);
            }
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
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    resume.addContact(ContactType.valueOf(dis.readUTF()), readContact(dis.readUTF(), dis.readUTF()));
                }
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
        dos.writeUTF((contact.getUrl() == null) ? "NULL" : contact.getUrl());
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
        int numSect = sectionType.ordinal();
        if (numSect < 2) {
            String blockPosition = ((TextSection) section).getBlockPosition();
            dos.writeUTF((blockPosition == null) ? "NULL" : blockPosition);
        } else if (numSect < 4) writeTextList(section, dos);
        else writeCompanyList(section, dos);
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение секции
     */
    private void readSection(SectionType sectionType, Resume resume, DataInputStream dis) throws IOException {
        int numSect = sectionType.ordinal();
        if (numSect < 2) {
            String blockPosition = dis.readUTF();
            if (!"NULL".equals(blockPosition))
                ((TextSection) resume.getBody().get(sectionType)).addBlockPosition(blockPosition);
        } else if (numSect < 4) readTextList(sectionType, resume, dis);
        else readCompanyList(sectionType, resume, dis);
    }

    /**
     * вспомогательный метод для сокращения кода
     * запись списка текста
     */
    private void writeTextList(AbstractSection section, DataOutputStream dos) throws IOException {
        List<String> list = ((TextSection) section).getListPosition();
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
            for (int i = 0; i < cnt; i++) {
                ((TextSection) resume.getBody().get(sectionType)).addListPosition(dis.readUTF());
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
            TreeSet<Experience> setExp = company.getExperienceSet();
            dos.writeInt(setExp.size());
            for (Experience exp : setExp) {
                dos.writeUTF(exp.getDateFrom().format(PATTERN_DATE));
                dos.writeUTF(exp.getDateTo().format(PATTERN_DATE));
                dos.writeUTF(exp.getPositionTitle());
                dos.writeUTF((exp.getPositionText() == null) ? "NULL" : exp.getPositionText());
            }
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение списка компаний
     */
    private void readCompanyList(SectionType sectionType, Resume resume, DataInputStream dis) throws IOException {
        int cnt = dis.readInt();
        if (cnt > 0) {
            int cntEx;
            for (int i = 0; i < cnt; i++) {
                Contact contact = readContact(dis.readUTF(), dis.readUTF());
                Company company = new Company(contact.getValue(), contact.getUrl());
                cntEx = dis.readInt();
                if (cntEx > 0) {
                    for (int j = 0; j < cntEx; j++) {
                        LocalDate dateFrom = LocalDate.parse(dis.readUTF(), PATTERN_DATE);
                        LocalDate dateTo = LocalDate.parse(dis.readUTF(), PATTERN_DATE);
                        String posTitle = dis.readUTF();
                        String posText = dis.readUTF();
                        company.addExperience(dateFrom, dateTo, posTitle, ("NULL".equals(posText)) ? null : posText);
                    }
                }
                ((CompanySection) resume.getBody().get(sectionType)).addListPosition(company);
            }
        }
    }
}
