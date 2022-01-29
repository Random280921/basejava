package ru.javaonline.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaonline.basejava.exception.ExistStorageException;
import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javaonline.basejava.TestData.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
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
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = NullPointerException.class)
    public void saveNull() {
        storage.save(null);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume resumeUpd = RESUME_2;
        storage.update(resumeUpd);
        assertGet(resumeUpd);
    }

    @Test(expected = NullPointerException.class)
    public void updateNull() {
        storage.update(null);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
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
    public void getAllSorted() {
        final List<Resume> expectedResume = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        final List<Resume> actualResumes = storage.getAllSorted();

        expectedResume.sort(Resume::compareTo);
        Assert.assertEquals(expectedResume, actualResumes);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}