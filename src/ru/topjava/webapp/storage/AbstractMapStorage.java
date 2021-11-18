package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map based storage for Resumes
 */
public abstract class AbstractMapStorage extends AbstractStorage {

    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected abstract Resume getResume(Object searchKey);

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected abstract Object findKey(String uuid);

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected abstract void deleteResume(Object searchKey);

    @Override
    public final void updateResume(Resume resume, Object searchKey) {
        storage.replace(resume.getUuid(), resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> convertToList() {
        return new ArrayList<>(storage.values());
    }
}
