package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        if (checkUuidToNull(uuid, "get")) {
            return null;
        }
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для получения в хранилище нет!\n", uuid);
            return null;
        }
        return storage[index];
    }

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища, где лежит резюме
     * Если резюме не найдено, возвращает -1
     */
    protected abstract int findIndex(String uuid);

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет входной параметр uuid на null
     */
    protected boolean checkUuidToNull(String uuid, String oper) {
        if (uuid == null) {
            System.out.printf("uuid invalid - don't %s resume!", oper);
            return true;
        }
        return false;
    }
}
