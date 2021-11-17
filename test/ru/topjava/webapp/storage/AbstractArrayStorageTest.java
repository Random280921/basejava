package ru.topjava.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.topjava.webapp.exception.StorageException;
import ru.topjava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
        try {
            for (int i = storage.size() + 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.format("uuid%d", i)));
            }
        } catch (StorageException e) {
            Assert.fail("Переполнение произошло до нужной проверки");
        }
        storage.save(new Resume(String.format("uuid%d", AbstractArrayStorage.STORAGE_LIMIT + 1)));
    }

}