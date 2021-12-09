package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StrategySerialize {
    void writeResume(Resume resume, OutputStream os) throws IOException;

    Resume readResume(InputStream is) throws IOException;
}
