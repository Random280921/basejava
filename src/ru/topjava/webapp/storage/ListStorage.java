package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();
    ListIterator<Resume> iterator = storage.listIterator();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int findIndex(String uuid) {
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return storage.indexOf(resume);
            }
        }
        return -1;
    }

    @Override
    protected void saveResumeToStorage(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        storage.remove(index);
    }

    @Override
    public final void updateResumeToStorage(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public final void deleteResume(int index) {
        deleteResumeFromStorage(index);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
