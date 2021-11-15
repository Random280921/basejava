package ru.topjava.webapp.storage;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.ExcludeCategory(ArrayTest.class)
@Suite.SuiteClasses({ListStorageTest.class, MapStorageTest.class})
public class AllNonArrayStorageTest {
}
