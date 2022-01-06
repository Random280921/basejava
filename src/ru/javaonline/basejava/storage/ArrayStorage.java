package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findKey(String uuid) {
        for (int index = 0; index < size; index++) {
            if (uuid.equals(storage[index].getUuid())) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void saveResumeToStorage(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        storage[index] = storage[size];
    }

}
