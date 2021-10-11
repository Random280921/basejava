package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public int size() {
        return size;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int index = 0; index < size; index++) {
            if (uuid.equals(storage[index].getUuid())) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void saveResumeToIndex(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResumeFromIndex(int index) {
        storage[index] = storage[size];
    }

}
