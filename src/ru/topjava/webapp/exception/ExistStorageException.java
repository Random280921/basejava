package ru.topjava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid, int index) {
        super(String.format("Такое резюме %s уже есть: Id storage= %d\n", uuid, index), uuid);
    }
}
