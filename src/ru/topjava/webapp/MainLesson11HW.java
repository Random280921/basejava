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
    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            final String threadName = Thread.currentThread().getName();
            synchronized (Lock1) {
                System.out.println(threadName + ": взяли Lock1");
                System.out.println(threadName + ": ждём Lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + ": пробуем взять Lock2");
                synchronized (Lock2) {
                    System.out.println(threadName + ": Lock1 и Lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            synchronized (Lock2) {
                System.out.println(threadName + ": взяли Lock2");
                System.out.println(threadName + ": ждём Lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + ": пробуем взять Lock1");
                synchronized (Lock1) {
                    System.out.println(threadName + ": Lock2 и Lock1");
                }
            }
        });
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
}
