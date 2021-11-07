package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getResumeForIndex(int index) {
        return storage[index];
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void checkSizeToLimitArray(String uuidRes) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище уже заполнено - резюме невозможно сохранить!", uuidRes);
        }
    }

    @Override
    public final void updateResumeToStorage(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public final void deleteResumeAndTrimStorage(int index) {
        size--;
        deleteResumeFromStorage(index);
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }
}
