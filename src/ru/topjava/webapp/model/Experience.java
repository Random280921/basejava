package ru.topjava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;

public class Experience implements Comparable<Experience> {

    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String positionTitle;
    private final String positionText;

    private final DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("MM/yyyy");

    public Experience(LocalDate dateFrom, LocalDate dateTo, String positionTitle, String positionText) {
        requireNonNull(dateFrom, "Experience.dateFrom must not be null");
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
                dateFrom.format(PATTERN_DATE),
                (dateTo == null) ? "Сейчас" : dateTo.format(PATTERN_DATE));
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s\n",
                getPeriod(),
                getPositionTitle(),
                getPositionText());
    }

    /**
     * Sorted: last date experience to first
     */
    @Override
    public int compareTo(Experience o) {
        return o.dateFrom.compareTo(dateFrom);
    }
}
