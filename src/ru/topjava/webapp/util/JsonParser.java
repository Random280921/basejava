package ru.topjava.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.topjava.webapp.model.AbstractSection;

import java.io.Reader;
import java.io.Writer;

/**
 * Класс JsonParser -- сериализация json
 *
 * @author KAIvanov
 * created by 15.12.2021 15:21
 * @version 1.0
 */
public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter<AbstractSection>())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
