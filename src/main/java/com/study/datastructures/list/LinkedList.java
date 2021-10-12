package com.study.datastructures.list;

import lombok.Data;

import java.util.StringJoiner;

public class LinkedList implements List {
    public static final String EXCEPTION_SET_VALUE = "We can set value by index between [0, size - 1]";
    public static final String EXCEPTION_ADD_VALUE = "We can add value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_VALUE = "We can remove value by index between [0, size - 1]";
    public static final String EXCEPTION_GET_VALUE = "We can get value by index between [0, size - 1]";

    private Node head;
    private Node tail;
    private int size;

    public LinkedList() {
    }

    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public void add(Object value, int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_ADD_VALUE);
        }

        if (head == null) {
            tail = head = new Node(value, null, null);
            size++;
            return;
        }

        if (head == tail) {
            tail = new Node(value, head, null);
            head.next = tail;
            size++;
            return;
        }

        if (index == size) {
            Node newNode = new Node(value, tail, null);
            tail.next = newNode;
            tail = newNode;
            size++;
            return;
        }

        Node foundNode = findNode(index);
        if (foundNode == head) {
            head = new Node(value, null, foundNode);
            foundNode.prev = head;
            size++;
            return;
        }

        if (foundNode.prev != null) {
            Node newNode = new Node(value, foundNode.prev, foundNode);
            foundNode.prev.next = newNode;
            foundNode.prev = newNode;
        }
        size++;
    }

    @Override
    public Object remove(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
        size -= 1;

        Object removeNode;
        if (index == size) {
            removeNode = tail.value;
            tail.prev.next = null;
            tail = tail.prev;

        } else {
            Node currentNode = head;

            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNext();
            }
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            removeNode = currentNode.value;
        }
        return removeNode;
    }

    @Override
    public Object get(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public Object set(Object value, int index) {
        return null;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

    @Override
    public String toString() {
        if (head != null) {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            Node currentNode = head;
            while (currentNode.next != null) {
                stringJoiner.add(String.valueOf(currentNode.value));
                currentNode = currentNode.next;
            }
            stringJoiner.add(String.valueOf(currentNode.value));
            return stringJoiner.toString();
        }
        return "";
    }

    private Node findNode(int index) {
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Data
    private class Node {
        private Object value;
        private Node prev;
        private Node next;

        public Node() {
        }

        public Node(Object value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
