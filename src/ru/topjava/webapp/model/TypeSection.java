package ru.topjava.webapp.model;

public enum TypeSection {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    TypeSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
