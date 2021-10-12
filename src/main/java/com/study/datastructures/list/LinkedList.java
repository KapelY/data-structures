package com.study.datastructures.list;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

public class LinkedList implements List{

    private Node head;
    private Node tail;
    private int size;

//    public LinkedList() {
//        head = tail = new Node();
//    }

    @Override
    public void add(Object value) {
        if (size == 0) {
            tail = head = new Node(value);
            head.next = tail;
            tail.prev = head;
        } else {
            Node currentNode = new Node(value);
            currentNode.prev = tail;
            tail.next = currentNode;
            tail = currentNode;
        }
        size++;
    }

    @Override
    public void add(Object value, int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        size++;
    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public Object get(int index) {
        if (index > size ) {
            throw new IndexOutOfBoundsException();
        }

        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public Object set(Object value, int index) {
        return null;
    }

    @Override
    public void clear() {
        head = tail = new Node();
        size = 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object value) {
        return false;
    }

    @Override
    public int indexOf(Object value) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object value) {
        return 0;
    }


    @Data
    private class Node {
        private Object value;
        private Node prev;
        private Node next;

        public Node() {
        }

        public Node(Object value) {
            this.value = value;
        }
    }
}
