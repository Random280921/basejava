package ru.topjava.webapp.exception;

public class PhoneNumberException extends RuntimeException {
    public PhoneNumberException(String phoneNumber) {
        super(String.format("Phone number %s is incorrect!\n", phoneNumber));
    }
}
