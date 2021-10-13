package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

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
        int index = findIndex(checkUuidToNull(uuid));
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
        int index = findIndex(checkUuidToNull(resume.getUuid()));
        if (index < 0) {
            saveResumeToArray(resume);
            size++;
        } else
            System.out.printf("Такое резюме уже есть: Id storage= %d\n", index);
    }

    public final void update(Resume resume) {
        int index = findIndex(checkUuidToNull(resume.getUuid()));
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для обновления в хранилище нет!\n", resume.getUuid());
        } else
            storage[index] = resume;
    }

    public final void delete(String uuid) {
        int index = findIndex(checkUuidToNull(uuid));
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для удаления в хранилище нет!\n", uuid);
            return;
        }
        size--;
        deleteResumeFromArray(index);
        storage[size] = null;
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
    protected String checkUuidToNull(String uuid) {
        return Objects.requireNonNull(uuid, "Resume.uuid must not be null");
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
    protected abstract void saveResumeToArray(Resume resume);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме
     */
    protected abstract void deleteResumeFromArray(int index);
}
