package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;
import ru.topjava.webapp.model.SearchKey;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getResume(SearchKey searchKey) {
        return storage[searchKey.getIndex()];
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume resume, SearchKey searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище уже заполнено - резюме невозможно сохранить!", resume.getUuid());
        }
        saveResumeToStorage(resume, searchKey.getIndex());
        size++;
    }

    @Override
    public final void updateResume(Resume resume, SearchKey searchKey) {
        storage[searchKey.getIndex()] = resume;
    }

    @Override
    public final void deleteResume(SearchKey searchKey) {
        size--;
        deleteResumeFromStorage(searchKey.getIndex());
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> getAllSorted() {
        return Arrays.stream(Arrays.copyOfRange(storage, 0, size)).sorted(RESUME_COMPARATOR).collect(Collectors.toList());
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища сохраняем резюме
     */
    protected abstract void saveResumeToStorage(Resume resume, int index);

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * По заданному индексу хранилища удаляем резюме
     */
    protected abstract void deleteResumeFromStorage(int index);

}
