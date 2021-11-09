package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.*;

/**
 * Map based storage for Resumes
 */
public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(int index, String key) {
        return storage.get(key);
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int findIndex(String uuid) {
        if (storage.containsKey(uuid)) return 1;
        return -1;
    }

    @Override
    protected void saveResume(Resume resume, int index, String key) {
        storage.put(key, resume);
    }

    @Override
    protected void deleteResume(int index, String key) {
        storage.remove(key);
    }

    @Override
    public final void updateResume(Resume resume, int index, String key) {
        storage.replace(key, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }
}
