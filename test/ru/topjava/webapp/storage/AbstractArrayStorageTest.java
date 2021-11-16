package ru.topjava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.topjava.webapp.exception.*;
import ru.topjava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume[] etalonResume = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));

    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NullPointerException.class)
    public void getNull() {
        storage.get(null);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resumeSave = new Resume(UUID_4);
        storage.save(resumeSave);
        Assert.assertEquals(resumeSave, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NullPointerException.class)
    public void saveNull() {
        storage.save(null);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
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

    @Test
    public void update() {
        Resume resumeUpd = new Resume(UUID_2);
        storage.update(resumeUpd);
        Assert.assertEquals(resumeUpd, storage.get(UUID_2));
    }

    @Test(expected = NullPointerException.class)
    public void updateNull() {
        storage.update(null);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_4));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NullPointerException.class)
    public void deleteNull() {
        storage.delete(null);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(etalonResume.length, storage.size());
        Assert.assertArrayEquals(etalonResume, storage.getAll());
    }
}