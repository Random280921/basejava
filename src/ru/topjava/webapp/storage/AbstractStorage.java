package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.ExistStorageException;
import ru.topjava.webapp.exception.NotExistStorageException;
import ru.topjava.webapp.model.Resume;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    protected Object abstractStorage;

    protected AbstractStorage(Object storage) {
        this.abstractStorage = storage;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return (abstractStorage instanceof Map) ? getResume(uuid) : getResume(index);
    }

    public final void save(Resume resume) {
        String uuidRes = resume.getUuid();
        int index = getIndex(uuidRes);
        if (index >= 0) {
            throw new ExistStorageException(uuidRes, index);
        }
        saveResume(resume, abstractStorage instanceof Map ? uuidRes : Integer.valueOf(index));
    }

    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateResume(resume, abstractStorage instanceof Map ? uuid : Integer.valueOf(index));
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(abstractStorage instanceof Map ? uuid : Integer.valueOf(index));
    }

    /**
     * @return uuid or NullPointerException
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет входной параметр uuid на null
     */
    protected String checkUuidToNull(String uuid) {
        return Objects.requireNonNull(uuid, "Resume.uuid must not be null");
    }

    /**
     * @return Resume from storage for input index
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает резюме по индексу
     */
    protected abstract Resume getResume(Object inky);

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
    protected abstract void saveResume(Resume resume, Object inky);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища сохраняем резюме
     */
    protected abstract void updateResume(Resume resume, Object inky);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме и меняем размер хранилища (если требуется)
     */
    protected abstract void deleteResume(Object inky);

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает полученный индекс хранилища, с предварительной проверкой uuid на null
     */
    private int getIndex(String uuid) {
        return findIndex(checkUuidToNull(uuid));
    }
}
