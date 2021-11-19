package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Map based storage for Resumes
 */
public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected Resume findKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return storage.get(searchKey.getUuid());
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }
}
