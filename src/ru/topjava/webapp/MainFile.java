package ru.topjava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;

public class MainFile {

    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Lesson8 ДЗ рекурсивный обход директорий по любому переданному пути
        System.out.println("-----ДЗ----------");
        recursiveSearch(dir.getCanonicalPath());
        // но для этого уже есть готовое библиотечное решение java.nio.file.Files.walk
        System.out.println("-----Java.Nio--------");
        Files.walk(dir.toPath(), FileVisitOption.FOLLOW_LINKS).forEach(System.out::println);
    }

    public static void recursiveSearch(String pathname) throws IOException {
        File file = new File(pathname);
        if (file.exists())
            System.out.printf("%s%s%n", (file.isDirectory()) ? "Directory: " : "File: ", file.getName());
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file1 : listFiles) {
                recursiveSearch(file1.getCanonicalPath());
            }
        }
    }
}
