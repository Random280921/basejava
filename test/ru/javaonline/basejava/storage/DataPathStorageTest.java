package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.storage.serialize.DataStrategy;

/**
 * Класс DataPathStorageTest -- тестирование PathStorage with DataStrategy
 *
 * @author KAIvanov
 * created by 15.12.2021 21:20
 * @version 1.0
 */
public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStrategy()));
    }
}
