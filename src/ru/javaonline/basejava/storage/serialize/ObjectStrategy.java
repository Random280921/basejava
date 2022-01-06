package ru.javaonline.basejava.storage.serialize;

import ru.javaonline.basejava.exception.StorageException;
import ru.javaonline.basejava.model.Resume;

import java.io.*;

public class ObjectStrategy implements Strategy {
    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
