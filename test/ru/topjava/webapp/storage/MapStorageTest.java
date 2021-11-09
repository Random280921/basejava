package ru.topjava.webapp.storage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Ignore
    @Test
    public void saveOverFlow() {
    }

    @Override
    protected void assertArray(Resume[] expectedResume, Resume[] actualResumes) {
        Arrays.sort(actualResumes);
        Arrays.sort(expectedResume);
        Assert.assertArrayEquals(expectedResume, actualResumes);
    }
}