package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.model.Resume;

/**
 * Map based storage for Resumes
 */
public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected String findKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getResume(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteResume(String searchKey) {
        storage.remove(searchKey);
    }
}
