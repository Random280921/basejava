package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResumeToIndex(Resume resume) {
        int key = Arrays.binarySearch(storage, 0, size, resume);
        key = -key - 1;
        Resume[] partStorage = new Resume[size - key];
        System.arraycopy(storage, key, partStorage, 0, partStorage.length);
        storage[key] = resume;
        System.arraycopy(partStorage, 0, storage, key + 1, partStorage.length);
    }

    @Override
    protected void deleteResumeFromIndex(int index) {
        for (int i = index; i < size-1; i++) {
            storage[i]=storage[i+1];
        }
    }
}
