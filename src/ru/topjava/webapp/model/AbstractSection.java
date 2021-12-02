package ru.topjava.webapp.model;

import java.util.List;

public abstract class AbstractSection<POS> implements Section {
    protected List<POS> listPosition;

    protected abstract void addListPosition(POS position);

    protected abstract List<POS> getListPosition();
}
