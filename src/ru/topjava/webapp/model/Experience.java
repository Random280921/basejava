package ru.topjava.webapp.model;

import ru.topjava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static ru.topjava.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience implements Comparable<Experience>, Serializable {
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
