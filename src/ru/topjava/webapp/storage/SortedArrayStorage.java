package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void saveResumeToStorage(Resume resume, int index) {
        int key = -index - 1;
        System.arraycopy(storage, key, storage, key + 1, size - key);
        storage[key] = resume;
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}
