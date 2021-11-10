package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.*;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    public ListStorage() {
        super(new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    private final List<Resume> storage = (ArrayList<Resume>) abstractStorage;

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(Object inky) {
        return storage.get((int) inky);
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
    protected void saveResume(Resume resume, Object inky) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(Object inky) {
        storage.remove((int) inky);
    }

    @Override
    public final void updateResume(Resume resume, Object inky) {
        storage.set((int) inky, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
