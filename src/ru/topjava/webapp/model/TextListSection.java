package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    protected List<String> listPosition = new ArrayList<>();

    public TextListSection() {
    }

    public void addListPosition(String position) {
        listPosition.add(position);
    }

    public List<String> getListPosition() {
        return listPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextListSection that = (TextListSection) o;
        return Objects.equals(listPosition, that.listPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listPosition);
    }
}
