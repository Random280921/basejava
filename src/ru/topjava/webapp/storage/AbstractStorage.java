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
        return getResumeForIndex(index);
    }

    public final void save(Resume resume) {
        String uuidRes = checkUuidToNull(resume.getUuid());
        checkSizeToLimitArray(uuidRes);
        int index = findIndex(uuidRes);
        if (index < 0) {
            saveResumeToStorage(resume, index);
        } else
            throw new ExistStorageException(uuidRes, index);
    }

    public final void update(Resume resume) {
        int index = findIndex(checkUuidToNull(resume.getUuid()));
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else
            updateResumeToStorage(resume, index);
    }

    public final void delete(String uuid) {
        int index = findIndex(checkUuidToNull(uuid));
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResumeAndTrimStorage(index);
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Проверяет хранилище на переполнение (там, где критично) - при перполнении выбрасывает StorageException
     */
    protected abstract void checkSizeToLimitArray(String uuid);

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
    protected abstract Resume getResumeForIndex(int index);

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
    protected abstract void saveResumeToStorage(Resume resume, int index);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища сохраняем резюме
     */
    protected abstract void updateResumeToStorage(Resume resume, int index);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме
     */
    protected abstract void deleteResumeFromStorage(int index);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме и меняем размер хранилища (если требуется)
     */
    protected abstract void deleteResumeAndTrimStorage(int index);
}
