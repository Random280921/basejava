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
    protected Resume getResume(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected Object findKey(String uuid) {
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
    protected void saveResume(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(storage.get((Integer) searchKey));
    }

    @Override
    public final void updateResume(Resume resume, Object searchKey) {
        storage.set((Integer) searchKey, resume);
    }

    /**
     * @return sorted list, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> convertToList() {
        return storage;
    }
}
