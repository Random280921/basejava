package ru.topjava.webapp.storage;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    /**
     * @return uuid or NullPointerException
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет входной параметр uuid на null
     */
    protected String checkUuidToNull(String uuid) {
        return Objects.requireNonNull(uuid, "Resume.uuid must not be null");
    }
}
