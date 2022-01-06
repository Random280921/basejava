package ru.javaonline.basejava.util;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Класс JsonSectionAdapter -- адаптер сериализации для AbstractSection
 *
 * @author KAIvanov
 * created by 15.12.2021 15:01
 * @version 1.0
 */
public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        try {
            Class<?> clazz = Class.forName(jsonObject.get(CLASSNAME).getAsString());
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, section.getClass().getName());
        retValue.add(INSTANCE, context.serialize(section));
        return retValue;
    }
}
