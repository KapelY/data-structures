package com.study.datastructures.list;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> implements List<T> {
    public static final String EXCEPTION_SET_VALUE = "We can set value by index between [0, size - 1]";
    public static final String EXCEPTION_ADD_VALUE = "We can add value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_VALUE = "We can remove value by index between [0, size - 1]";
    public static final String EXCEPTION_GET_VALUE = "We can get value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        addCheckRange(index);

        if (head == null) {
            tail = head = new Node<>(value, null, null);
            size++;
            return;
        }

        if (size == 1) {
            if (index == 1) {
                tail = new Node<>(value, head, null);
                head.next = tail;
            } else {
                head = new Node<>(value, null, tail);
                tail.prev = head;
            }
            size++;
            return;
        }

        if (index == size) {
            Node<T> newNode = new Node<>(value, tail, null);
            tail.next = newNode;
            tail = newNode;
            size++;
            return;
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(value, null, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }

        Node<T> foundNode = findNode(index);
        Node<T> newNode = new Node<>(value, foundNode.prev, foundNode);
        foundNode.prev.next = newNode;
        foundNode.prev = newNode;
        size++;
    }

    @Override
    public T remove(int index) {
        checkRange(index, EXCEPTION_REMOVE_VALUE);
        Node<T> removeNode = findNode(index);
        if (size == 1) {
            clear();
            return removeNode.value;
        }

        if (size == 2) {
            if (index == 0) {
                head = tail;
                tail.prev = null;
            } else {
                tail = head;
                head.prev = null;
            }
            size--;
            return removeNode.value;
        }

        if (removeNode.prev != null) {
            removeNode.prev.next = removeNode.next;
        }
        if (removeNode.next != null) {
            removeNode.next.prev = removeNode.prev;
        }
        size--;
        return removeNode.value;
    }

    @Override
    public T get(int index) {
        checkRange(index, EXCEPTION_GET_VALUE);

        Node<T> currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkRange(index, EXCEPTION_SET_VALUE);

        Node<T> foundNode = findNode(index);
        T oldValue = foundNode.value;
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
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(T value) {
        int index = 0;
        for (Node<T> currentNode = head; currentNode != null; index++) {
            if (Objects.equals(value, currentNode.value)) {
                return index;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        int index = size - 1;
        for (Node<T> currentNode = tail; currentNode != null; index--) {
            if (Objects.equals(value, currentNode.value)) {
                return index;
            }
            currentNode = currentNode.prev;
        }
        return -1;
    }

    @Override
    public String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            Node<T> currentNode = head;
            while (currentNode != null) {
                stringJoiner.add(String.valueOf(currentNode.value));
                currentNode = currentNode.next;
            }
            return stringJoiner.toString();
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
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
    public Iterator<T> iterator() {
        return new <T>MyIterator();
    }

    @Data
    @AllArgsConstructor
    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", prev=" + (prev != null ? (String) prev.value : null) +
                    ", next=" + (next != null ? (String) next.value : null) +
                    '}';
        }
    }

    private class MyIterator implements Iterator<T> {
        private Node<T> currentNode;
        private Node<T> returnedNode;
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
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextWasCalled = true;
            returnedNode = currentNode;
            currentNode = currentNode.next;
            currentIndex++;
            return returnedNode.value;
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException(EXCEPTION_REMOVE_ITERATOR);
            }
            nextWasCalled = false;
            System.out.println("currentNode=" + currentNode);
            if (returnedNode.prev != null && returnedNode.next != null) {
                returnedNode.prev.next = returnedNode.next;
                returnedNode.next.prev = returnedNode.prev;
            } else if(returnedNode.prev != null) {
                returnedNode.prev.next = null;
            } else if (returnedNode.next != null) {
                returnedNode.next.prev = null;
            }
        }
    }
}
