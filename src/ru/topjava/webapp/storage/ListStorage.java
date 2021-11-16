package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;
import ru.topjava.webapp.model.SearchKey;

import java.util.*;
import java.util.stream.Collectors;

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
    protected Resume getResume(SearchKey searchKey) {
        return storage.get(searchKey.getIndex());
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
    protected void saveResume(Resume resume, SearchKey searchKey) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(SearchKey searchKey) {
        storage.remove(searchKey.getIndex());
    }

    @Override
    public final void updateResume(Resume resume, SearchKey searchKey) {
        storage.set(searchKey.getIndex(), resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> getAllSorted() {
        return storage.stream().sorted(RESUME_COMPARATOR).collect(Collectors.toList());
    }
}
