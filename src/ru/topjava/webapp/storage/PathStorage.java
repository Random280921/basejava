package ru.topjava.webapp.storage;

import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StrategySerialize strategySerialize;

    protected PathStorage(String dirName, StrategySerialize strategySerialize) {
        Objects.requireNonNull(dirName, "dirName must not be null");
        Objects.requireNonNull(strategySerialize, "strategySerialize must not be null");
        this.directory = Paths.get(dirName);
        this.strategySerialize = strategySerialize;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dirName + " is not directory or is not writable");
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            return strategySerialize.readResume(is);
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
            strategySerialize.writeResume(resume, os);
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
        try {
            return Files.list(directory).map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path convert to List error", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}
