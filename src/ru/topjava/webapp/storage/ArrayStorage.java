package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
        } else {
            Integer id = this.getIdResume(resume.getUuid());
            if (id == null) {
                storage[size] = resume;
                size++;
            } else if (id >= 0) {
                System.out.printf("Такое резюме уже есть: Id storage= %d\n", id);
            }
        }
    }

    public void update(Resume resume) {
        if (resume.getUuid() == null) {
            System.out.println("uuid пустой - невозможно обновить резюме!");
        } else {
            Integer id = this.getIdResume(resume.getUuid());
            if (id == null) {
                System.out.println("Такого резюме для обновления в хранилище нет!");
            } else if (id >= 0) {
                storage[id] = resume;
            }
        }
    }

    public Resume get(String uuid) {
        if (uuid == null) {
            System.out.println("uuid пустой - невозможно получить резюме!");
        } else {
            Integer id = this.getIdResume(uuid);
            if (id == null) {
                System.out.println("Такого резюме для получения в хранилище нет!");
                return null;
            } else if (id >= 0) {
                return storage[id];
            }
        }
        return null;
    }

    /**
     * @return Id storage, contains Resume
     * Вспомогательный метод, чтобы убрать дублирование кода в методах
     * Получаем идентификатор хранилища, где лежит резюме
     * При пустом входном uuid возвращаем признак ошибки
     */
    public Integer getIdResume(String uuid) {
        if (uuid != null) {
            int id = 0;
            while (id < size) {
                if (uuid.equals(storage[id].getUuid())) {
                    return id;
                }
                id++;
            }
            return null;
        } else {
            return -1;
        }
    }

    public void delete(String uuid) {
        Integer id = this.getIdResume(uuid);
        if (null == id) {
            System.out.println("Такого резюме для удаления в хранилище нет!");
        } else if (id < 0) {
            System.out.println("uuid пустой - невозможно удалить резюме!");
        } else {
            size--;
            storage[id] = storage[size];
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        for (int i = 0; i < size; i++) {
            allResume[i] = storage[i];
        }
        return allResume;
    }

    public int size() {
        return size;
    }
}
