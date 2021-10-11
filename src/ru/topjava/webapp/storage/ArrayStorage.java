package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
            return;
        }
        if (resume.getUuid() == null) {
            System.out.println("uuid некорректный - невозможно сохранить резюме!");
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            storage[size] = resume;
            size++;
        } else
            System.out.printf("Такое резюме уже есть: Id storage= %d\n", index);
    }

    public void update(Resume resume) {
        if (resume.getUuid() == null) {
            System.out.println("uuid некорректный - невозможно обновить резюме!");
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для обновления в хранилище нет!\n", resume.getUuid());
        } else
            storage[index] = resume;
    }

    public void delete(String uuid) {
        if (uuid == null) {
            System.out.println("uuid некорректный - невозможно удалить резюме!");
            return;
        }
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Такого резюме (uuid = %s) для удаления в хранилище нет!\n", uuid);
            return;
        }
        size--;
        storage[index] = storage[size];
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища, где лежит резюме
     * Если резюме не найдено, возвращает -1
     */
    protected int findIndex(String uuid) {
        for (int index = 0; index < size; index++) {
            if (uuid.equals(storage[index].getUuid())) {
                return index;
            }
        }
        return -1;
    }

}
