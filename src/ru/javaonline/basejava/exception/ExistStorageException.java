package ru.javaonline.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid, String key) {
        super(String.format("Такое резюме %s уже есть: Key storage= %s\n", uuid, key), uuid);
    }

    public ExistStorageException(Exception exception) {
        super(exception);
    }
}
