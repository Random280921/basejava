package ru.topjava.webapp.model;

import java.util.Objects;

public class TextBlockSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String blockPosition;

    public TextBlockSection() {
    }

    public TextBlockSection(String blockPosition) {
        this.blockPosition = blockPosition;
    }

    public void addBlockPosition(String blockPosition) {
        this.blockPosition = blockPosition;
    }

    public String getBlockPosition() {
        return blockPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextBlockSection that = (TextBlockSection) o;
        return Objects.equals(blockPosition, that.blockPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockPosition);
    }
}
