package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.ExistStorageException;
import ru.topjava.webapp.exception.NotExistStorageException;
import ru.topjava.webapp.model.Resume;
import ru.topjava.webapp.model.SearchKey;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {

    public final Resume get(String uuid) {
        return getResume(getKey(uuid, -1));
    }

    public final void save(Resume resume) {
        saveResume(resume, getKey(resume.getUuid(), 1));
    }

    public final void update(Resume resume) {
        updateResume(resume, getKey(resume.getUuid(), -1));
    }

    public final void delete(String uuid) {
        deleteResume(getKey(uuid, -1));
    }

    /**
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет входной параметр uuid на null
     */
    private String checkUuidToNull(String uuid) {
        return Objects.requireNonNull(uuid, "Resume.uuid must not be null");
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает резюме по ключу
     */
    protected abstract Resume getResume(SearchKey searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища (для индексированных хранилищ, иначе возвращает 1), где лежит резюме
     * Если резюме не найдено, возвращает -1
     */
    protected abstract int findIndex(String uuid);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища сохраняем резюме
     */
    protected abstract void saveResume(Resume resume, SearchKey searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища сохраняем резюме
     */
    protected abstract void updateResume(Resume resume, SearchKey searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища удаляем резюме и меняем размер хранилища (если требуется)
     */
    protected abstract void deleteResume(SearchKey searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает полученный ключ хранилища или нужное исключение, с предварительной проверкой uuid на null
     */
    private SearchKey getKey(String uuid, int checkType) {
        SearchKey searchKey = new SearchKey(findIndex(checkUuidToNull(uuid)), uuid);
        int index = searchKey.getIndex();
        if (index < 0 && checkType == -1) {
            throw new NotExistStorageException(uuid);
        }
        if (index >= 0 && checkType == 1) {
            throw new ExistStorageException(uuid, searchKey.getIndex());
        }
        return searchKey;
    }
}
