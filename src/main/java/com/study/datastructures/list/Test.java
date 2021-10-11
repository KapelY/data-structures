package com.study.datastructures.list;

public class Test {

    public static void main(String[] args) {
        List list = new ListImpl(10);

        list.add("some");
        System.out.println(list);

        System.out.println(list.size());

        list.add("some1");
        System.out.println(list);

        list.add("some2");
        System.out.println(list);

        System.out.println(list.size());



    }
}
