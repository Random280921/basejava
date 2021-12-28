package ru.topjava.webapp;

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
public class MainLesson11HW {
    public static final Lock Lock1 = new Lock("Lock1");
    public static final Lock Lock2 = new Lock("Lock2");

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> threadProcess(Lock1, Lock2));
        Thread thread2 = new Thread(() -> threadProcess(Lock2, Lock1));
        thread1.start();
        thread2.start();

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
                if (info.getThreadName().equals(thread1.getName()) || info.getThreadName().equals(thread2.getName()))
                    System.out.println(info);
            }
        }
    }

    private static class Lock {
        String name;

        public Lock(String name) {
            this.name = name;
        }
    }

    private static void threadProcess(Lock l1, Lock l2) {
        String threadName = Thread.currentThread().getName();
        synchronized (l1) {
            System.out.println(threadName + ": взяли " + l1.name);
            System.out.println(threadName + ": ждём " + l2.name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + ": пробуем взять " + l2.name);
            synchronized (l2) {
                System.out.println(threadName + ": " + l1.name + " и " + l2.name);
            }
        }
    }
}
