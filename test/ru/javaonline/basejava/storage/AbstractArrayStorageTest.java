package ru.javaonline.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaonline.basejava.exception.StorageException;
import ru.javaonline.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
        try {
            for (int i = storage.size() + 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.format("Name%d", i)));
            }
        } catch (StorageException e) {
            Assert.fail("Переполнение произошло до нужной проверки");
        }
        storage.save(new Resume(String.format("Name%d", AbstractArrayStorage.STORAGE_LIMIT + 1)));
    }

}