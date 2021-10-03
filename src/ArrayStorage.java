/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }

    void save(Resume resume) {
        if (resume.uuid != null) {
            int newId = this.size();
            newId = newId++;
            if (storage.length == newId) {
                System.out.print("Хранилище уже заполнено - резюме невозможно сохранить!");
            } else {
                storage[newId] = resume;
            }
        }
    }

    Resume get(String uuid) {
        Resume findResume = null;
        int i = 0;
        while (storage[i] != null) {
            if (uuid.equals(storage[i].uuid)) {
                findResume = storage[i];
                break;
            }
            i++;
        }
        return findResume;
    }

    void delete(String uuid) {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                break;
            }
        }
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
