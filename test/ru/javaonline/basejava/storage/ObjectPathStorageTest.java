package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.storage.serialize.ObjectStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStrategy()));
    }
}