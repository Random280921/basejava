package ru.topjava.webapp.model;

/**
 * Составной ключ поиска в хранилищах разных типов
 */
public class SearchKey {
    private final int index;
    private final String key;

    public SearchKey(int index, String key) {
        this.index = index;
        this.key = key;
    }

    public int getIndex() {
        return index;
    }

    public String getKey() {
        return key;
    }
}
