package ru.topjava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Experience implements Comparable<Experience> {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String positionTitle;
    private final String positionText;

    private final DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("MM/yyyy");

    public Experience(LocalDate dateFrom, LocalDate dateTo, String positionTitle, String positionText) {
        Objects.requireNonNull(dateFrom, "dateFrom must not be null");
        Objects.requireNonNull(positionTitle, "positionTitle must not be null");
        Objects.requireNonNull(positionText, "positionText must not be null");
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.positionTitle = positionTitle;
        this.positionText = positionText;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public String getPositionText() {
        return positionText;
    }

    public String getPeriod() {
        return String.format("%s - %s",
                dateFrom.format(PATTERN_DATE),
                (dateTo == null) ? "Сейчас" : dateTo.format(PATTERN_DATE));
    }

    @Override
    public String toString() {
        return String.format("%s - %s\n%s\n%s",
                dateFrom.format(PATTERN_DATE),
                (dateTo == null) ? "Сейчас" : dateTo.format(PATTERN_DATE),
                positionTitle,
                positionText);
    }

    @Override
    public int compareTo(Experience o) {
        return o.dateFrom.compareTo(dateFrom);
    }
}
