package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
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
    protected Resume getResume(File file) {
        try (InputStream is = Files.newInputStream(file.toPath())) {
            return readResume(is);
        } catch (IOException e) {
            throw new StorageException(String.format("Error read Resume from file %s", file.getAbsolutePath()), file.getName());
        }
    }

    @Override
    protected File findKey(String uuid) {
        File keyFile = new File(directory.getAbsolutePath(), uuid);
        return (keyFile.exists()) ? keyFile : null;
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        file = new File(directory.getAbsolutePath(), resume.getUuid());
        try {
            if (file.createNewFile()) updateResume(resume, file);
        } catch (IOException e) {
            throw new StorageException(String.format("Could not create file %s /saveResume", file.getAbsolutePath()), file.getName(), e);
        }
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            writeResume(resume, os);
        } catch (IOException e) {
            throw new StorageException(String.format("Could not write Resume to file %s /updateResume", file.getAbsolutePath()), file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete())
            throw new StorageException(String.format("Could not delete file %s ", file.getAbsolutePath()), file.getName());
    }

    @Override
    protected List<Resume> convertToList() {
        List<Resume> listResume = new ArrayList<>(size());
        listFiles = directory.listFiles();
        if (listFiles == null) throw new StorageException("Directory read error", null);
        for (File file : listFiles) {
            listResume.add(getResume(file));
        }
        return listResume;
    }

    @Override
    public void clear() {
        listFiles = directory.listFiles();
        if (listFiles == null) throw new StorageException("Directory read error", null);
        for (File file : listFiles) {
            if (file.isFile()) {
                deleteResume(file);
            }
        }
    }

    @Override
    public int size() {
        String[] namesFiles = directory.list();
        if (namesFiles == null) throw new StorageException("Directory read error", null);
        return namesFiles.length;
    }

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Cохраняет резюме в файл
     */
    protected abstract void writeResume(Resume resume, OutputStream os) throws IOException;

    /**
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Читает резюме из файла
     */
    protected abstract Resume readResume(InputStream is) throws IOException;
}
