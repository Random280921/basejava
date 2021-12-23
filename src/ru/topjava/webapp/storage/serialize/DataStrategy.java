package ru.topjava.webapp.storage.serialize;

import ru.topjava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс  DataStrategy -- стратегия сериализации DataStream
 *
 * @author KAIvanov
 * created by 15.12.2021 21:10
 * @version 1.0
 */
public class DataStrategy implements Strategy {
    private static final DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static class ObjectData<K, V> {
        private final K key;
        private final V val;

        private ObjectData(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private <K, V> List<ObjectData> getListObjectData(Map<K, V> map) {
        return map.entrySet().stream().map(m -> new ObjectData(m.getKey(), m.getValue())).collect(Collectors.toList());
    }

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            List<ObjectData> contacts = getListObjectData(resume.getHeader());
            writeWithException(contacts, dos, entry -> {
                dos.writeUTF(((ContactType) entry.key).name());
                writeContact(((Contact) entry.val), dos);
            });
            List<ObjectData> sections = getListObjectData(resume.getBody());
            writeWithException(sections, dos, entry -> {
                SectionType typeSect = (SectionType) entry.key;
                dos.writeUTF(typeSect.name());
                writeSection(typeSect, ((AbstractSection) entry.val), dos);
            });
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis,
                    () -> {
                    },
                    () -> resume.addContact(ContactType.valueOf(dis.readUTF()), readContact(dis.readUTF(), dis.readUTF())));
            readWithException(dis,
                    () -> {
                    },
                    () -> readSection(SectionType.valueOf(dis.readUTF()), resume, dis));
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
                dos.writeUTF(((TextBlockSection) section).getBlockPosition());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeWithException(((TextListSection) section).getListPosition(), dos, dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeWithException(((CompanySection) section).getListPosition(), dos, company -> {
                    writeContact(company.getCompanyName(), dos);
                    writeWithException(company.getExperienceList(), dos, exp -> {
                        writeDate(dos, exp.getDateFrom());
                        writeDate(dos, exp.getDateTo());
                        dos.writeUTF(exp.getPositionTitle());
                        String positionText = exp.getPositionText();
                        dos.writeUTF((positionText == null) ? "NULL" : positionText);
                    });
                });
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
                resume.addSection(sectionType, new TextBlockSection(dis.readUTF()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                readWithException(dis, () -> resume.addSection(sectionType, new TextListSection()), () -> ((TextListSection) resume.getBody().get(sectionType)).addListPosition(dis.readUTF()));
                break;
            case EXPERIENCE:
            case EDUCATION:
                readWithException(dis, () -> resume.addSection(sectionType, new CompanySection()), () -> {
                    Contact contact = readContact(dis.readUTF(), dis.readUTF());
                    Company company = new Company(contact.getValue(), contact.getUrl());
                    readWithException(dis, () -> {
                    }, () -> {
                        LocalDate dateFrom = readDate(dis.readUTF());
                        LocalDate dateTo = readDate(dis.readUTF());
                        String posTitle = dis.readUTF();
                        String posText = dis.readUTF();
                        company.addExperience(dateFrom, dateTo, posTitle, ("NULL".equals(posText)) ? null : posText);
                    });
                    ((CompanySection) resume.getBody().get(sectionType)).addListPosition(company);
                });
                break;
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
     * вспомогательный метод для уникальности кода
     * конвертация строки в дату
     */
    private LocalDate readDate(String read) {
        return LocalDate.parse(read, PATTERN_DATE);
    }

    @FunctionalInterface
    private interface WriteConsumer<T> {
        void accept(T t) throws IOException;
    }

    @FunctionalInterface
    private interface ReadConsumer {
        void apply() throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriteConsumer<T> writeConsumer) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(writeConsumer);
        dos.writeInt(collection.size());
        for (T t : collection) {
            writeConsumer.accept(t);
        }
    }

    private void readWithException(DataInputStream dis, ReadConsumer readConsumer1, ReadConsumer readConsumer2) throws IOException {
        Objects.requireNonNull(readConsumer1);
        Objects.requireNonNull(readConsumer2);
        int cnt = dis.readInt();
        if (cnt > 0) {
            readConsumer1.apply();
            for (int i = 0; i < cnt; i++) {
                readConsumer2.apply();
            }
        }
    }
}
