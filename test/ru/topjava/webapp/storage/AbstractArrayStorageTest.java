package ru.topjava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.topjava.webapp.exception.*;
import ru.topjava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

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
        Assert.assertEquals(new Resume(UUID_1), storage.get("uuid1"));
        Assert.assertEquals(new Resume(UUID_2), storage.get("uuid2"));
        Assert.assertEquals(new Resume(UUID_3), storage.get("uuid3"));
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
        Resume resumeSave = new Resume("uuid4");
        storage.save(resumeSave);
        Assert.assertEquals(resumeSave, storage.get("uuid4"));
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
        } catch (Exception e) {
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
        storage.update(new Resume("uuid4"));
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
        storage.delete("uuid4");
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(resumes.length, storage.size());
        try {
            for (Resume resume : resumes) {
                Assert.assertEquals(resume, storage.get(resume.getUuid()));
            }
        } catch (Exception e) {
            Assert.fail("Не совпадают резюме в storage и в результате getAll");
        }
    }

    @Test(expected = NullPointerException.class)
    public void checkUuidToNull() {
        Assert.assertEquals(UUID_1, ((AbstractArrayStorage) storage).checkUuidToNull(UUID_1));
        Assert.assertNull(((AbstractArrayStorage) storage).checkUuidToNull(null));
    }
}