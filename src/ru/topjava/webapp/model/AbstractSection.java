package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSection<POS> {
    protected String blockPosition;
    protected List<POS> listPosition = new ArrayList<>();

    public void addBlockPosition(String blockPosition) {
        this.blockPosition = blockPosition;
    }

    public String getBlockPosition() {
        return blockPosition;
    }

    protected abstract void addListPosition(POS Position);

    protected abstract List<POS> getListPosition();
}
