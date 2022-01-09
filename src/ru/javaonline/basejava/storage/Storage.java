package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.model.Resume;

import java.util.List;
import java.util.logging.Logger;

/**
 * Storage for Resumes
 */
public interface Storage {
    Logger LOG = Logger.getLogger("StorageLog");

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

    /**
     * Вспомогательный метод, для сокращения общего кода в методах
     * Проверяет входной параметр на null и логирует
     */
    default void logCheckToNull(String operation, Object object, String objectName) {
        LOG.info(operation + " " + ((object == null) ? "null"
                : ((object instanceof Resume) ?
                "Resume: uuid= " + ((Resume) object).getUuid()
                : "uuid= " + object)));
        if (object == null) {
            LOG.severe(objectName + " is not be null!");
            throw new NullPointerException();
        }
    }
}
