package ru.javaonline.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainString {
    public static void main(String[] args) {
//        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
////        String result = "";
//        StringBuilder sb = new StringBuilder();
//        for (String str : strArray) {
//            sb.append(str).append(", ");
//        }
//        System.out.println(sb);
//
//        String str1 = "abc";
//        String str3 = "c";
//        String str2 = ("ab" + str3).intern();
//        System.out.println(str2.equals(str1));

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("  ");
        list.add("3");
        list.removeIf(s -> s.trim().length() == 0);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
