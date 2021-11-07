package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.ExistStorageException;
import ru.topjava.webapp.exception.NotExistStorageException;
import ru.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    public int size() {
        return storage.size();
    }

    public final Resume get(String uuid) {
        String checkUuid = checkUuidToNull(uuid);
        for (Resume resume : storage) {
            if (Objects.equals(resume.getUuid(), checkUuid)) {
                return resume;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    public final void clear() {
        storage.clear();
    }

    public final void save(Resume resume) {
        String uuidRes = checkUuidToNull(resume.getUuid());
        int index = storage.indexOf(resume);
        if (index >= 0) {
            throw new ExistStorageException(uuidRes, index);
        } else storage.add(resume);
    }

    public final void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else
            storage.set(index, resume);
    }

    public final void delete(String uuid) {
        String checkUuid = checkUuidToNull(uuid);
        Iterator<Resume> iterator = storage.iterator();
        int cnt = 0;
        while (iterator.hasNext()) {
            cnt++;
            Resume r = iterator.next();
            System.out.println(r);
            if (Objects.equals(r.getUuid(), checkUuid)) {
                iterator.remove();
                break;
            }
        }
        if (cnt == storage.size()) throw new NotExistStorageException(uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
