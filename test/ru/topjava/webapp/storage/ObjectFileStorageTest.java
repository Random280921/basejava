package ru.topjava.webapp.storage;

import ru.topjava.webapp.storage.serialize.ObjectStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStrategy()));
    }
}