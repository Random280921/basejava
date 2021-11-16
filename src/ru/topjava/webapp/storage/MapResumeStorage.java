package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Map based storage for Resumes
 */
public class MapResumeStorage extends MapStorage {

    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? storage.get(uuid) : -1;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }
}
