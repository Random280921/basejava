package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
    protected void saveResumeToArray(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResumeFromArray(int index) {
        storage[index] = storage[size];
    }

}
