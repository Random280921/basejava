package ru.topjava.webapp.model;

import java.util.List;

public abstract class SectionCompany extends AbstractSection<Company> {

    protected void addListPosition(Company position) {
        listPosition.add(position);
    }

    protected List<Company> getListPosition() {
        return listPosition;
    }
}
