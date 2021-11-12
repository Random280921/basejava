package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;
import ru.topjava.webapp.model.SearchKey;

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
    protected Resume getResume(SearchKey searchKey) {
        return storage.get(searchKey.getKey());
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int findIndex(String uuid) {
        return storage.containsKey(uuid) ? 1 : -1;
    }

    @Override
    protected void saveResume(Resume resume, SearchKey searchKey) {
        storage.put(searchKey.getKey(), resume);
    }

    @Override
    protected void deleteResume(SearchKey searchKey) {
        storage.remove(searchKey.getKey());
    }

    @Override
    public final void updateResume(Resume resume, SearchKey searchKey) {
        storage.replace(searchKey.getKey(), resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }
}
