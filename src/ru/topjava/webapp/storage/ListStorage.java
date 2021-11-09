package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(int index, String key) {
        return storage.get(index);
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int findIndex(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), uuid)) {
                return iterator.previousIndex();
            }
        }
        return -1;
    }

    @Override
    protected void saveResume(Resume resume, int index, String key) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(int index, String key) {
        storage.remove(index);
    }

    @Override
    public final void updateResume(Resume resume, int index, String key) {
        storage.set(index, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
