package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void updateResumeToStorage(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public final void deleteResume(int index) {
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
