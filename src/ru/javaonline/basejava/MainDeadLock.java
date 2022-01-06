package ru.javaonline.basejava;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Класс  Lesson 11 ДЗ Deadlock
 *
 * @author KAIvanov
 * created by 28.12.2021 15:30
 * @version 1.0
 */
public class MainDeadLock {
    public static void main(String[] args) {
        final String lock1 = "lock1";
        final String lock2 = "lock2";

        new Thread(() -> threadProcess(lock1, lock2)).start();
        new Thread(() -> threadProcess(lock2, lock1)).start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Проверяем состояние потоков:");

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();
        if (threadIds != null) {
            ThreadInfo[] infos = threadMXBean.getThreadInfo(threadIds);
            for (ThreadInfo info : infos) {
                if (info.getThreadName().contains("Thread"))
                    System.out.println(info);
            }
        }
    }

    private static void threadProcess(String l1, String l2) {
        String threadName = Thread.currentThread().getName();
        synchronized (l1) {
            System.out.println(threadName + ": взяли " + l1);
            System.out.println(threadName + ": ждём " + l2);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + ": пробуем взять " + l2);
            synchronized (l2) {
                System.out.println(threadName + ": " + l1 + " и " + l2);
            }
        }
    }
}
