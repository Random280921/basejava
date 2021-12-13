package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String blockPosition;
    protected List<String> listPosition = new ArrayList<>();

    public TextSection() {
    }

    public void addBlockPosition(String blockPosition) {
        this.blockPosition = blockPosition;
    }

    public String getBlockPosition() {
        return blockPosition;
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
        TextSection that = (TextSection) o;
        return Objects.equals(blockPosition, that.blockPosition) && Objects.equals(listPosition, that.listPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockPosition, listPosition);
    }
}
