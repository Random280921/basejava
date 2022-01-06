package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.storage.serialize.XmlStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStrategy()));
    }
}