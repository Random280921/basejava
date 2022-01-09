package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.util.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}