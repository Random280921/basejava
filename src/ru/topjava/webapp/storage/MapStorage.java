package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.*;

/**
 * Map based storage for Resumes
 */
public class MapStorage extends AbstractStorage {

    public MapStorage() {
        super(new HashMap<String, Resume>());
    }

    @SuppressWarnings("unchecked")
    private final Map<String, Resume> storage = (HashMap<String, Resume>) abstractStorage;

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Object inky) {
        return storage.get((String) inky);
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int findIndex(String uuid) {
        return (storage.containsKey(uuid)) ? 1 : -1;
    }

    @Override
    protected void saveResume(Resume resume, Object inky) {
        storage.put((String) inky, resume);
    }

    @Override
    protected void deleteResume(Object inky) {
        storage.remove((String) inky);
    }

    @Override
    public final void updateResume(Resume resume, Object inky) {
        storage.replace((String) inky, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }
}
