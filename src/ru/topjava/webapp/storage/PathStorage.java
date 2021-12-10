package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;
import ru.topjava.webapp.storage.serialize.Strategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Strategy strategy;

    protected PathStorage(String dirName, Strategy strategy) {
        Objects.requireNonNull(dirName, "dirName must not be null");
        Objects.requireNonNull(strategy, "strategySerialize must not be null");
        this.directory = Paths.get(dirName);
        this.strategy = strategy;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dirName + " is not directory or is not writable");
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            return strategy.readResume(is);
        } catch (IOException e) {
            throw new StorageException(String.format("Error read Resume from file %s", path), path.getFileName().toString());
        }
    }

    @Override
    protected Path findKey(String uuid) {
        Path path = Paths.get(directory.toString(), uuid);
        return (Files.exists(path)) ? path : null;
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        if (path == null) path = Paths.get(directory.toString(), resume.getUuid());
        try {
            updateResume(resume, Files.createFile(path));
        } catch (IOException e) {
            throw new StorageException(String.format("Could not create file %s /saveResume", path), path.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try (OutputStream os = Files.newOutputStream(path)) {
            strategy.writeResume(resume, os);
        } catch (IOException e) {
            throw new StorageException(String.format("Could not write Resume to file %s /updateResume", path), path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException(String.format("Could not delete file %s ", path), path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> convertToList() {
        return getFiles(directory).map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFiles(directory).forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getFiles(directory).count();
    }

    /**
     * Вспомогательный метод, для сокращения общего кода в методах
     * Возвращает стрим файлов директории
     */
    private Stream<Path> getFiles(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}
