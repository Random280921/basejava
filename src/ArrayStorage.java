/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
    }

    void save(Resume resume) {
        int newId  = this.size();
        newId = newId++;
        if (storage.length == newId) {
            System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
        } else {
            storage[newId] = resume;
        }
    }

    Resume get(String uuid) {
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int sizeStorage = this.size();
        Resume[] allResume = new Resume[sizeStorage];
        for (int i = 0; i < sizeStorage; i++) {
            allResume[i] = storage[i];
        }
        return allResume;
    }

    int size() {
        int size = 0;
        while (storage[size] != null) {
            size++;
        }
        return size;
    }
}
