package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class SectionCompany implements Section {
    private final List<Company> listPosition = new ArrayList<>();

    protected void addListPosition(Company position) {
        listPosition.add(position);
    }

    protected List<Company> getListPosition() {
        return listPosition;
    }
}
