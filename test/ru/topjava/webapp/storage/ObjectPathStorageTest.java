package ru.topjava.webapp.storage;

import ru.topjava.webapp.storage.serialize.ObjectStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStrategy()));
    }
}