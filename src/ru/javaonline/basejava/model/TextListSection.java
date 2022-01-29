package ru.javaonline.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextListSection extends AbstractSection {
    public static final TextListSection EMPTY = new TextListSection("");
    private static final long serialVersionUID = 1L;

    protected List<String> listPosition = new ArrayList<>();

    public TextListSection() {
    }

    public TextListSection(List<String> listPosition) {
        this.listPosition = listPosition;
    }

    public TextListSection(String position) {
        this.listPosition.add(position);
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
