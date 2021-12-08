package ru.topjava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.Collections;

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
        System.out.println("-----ДЗ-8---------");
        recursiveSearch(dir.getCanonicalPath());
        // но для этого уже есть готовое библиотечное решение java.nio.file.Files.walk
        System.out.println("-----Java.Nio--------");
        Files.walk(dir.toPath(), FileVisitOption.FOLLOW_LINKS).forEach(System.out::println);

        // Lesson9 ДЗ рекурсивный обход c отступами
        System.out.println("-----ДЗ-9---------");
        recursiveSearchIndent(dir.getCanonicalPath(), 0);
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

    public static void recursiveSearchIndent(String pathname, int identDepth) throws IOException {
        File file = new File(pathname);
        String ident = String.join("", Collections.nCopies(identDepth, "  "));
        if (file.exists())
            System.out.printf("%s%s%n", ident, file.getName());
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            identDepth++;
            for (File file1 : listFiles) {
                recursiveSearchIndent(file1.getCanonicalPath(), identDepth);
            }
        }
    }
}
