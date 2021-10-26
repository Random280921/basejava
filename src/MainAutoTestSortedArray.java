import ru.topjava.webapp.model.Resume;
import ru.topjava.webapp.storage.ArrayStorage;
import ru.topjava.webapp.storage.Storage;

/**
 * Interactive test for ru.topjava.webapp.storage.SortedArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainAutoTestSortedArray {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        String[] comBuf = {"gdsfdajgfj", "size", "save", "save q1", "save w2", "save e3", "save r4", "save q1", "update t5", "update", "update w2",
                "size", "delete y6", "delete", "delete e3", "size", "get u7", "get", "get w2", "clear", "size"};
        for (String com : comBuf) {
            try {
                System.out.println("---Command: " + com);
                testAllVariant(com);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void testAllVariant(String comand) {
        Resume r;
//        System.out.print("Введите одну из команд - (list | size | save uuid | update uuid| delete uuid | get uuid | clear | exit): ");
        String[] params = comand.trim().toLowerCase().split(" ");
        if (params.length < 1 || params.length > 2) {
            System.out.println("Неверная команда.");
            return;
        }
        String uuid = null;
        if (params.length == 2) {
            uuid = params[1].intern();
        }
        switch (params[0]) {
            case "list":
                printAll();
                break;
            case "size":
                System.out.println(ARRAY_STORAGE.size());
                break;
            case "save":
                r = new Resume(uuid);
                ARRAY_STORAGE.save(r);
                printAll();
                break;
            case "update":
                r = new Resume(uuid);
                ARRAY_STORAGE.update(r);
                printAll();
                break;
            case "delete":
                ARRAY_STORAGE.delete(uuid);
                printAll();
                break;
            case "get":
                System.out.println(ARRAY_STORAGE.get(uuid));
                break;
            case "clear":
                ARRAY_STORAGE.clear();
                printAll();
                break;
            case "exit":
                return;
            default:
                System.out.println("Неверная команда.");
                break;
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
