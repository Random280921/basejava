package ru.javaonline.basejava;

import ru.javaonline.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws Exception {
        Resume r = new Resume("uuid1","John Doe");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        Method method = r.getClass().getMethod("toString");
        System.out.println(method.invoke(r));
//        System.out.println(r);
    }
}
