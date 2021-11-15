package ru.topjava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AllNonArrayStorageTest.class, ArrayStorageTest.class, SortedArrayStorageTest.class})
public class AllStorageTest {
}
