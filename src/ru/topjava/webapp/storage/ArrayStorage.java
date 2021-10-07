package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
        } else {
            int index = findIndex(resume.getUuid());
            switch (index) {
                case -2:
                    System.out.println("uuid некорректный - невозможно сохранить резюме!");
                    break;
                case -1:
                    storage[size] = resume;
                    size++;
                    break;
                default:
                    System.out.printf("Такое резюме уже есть: Id storage= %d\n", index);
                    break;
            }
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        switch (index) {
            case -2:
                System.out.println("uuid некорректный - невозможно обновить резюме!");
                break;
            case -1:
                System.out.println("Такого резюме для обновления в хранилище нет!");
                break;
            default:
                storage[index] = resume;
                break;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        switch (index) {
            case -2:
                System.out.println("uuid некорректный - невозможно получить резюме!");
                return null;
            case -1:
                System.out.println("Такого резюме для получения в хранилище нет!");
                return null;
            default:
                return storage[index];
        }
    }

    /**
     * @return index storage, contains Resume
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем индекс хранилища, где лежит резюме
     * Если резюме не найдено, возвращает -1
     * Если uuid некорректный (и поиск не выполнялся), возвращает -2
     */
    public int findIndex(String uuid) {
        if (uuid != null) {
            for (int index = 0; index < size; index++) {
                if (uuid.equals(storage[index].getUuid())) {
                    return index;
                }
            }
            return -1;
        } else {
            return -2;
        }
    }

    public void delete(String uuid) {
        int index = this.findIndex(uuid);
        switch (index) {
            case -2:
                System.out.println("uuid некорректный - невозможно удалить резюме!");
                break;
            case -1:
                System.out.println("Такого резюме для удаления в хранилище нет!");
                break;
            default:
                size--;
                storage[index] = storage[size];
                storage[size] = null;
                break;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
