package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Map based storage for Resumes
 */
public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected Object findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : -1;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(searchKey.toString());
    }
}
