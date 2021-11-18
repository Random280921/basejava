package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    protected Resume getResume(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище уже заполнено - резюме невозможно сохранить!", resume.getUuid());
        }
        saveResumeToStorage(resume, (Integer) searchKey);
        size++;
    }

    @Override
    public final void updateResume(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    public final void deleteResume(Object searchKey) {
        size--;
        deleteResumeFromStorage((Integer) searchKey);
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final List<Resume> convertToList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
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
