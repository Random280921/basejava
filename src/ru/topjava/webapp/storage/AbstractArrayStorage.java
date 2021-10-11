package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

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

    public final Resume get(String uuid) {
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

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
            return;
        }
        if (checkUuidToNull(resume.getUuid(), "save")) {
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            saveResumeToIndex(resume);
            size++;
        } else
            System.out.printf("Такое резюме уже есть: Id storage= %d\n", index);
    }

    public final void update(Resume resume) {
        if (checkUuidToNull(resume.getUuid(), "update")) {
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для обновления в хранилище нет!\n", resume.getUuid());
        } else
            storage[index] = resume;
    }

    public final void delete(String uuid) {
        if (checkUuidToNull(uuid, "delete")) {
            return;
        }
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для удаления в хранилище нет!\n", uuid);
            return;
        }
        deleteResumeFromIndex(index);
        storage[size] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет входной параметр uuid на null
     */
    protected boolean checkUuidToNull(String uuid, String oper) {
        if (uuid == null) {
            System.out.printf("uuid invalid - don't %s resume!\n", oper);
            return true;
        }
        return false;
    }

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища, где лежит резюме
     * Если резюме не найдено, возвращает -1
     */
    protected abstract int findIndex(String uuid);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища сохраняем резюме
     */
    protected abstract void saveResumeToIndex(Resume resume);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме
     */
    protected abstract void deleteResumeFromIndex(int index);
}
