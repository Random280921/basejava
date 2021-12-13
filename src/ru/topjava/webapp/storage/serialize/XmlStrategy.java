package ru.topjava.webapp.storage.serialize;

import ru.topjava.webapp.model.*;
import ru.topjava.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс XmlStrategy -- инструмент конвертации Resume в/из xml
 *
 * @author KAIvanov
 * created by 13.12.2021 21:05
 * @version 1.0
 */
public class XmlStrategy implements Strategy {
    private final XmlParser xmlParser;

    public XmlStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Contact.class, Experience.class, ContactType.class,
                CompanySection.class, TextSection.class, SectionType.class, Company.class);
    }

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
