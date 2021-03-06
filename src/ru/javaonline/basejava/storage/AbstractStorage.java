package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.ExistStorageException;
import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Resume;

import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    public final Resume get(String uuid) {
        logCheckToNull("Get", uuid, "uuid");
        return getResume(getKey(uuid, -1));
    }

    public final void save(Resume resume) {
        logCheckToNull("Save", resume, "Resume");
        saveResume(resume, getKey(resume.getUuid(), 1));
    }

    public final void update(Resume resume) {
        logCheckToNull("Update", resume, "Resume");
        updateResume(resume, getKey(resume.getUuid(), -1));
    }

    public final void delete(String uuid) {
        logCheckToNull("Delete", uuid, "uuid");
        deleteResume(getKey(uuid, -1));
    }

    public final List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = convertToList();
        list.sort(Resume::compareTo);
        return list;
    }

    /**
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет на существование в хранилище и логирует
     */
    private void logCheckToExist(String uuid, String key) {
        LOG.severe(String.format("Такое резюме %s уже есть: Key storage= %s\n", uuid, key));
        throw new ExistStorageException(uuid, key);
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает резюме по ключу
     */
    protected abstract Resume getResume(SK searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища (для индексированных хранилищ, иначе возвращает 1), где лежит резюме
     * Если резюме не найдено, возвращает -1
     */
    protected abstract SK findKey(String uuid);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища сохраняем резюме
     */
    protected abstract void saveResume(Resume resume, SK searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища сохраняем резюме
     */
    protected abstract void updateResume(Resume resume, SK searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища удаляем резюме и меняем размер хранилища (если требуется)
     */
    protected abstract void deleteResume(SK searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Конвертирует storage в List
     */
    protected abstract List<Resume> convertToList();

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает полученный ключ хранилища или нужное исключение, с предварительной проверкой uuid на null
     */
    private SK getKey(String uuid, int checkType) {
        SK searchKey = findKey(uuid);
        try {
            int index = (searchKey == null) ? -1 : Integer.parseInt(searchKey.toString());
            if (index < 0 && checkType == -1) {
                LOG.severe(String.format("Такого резюме (uuid = %s) в хранилище нет!\n", uuid));
                throw new NotExistStorageException(uuid);
            }
            if (index >= 0 && checkType == 1) logCheckToExist(uuid, String.valueOf(index));
        } catch (NumberFormatException n) {
            if (checkType == 1) logCheckToExist(uuid, uuid);
        }
        return searchKey;
    }
}
