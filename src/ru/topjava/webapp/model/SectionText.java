package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class SectionText implements Section {
    private String blockPosition;
    private final List<String> listPosition = new ArrayList<>();

    public void addBlockPosition(String blockPosition) {
        this.blockPosition = blockPosition;
    }

    public String getBlockPosition() {
        return blockPosition;
    }

    protected void addListPosition(String position) {
        listPosition.add(position);
    }

    protected List<String> getListPosition() {
        return listPosition;
    }
}
