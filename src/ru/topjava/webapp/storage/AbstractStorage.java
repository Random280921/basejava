package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.ExistStorageException;
import ru.topjava.webapp.exception.NotExistStorageException;
import ru.topjava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractStorage implements Storage {

    protected static Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

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

    public final List<Resume> getAllSorted() {
        List<Resume> list = convertStorage();
        return list.stream().sorted(RESUME_COMPARATOR).collect(Collectors.toList());
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
    protected abstract Resume getResume(Object searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища (для индексированных хранилищ, иначе возвращает 1), где лежит резюме
     * Если резюме не найдено, возвращает -1
     */
    protected abstract Object findKey(String uuid);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища сохраняем резюме
     */
    protected abstract void saveResume(Resume resume, Object searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища сохраняем резюме
     */
    protected abstract void updateResume(Resume resume, Object searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному ключу хранилища удаляем резюме и меняем размер хранилища (если требуется)
     */
    protected abstract void deleteResume(Object searchKey);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Конвертирует storage в List
     */
    protected abstract List<Resume> convertStorage();

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Возвращает полученный ключ хранилища или нужное исключение, с предварительной проверкой uuid на null
     */
    private Object getKey(String uuid, int checkType) {
        Object searchKey = findKey(checkUuidToNull(uuid));
        try {
            int index = Integer.parseInt(searchKey.toString());
            if (index < 0 && checkType == -1) throw new NotExistStorageException(uuid);
            if (index >= 0 && checkType == 1) throw new ExistStorageException(uuid, String.valueOf(index));
        } catch (NumberFormatException n) {
            if (checkType == 1) throw new ExistStorageException(uuid, uuid);
            return searchKey;
        }
        return (searchKey == "-1") ? uuid : searchKey;
    }
}
