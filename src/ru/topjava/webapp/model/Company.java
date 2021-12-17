package ru.topjava.webapp.model;

import ru.topjava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;
import static ru.topjava.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Comparable<Company>, Serializable {
    private static final long serialVersionUID = 1L;

    private Contact companyName;
    private TreeSet<Experience> experienceSet = new TreeSet<>();

    public Company() {
    }

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
     * Sorted: last date in Company to first, dateTo NOW is first position
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyName.equals(company.companyName) && Objects.equals(experienceSet, company.experienceSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, experienceSet);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Comparable<Experience>, Serializable {
        private static final long serialVersionUID = 1L;
        private static final DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("MM/yyyy");

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;
        private String positionTitle;
        private String positionText;

        public Experience() {
        }

        Experience(LocalDate dateFrom, LocalDate dateTo, String positionTitle, String positionText) {
            requireNonNull(dateFrom, "Experience.dateFrom must not be null");
            requireNonNull(dateTo, "Experience.dateTo must not be null");
            requireNonNull(positionTitle, "Experience.positionTitle must not be null");
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            this.positionTitle = positionTitle;
            this.positionText = positionText;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        public String getPositionTitle() {
            return positionTitle;
        }

        public String getPositionText() {
            return (positionText == null) ? "" : positionText;
        }

        public String getPeriod() {
            return String.format("%s - %s",
                    getDateFrom().format(PATTERN_DATE),
                    (NOW.equals(getDateTo())) ? "Сейчас" : getDateTo().format(PATTERN_DATE));
        }

        @Override
        public String toString() {
            return ("".equals(getPositionText()))
                    ?
                    String.format("%s    %s\n",
                            getPeriod(),
                            getPositionTitle())
                    :
                    String.format("%s    %s\n                      %s\n",
                            getPeriod(),
                            getPositionTitle(),
                            getPositionText());
        }

        /**
         * Sorted: last date experience to first
         */
        @Override
        public int compareTo(Experience o) {
            return o.getDateFrom().compareTo(getDateFrom());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Experience that = (Experience) o;
            return dateFrom.equals(that.dateFrom) && dateTo.equals(that.dateTo) && positionTitle.equals(that.positionTitle) && Objects.equals(positionText, that.positionText);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateFrom, dateTo, positionTitle, positionText);
        }
    }
}
