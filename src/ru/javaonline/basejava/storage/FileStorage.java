package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.StorageException;
import ru.javaonline.basejava.storage.serialize.Strategy;
import ru.javaonline.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Strategy strategy;

    protected FileStorage(File directory, Strategy strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return strategy.readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException(String.format("Error read Resume from file %s", file.getAbsolutePath()), file.getName(), e);
        }
    }

    @Override
    protected File findKey(String uuid) {
        File keyFile = new File(directory.getAbsolutePath(), uuid);
        return (keyFile.exists()) ? keyFile : null;
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        if (file == null) file = new File(directory.getAbsolutePath(), resume.getUuid());
        boolean b;
        try {
            b = file.createNewFile();
        } catch (IOException e) {
            throw new StorageException(String.format("Could not create file %s /saveResume", file.getAbsolutePath()), file.getName(), e);
        }
        if (!b) LOG.info(String.format("File %s exists!", file.getAbsolutePath()));
        updateResume(resume, file);
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            strategy.writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
        for (File file : getFiles(directory)) {
            listResume.add(getResume(file));
        }
        return listResume;
    }

    @Override
    public void clear() {
        for (File file : getFiles(directory)) {
            if (file.isFile()) {
                deleteResume(file);
            }
        }
    }

    @Override
    public int size() {
        return getFiles(directory).length;
    }

    /**
     * Вспомогательный метод, для сокращения общего кода в методах
     * Возвращает массив файлов директории
     */
    private File[] getFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) throw new StorageException("Directory read error", null);
        return files;
    }
}
