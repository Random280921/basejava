package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected final File directory;
    protected File[] listFiles;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected abstract Resume getResume(File file);

    @Override
    protected File findKey(String uuid) {
        try {
            return new File(directory.getCanonicalPath(), uuid);
        } catch (IOException e) {
            throw new StorageException("IO error findKey", uuid, e);
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            if (file.createNewFile())
                writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error saveResume", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error updateResume", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete())
            throw new StorageException(String.format("Do not file %s delete", file.getName()), file.getName());
    }

    @Override
    protected List<Resume> convertToList() {
        List<Resume> listResume = new ArrayList<>();
        listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                try {
                    listResume.add(readResume(file));
                } catch (IOException e) {
                    throw new StorageException(String.format("Do not read Resume from file %s", file.getName()), file.getName());
                }
            }
        }
        return listResume;
    }

    @Override
    public void clear() {
        listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    if (!file.delete())
                        throw new StorageException(String.format("Don't clear directory. File %s don't delete", file.getName()), file.getName());
                }
            }
        }
    }

    @Override
    public int size() {
        listFiles = directory.listFiles();
        return (listFiles == null) ? 0 : listFiles.length;
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Cохраняет резюме в файл
     */
    protected abstract void writeResume(Resume resume, File file) throws IOException;

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Читает резюме из файла
     */
    protected abstract Resume readResume(File file) throws IOException;
}
