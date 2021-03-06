package ru.javaonline.basejava.model;

import ru.javaonline.basejava.util.DateUtil;
import ru.javaonline.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Comparable<Company>, Serializable {
    private static final long serialVersionUID = 1L;
    public static final Company EMPTY = new Company("", "", Experience.EMPTY);

    private Contact companyName;
    private final List<Experience> experienceList = new ArrayList<>();

    public Company() {
    }

    public Company(String value, String url) {
        this.companyName = new Contact(value, url);
    }

    public Company(String value) {
        this(value, null);
    }

    public Company(String value, String url, Experience experience) {
        this.companyName = new Contact(value, url);
        this.experienceList.add(experience);
    }

    public Contact getCompanyName() {
        return companyName;
    }

    public List<Experience> getExperienceList() {
        experienceList.sort(Experience::compareTo);
        return experienceList;
    }

    public void addExperience(LocalDate dateFrom, LocalDate dateTo, String positionTitle, String positionText) {
        getExperienceList().add(new Experience(dateFrom, (dateTo == null) ? DateUtil.NOW : dateTo, positionTitle, positionText));
    }

    public void addExperience(LocalDate dateFrom, LocalDate dateTo, String positionTitle) {
        addExperience(dateFrom, dateTo, positionTitle, null);
    }

    public void addExperience(LocalDate dateFrom, String positionTitle, String positionText) {
        addExperience(dateFrom, DateUtil.NOW, positionTitle, positionText);
    }

    /**
     * Sorted: last date in Company to first, dateTo NOW is first position
     */
    @Override
    public int compareTo(Company o) {
        if (this.equals(Company.EMPTY)) return -1;
        List<Company.Experience> thisExp = getExperienceList();
        List<Company.Experience> thatExp = o.getExperienceList();
        if (thisExp.size() == 0 || thatExp.size() == 0)
            return o.companyName.compareTo(this.companyName);
        return thisExp.get(0).compareTo(thatExp.get(0));
    }

    @Override
    public String toString() {
        String url = getCompanyName().getUrl();
        StringBuilder companyDescription = new StringBuilder(String.format("%s (%s)\n",
                getCompanyName().getValue(),
                (url == null) ? "url not exist" : url));
        for (Experience experience : getExperienceList()) {
            companyDescription.append("   ").append(experience.toString());
        }
        return companyDescription.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyName.equals(company.companyName) && Objects.equals(experienceList, company.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, experienceList);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Comparable<Experience>, Serializable {
        private static final long serialVersionUID = 1L;
        public static final Experience EMPTY = new Experience();

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
            return positionText;
        }

        public String getPeriod() {
            DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("MM/yyyy");
            return String.format("%s - %s",
                    getDateFrom().format(PATTERN_DATE),
                    (DateUtil.NOW.equals(getDateTo())) ? "????????????" : getDateTo().format(PATTERN_DATE));
        }

        @Override
        public String toString() {
            return (getPositionText() == null)
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
            LocalDate thisFrom = (getDateFrom() != null) ? getDateFrom() : DateUtil.NOW;
            LocalDate thatFrom = (o.getDateFrom() != null) ? o.getDateFrom() : DateUtil.NOW;
            LocalDate thisTo = (getDateTo() != null) ? getDateTo() : DateUtil.NOW;
            LocalDate thatTo = (o.getDateTo() != null) ? o.getDateTo() : DateUtil.NOW;
            int resultFrom = thatFrom.compareTo(thisFrom);
            int resultTo = thatTo.compareTo(thisTo);
            return (resultTo == 0) ? resultFrom : resultTo;
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
