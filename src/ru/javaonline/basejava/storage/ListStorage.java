package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        LOG.info("size");
        return storage.size();
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public final void clear() {
        LOG.info("clear");
        storage.clear();
    }

    @Override
    protected Integer findKey(String uuid) {
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
    protected void saveResume(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        storage.remove(storage.get(searchKey));
    }

    @Override
    public final void updateResume(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    /**
     * @return sorted list, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> convertToList() {
        return new ArrayList<>(storage);
    }
}
