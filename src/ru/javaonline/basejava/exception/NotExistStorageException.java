package ru.javaonline.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super(String.format("Такого резюме (uuid = %s) в хранилище нет!\n", uuid), uuid);
    }
}
