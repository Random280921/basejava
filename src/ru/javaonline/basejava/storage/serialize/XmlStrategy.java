package ru.javaonline.basejava.storage.serialize;

import ru.javaonline.basejava.model.*;
import ru.topjava.webapp.model.*;
import ru.javaonline.basejava.util.XmlParser;

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
                Resume.class, Contact.class, Company.Experience.class, ContactType.class,
                CompanySection.class, TextBlockSection.class, TextListSection.class, SectionType.class, Company.class);
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
