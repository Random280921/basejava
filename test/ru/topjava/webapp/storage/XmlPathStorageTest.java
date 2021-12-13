package ru.topjava.webapp.storage;

import ru.topjava.webapp.storage.serialize.XmlStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStrategy()));
    }
}