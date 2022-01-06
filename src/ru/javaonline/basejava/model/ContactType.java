package ru.javaonline.basejava.model;

public enum ContactType {
    PHONE_DEFAULT("Тел.: "),
    PHONE_ADD1("Тел. 2: "),
    PHONE_ADD2("Тел. 3: "),
    MESSENGER_DEFAULT("Мессенджер: "),
    MESSENGER_ADD1("Мессенджер 2: "),
    MESSENGER_ADD2("Мессенджер 3: "),
    EMAIL("Почта: "),
    NETWORK_DEFAULT("Соцсеть: "),
    NETWORK_ADD1("Соцсеть 2: "),
    NETWORK_ADD2("Соцсеть 3: "),
    SITE("Сайт: ");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
