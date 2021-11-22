package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        LOG.info("size");
        return size;
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    public final void clear() {
        LOG.info("clear");
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume resume, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            String message = "Хранилище уже заполнено - резюме невозможно сохранить!";
            LOG.severe(message);
            throw new StorageException(message, resume.getUuid());
        }
        saveResumeToStorage(resume, searchKey);
        size++;
    }

    @Override
    public final void updateResume(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    public final void deleteResume(Integer searchKey) {
        size--;
        deleteResumeFromStorage(searchKey);
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> convertToList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища сохраняем резюме
     */
    protected abstract void saveResumeToStorage(Resume resume, int index);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме
     */
    protected abstract void deleteResumeFromStorage(int index);

}
