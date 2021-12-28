package ru.topjava.webapp;

/**
 * Класс  ... description
 *
 * @author KAIvanov
 * created by 28.12.2021 14:10
 * @version 1.0
 */
public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
    }
}
