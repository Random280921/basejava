package ru.topjava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid, String key) {
        super(String.format("Такое резюме %s уже есть: Key storage= %s\n", uuid, key), uuid);
    }
}
