package ru.javaonline.basejava.storage.serialize;

import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс XmlStrategy -- инструмент конвертации Resume в/из xml
 *
 * @author KAIvanov
 * created by 13.12.2021 21:05
 * @version 1.0
 */
public class JsonStrategy implements Strategy {
    @Override
    public void writeResume(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(r, writer);
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
