package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.model.Resume;

import java.util.List;

/**
 * Storage for Resumes
 */
public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
