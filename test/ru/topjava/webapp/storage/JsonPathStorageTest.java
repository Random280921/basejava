package ru.topjava.webapp.storage;

import ru.topjava.webapp.storage.serialize.JsonStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStrategy()));
    }
}