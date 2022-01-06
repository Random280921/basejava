package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.storage.serialize.ObjectStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStrategy()));
    }
}