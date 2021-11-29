package ru.topjava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;
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
        if (!experienceSet.isEmpty() && experienceSet.first().getDateTo() == null)
            Objects.requireNonNull(dateTo, "The null Experience.dateTo field allready exist");
        experienceSet.add(new Experience(dateFrom, dateTo, positionTitle, positionText));
    }

    public void removeExperience(LocalDate fistDate) {
        experienceSet.removeIf(experience -> experience.getDateFrom().equals(fistDate));
    }

    /**
     * Sorted: last date in Company to first, dateTo null is first position
     */
    @Override
    public int compareTo(Company o) {
        int compareResult = o.experienceSet.first().getDateFrom().compareTo(experienceSet.first().getDateFrom());
        final LocalDate thisDateTo = experienceSet.first().getDateTo();
        final LocalDate otherDateTo = o.experienceSet.first().getDateTo();
        if (thisDateTo == null && otherDateTo == null) return compareResult;
        if (thisDateTo == null) return -1;
        if (otherDateTo == null) return 1;
        return compareResult;
    }

    @Override
    public String toString() {
        String url = companyName.getUrl();
        StringBuilder companyDescription = new StringBuilder(String.format("%s (%s)\n",
                companyName.getValue(),
                (url == null) ? "url not exist" : url));
        for (Experience experience : experienceSet) {
            companyDescription.append(experience.toString());
        }
        return companyDescription.append("\n").toString();
    }


}
