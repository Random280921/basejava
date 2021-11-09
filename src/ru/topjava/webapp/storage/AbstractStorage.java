package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.ExistStorageException;
import ru.topjava.webapp.exception.NotExistStorageException;
import ru.topjava.webapp.model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {

    public final Resume get(String uuid) {
        int index = findIndex(checkUuidToNull(uuid));
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index, uuid);
    }

    public final void save(Resume resume) {
        String uuidRes = checkUuidToNull(resume.getUuid());
        int index = findIndex(uuidRes);
        if (index >= 0) {
            throw new ExistStorageException(uuidRes, index);
        }
        saveResume(resume, index, uuidRes);
    }

    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(checkUuidToNull(uuid));
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateResume(resume, index, uuid);
    }

    public final void delete(String uuid) {
        int index = findIndex(checkUuidToNull(uuid));
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(index, uuid);
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
    protected abstract Resume getResume(int index, String key);

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
    protected abstract void saveResume(Resume resume, int index, String key);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища сохраняем резюме
     */
    protected abstract void updateResume(Resume resume, int index, String key);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме и меняем размер хранилища (если требуется)
     */
    protected abstract void deleteResume(int index, String key);
}
