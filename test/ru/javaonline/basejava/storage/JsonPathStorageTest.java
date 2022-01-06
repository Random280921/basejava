package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.storage.serialize.JsonStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStrategy()));
    }
}