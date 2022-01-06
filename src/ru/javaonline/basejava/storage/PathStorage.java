package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.StorageException;
import ru.javaonline.basejava.storage.serialize.Strategy;
import ru.javaonline.basejava.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.directory = Paths.get(dirName);
        this.strategy = strategy;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dirName + " is not directory or is not writable");
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return strategy.readResume(new BufferedInputStream(Files.newInputStream(path)));
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
        try {
            strategy.writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
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
