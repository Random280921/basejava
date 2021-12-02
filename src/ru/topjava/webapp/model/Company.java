package ru.topjava.webapp.model;

import java.time.LocalDate;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;

public class Company implements Comparable<Company> {
    private final Contact companyName;
    private TreeSet<Experience> experienceSet = new TreeSet<>();

    public Company(String value, String url) {
        this.companyName = new Contact(value, url);
    }

    public Company(String value) {
        this(value, null);
    }

    public Company(Contact companyName,
                   TreeSet<Experience> experienceSet) {
        this.companyName = companyName;
        this.experienceSet = experienceSet;
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
        if (!getExperienceSet().isEmpty() && getExperienceSet().first().getDateTo() == null)
            requireNonNull(dateTo, "The null Experience.dateTo field allready exist");
        getExperienceSet().add(new Experience(dateFrom, dateTo, positionTitle, positionText));
    }

    public void addExperience(LocalDate dateFrom, LocalDate dateTo, String positionTitle) {
        addExperience(dateFrom, dateTo, positionTitle, null);
    }

    public void removeExperience(LocalDate fistDate) {
        getExperienceSet().removeIf(experience -> experience.getDateFrom().equals(fistDate));
    }

    /**
     * Sorted: last date in Company to first, dateTo null is first position
     */
    @Override
    public int compareTo(Company o) {
        int compareResult = o.getExperienceSet().first().getDateFrom().compareTo(getExperienceSet().first().getDateFrom());
        final LocalDate thisDateTo = getExperienceSet().first().getDateTo();
        final LocalDate otherDateTo = o.getExperienceSet().first().getDateTo();
        if (thisDateTo == null && otherDateTo == null) return compareResult;
        if (thisDateTo == null) return -1;
        if (otherDateTo == null) return 1;
        return compareResult;
    }

    @Override
    public String toString() {
        String url = getCompanyName().getUrl();
        StringBuilder companyDescription = new StringBuilder(String.format("%s (%s)\n",
                getCompanyName().getValue(),
                (url == null) ? "url not exist" : url));
        for (Experience experience : getExperienceSet()) {
            companyDescription.append("   ").append(experience.toString());
        }
        return companyDescription.toString();
    }


}
