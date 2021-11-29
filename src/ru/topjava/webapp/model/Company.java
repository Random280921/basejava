package ru.topjava.webapp.model;

import java.time.LocalDate;
import java.util.TreeSet;

public class Company implements Comparable<Company> {
    private final Contact companyName;
    private final TreeSet<Experience> experienceSet = new TreeSet<>();

    public Company(String value, String url) {
        this.companyName = new Contact(value, url);
    }

    public Company(String value) {
        this(value, null);
    }

    public Contact getCompanyName() {
        return companyName;
    }

    public TreeSet<Experience> getExperienceSet() {
        return experienceSet;
    }

    public void addUrl(String url) {
        companyName.setUrl(url);
    }

    public void addExperience(LocalDate dateFrom, LocalDate dateTo, String positionTitle, String positionText) {
        experienceSet.add(new Experience(dateFrom, dateTo, positionTitle, positionText));
    }

    public void removeExperience(LocalDate fistDate) {
        experienceSet.removeIf(experience -> (experience.getDateFrom().equals(fistDate)));
    }

    /**
     * Sorted: last date in Company to first
     */
    @Override
    public int compareTo(Company o) {
        return o.experienceSet.first().getDateFrom().compareTo(experienceSet.first().getDateFrom());
    }

    @Override
    public String toString() {
        StringBuilder companyDescription = new StringBuilder(String.format("%s (%s)\n",
                companyName.getValue(),
                companyName.getUrl()));
        for (Experience experience : experienceSet) {
            companyDescription.append(experience.toString());
        }
        return companyDescription.toString();
    }


}
