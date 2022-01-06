package ru.javaonline.basejava;

import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.storage.ArrayStorage;
import ru.javaonline.basejava.storage.Storage;

import java.util.List;

/**
 * Interactive test for ru.topjava.webapp.storage.SortedArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainAutoTest {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        String[] comBuf = {"gdsfdajgfj", "size", "save", "save name?", "save uuid7 qname8", "save uuid3 aname3", "save uuid4 hname5", "save null rname4",
                "save uuid7 qname8", "update tname5", "update", "update uuid3 aname3", "size", "delete y6", "delete", "delete uuid3 aname3", "size",
                "get u7", "get", "get uuid7 qname8", "clear", "size"};
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
        if (params.length < 1 || params.length > 3) {
            System.out.println("Неверная команда.");
            return;
        }
        String uuid = null;
        String name = null;
        if (params.length > 2) {
            uuid = ("null".equals(params[1].intern())) ? null : params[1].intern();
            name = comand.substring((params[0]+" "+params[1]).length()+1);
        }

        if (params.length == 2) name = params[1].intern();

        try {
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = (name !=null & uuid == null)? new Resume(name) : new Resume(uuid, name);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    r = (name !=null & uuid == null)? new Resume(name) : new Resume(uuid, name);
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            System.out.println("Storage.size = " + all.size());
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
