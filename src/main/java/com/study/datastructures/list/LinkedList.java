package com.study.datastructures.list;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList implements List {
    public static final String EXCEPTION_SET_VALUE = "We can set value by index between [0, size - 1]";
    public static final String EXCEPTION_ADD_VALUE = "We can add value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_VALUE = "We can remove value by index between [0, size - 1]";
    public static final String EXCEPTION_GET_VALUE = "We can get value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";

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
        addCheckRange(index);

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
        checkRange(index, EXCEPTION_REMOVE_VALUE);
        size -= 1;

        Node removeNode = findNode(index);
        if (removeNode == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        }

        if (removeNode.prev != null) {
            removeNode.prev.next = removeNode.next;
        }
        if (removeNode.next != null) {
            removeNode.next.prev = removeNode.prev;
        }

        return removeNode.value;
    }

    @Override
    public Object get(int index) {
        checkRange(index, EXCEPTION_GET_VALUE);

        Node currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public Object set(Object value, int index) {
        checkRange(index, EXCEPTION_SET_VALUE);

        Node foundNode = findNode(index);
        Object oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
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
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;
        for (Node currentNode = head; currentNode != null; index++) {
            if (Objects.equals(value, currentNode.value)) {
                return index;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        int index = size - 1;
        for (Node currentNode = tail; currentNode != null; index--) {
            if (Objects.equals(value, currentNode.value)) {
                return index;
            }
            currentNode = currentNode.prev;
        }
        return -1;
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

    private void checkRange(int index, String message) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(message);
        }
    }

    private void addCheckRange(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(EXCEPTION_ADD_VALUE);
        }
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    @Data
    @AllArgsConstructor
    public static class Node {
        private Object value;
        private Node prev;
        private Node next;
    }

    private class MyIterator implements Iterator {
        private Node currentNode;
        private int currentIndex;
        private boolean nextWasCalled;

        public MyIterator() {
            this.currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextWasCalled = true;
            Object returnedValue = currentNode.value;
            System.out.println(returnedValue + " returned value ");
            currentNode = currentNode.next;
            currentIndex++;
            return returnedValue;
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException(EXCEPTION_REMOVE_ITERATOR);
            }
            nextWasCalled = false;
            LinkedList.this.remove(--currentIndex);
        }
    }

}
