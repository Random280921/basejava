package ru.topjava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends AbstractSection<Company> {
    protected List<Company> listPosition = new ArrayList<>();

    public void addListPosition(Company position) {
        listPosition.add(position);
    }

    public List<Company> getListPosition() {
        listPosition.sort(Company::compareTo);
        return listPosition;
    }
}
