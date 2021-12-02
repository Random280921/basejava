package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class SectionText extends AbstractSection<String> {
    private String blockPosition;
    protected List<String> listPosition = new ArrayList<>();

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
}
