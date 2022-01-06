package ru.javaonline.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    protected List<Company> listPosition = new ArrayList<>();

    public void addListPosition(Company position) {
        listPosition.add(position);
    }

    public CompanySection() {
    }

    public List<Company> getListPosition() {
        listPosition.sort(Company::compareTo);
        return listPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(listPosition, that.listPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listPosition);
    }
}
