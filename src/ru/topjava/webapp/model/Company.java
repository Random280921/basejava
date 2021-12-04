package ru.topjava.webapp.model;

import java.time.LocalDate;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;
import static ru.topjava.webapp.util.DateUtil.NOW;

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
        if (!getExperienceSet().isEmpty() && NOW.equals(getExperienceSet().first().getDateTo()))
            requireNonNull(dateTo, "The NOW Experience.dateTo field allready exist");
        getExperienceSet().add(new Experience(dateFrom, dateTo, positionTitle, positionText));
    }

    public void addExperience(LocalDate dateFrom, LocalDate dateTo, String positionTitle) {
        addExperience(dateFrom, dateTo, positionTitle, null);
    }

    public void addExperience(LocalDate dateFrom, String positionTitle, String positionText) {
        addExperience(dateFrom, NOW, positionTitle, positionText);
    }

    public void removeExperience(LocalDate fistDate) {
        getExperienceSet().removeIf(experience -> experience.getDateFrom().equals(fistDate));
    }

    /**
     * Sorted: last date in Company to first, dateTo null is first position
     */
    @Override
    public int compareTo(Company o) {
        int compareResultFrom = o.getExperienceSet().first().getDateFrom().compareTo(getExperienceSet().first().getDateFrom());
        int compareResultTo = o.getExperienceSet().first().getDateTo().compareTo(getExperienceSet().first().getDateTo());
        return (compareResultTo != 0) ? compareResultTo : compareResultFrom;
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
