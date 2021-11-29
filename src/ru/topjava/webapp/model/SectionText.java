package ru.topjava.webapp.model;

import java.util.List;

public abstract class SectionText extends AbstractSection<String> {

    protected void addListPosition(String position) {
        listPosition.add(position);
    }

    protected List<String> getListPosition() {
        return listPosition;
    }
}
