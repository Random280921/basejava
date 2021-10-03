/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        if (resume.uuid != null) {
            if (storage.length - 1 == size) {
                System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
            } else {
                storage[size] = resume;
                size++;
            }
        }
    }

    Resume get(String uuid) {
        int i = 0;
        while (i < size) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
            i++;
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                size--;
                storage[i] = storage[size];
                storage[size] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        for (int i = 0; i < size; i++) {
            allResume[i] = storage[i];
        }
        return allResume;
    }

    int size() {
        return size;
    }
}
