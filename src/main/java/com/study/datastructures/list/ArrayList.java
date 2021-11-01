package com.study.datastructures.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;


public class ArrayList<T> implements List<T> {
    public static final String EXCEPTION_SET_VALUE = "We can set value by index between [0, size - 1]";
    public static final String EXCEPTION_ADD_VALUE = "We can add value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_VALUE = "We can remove value by index between [0, size - 1]";
    public static final String EXCEPTION_GET_VALUE = "We can get value by index between [0, size - 1]";
    public static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";
    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        array = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkCapacity();
        checkRangeForAdd(index);

        if (index < size) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }

        array[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        checkRange(index, EXCEPTION_REMOVE_VALUE);
        T oldObject = array[index];
        if (size > index) {
            System.arraycopy(array, index + 1, array, index, size - index);
        }
        array[size] = null;
        size--;
        return oldObject;
    }

    @Override
    public T get(int index) {
        checkRange(index, EXCEPTION_GET_VALUE);
        return array[index];
    }

    @Override
    public T set(T value, int index) {
        checkRange(index, EXCEPTION_SET_VALUE);
        T oldValue = array[index];
        array[index] = value;
        return oldValue;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
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
        for (int j = 0; j < array.length; j++) {
            if (array[j] == value) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        for (int j = array.length - 1; j >= 0; j--) {
            if (array[j] == value) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            stringJoiner.add(String.valueOf(array[i]));
        }
        return stringJoiner.toString();
    }

    private void checkCapacity() {
        if (size == array.length) {
            array = Arrays.copyOf(array, size * 3 / 2 + 1);
        }
    }

    private void checkRange(int index, String message) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(message);
        }
    }

    private void checkRangeForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(EXCEPTION_ADD_VALUE);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new <T>MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int currentIndex;
        private boolean nextWasCalled;

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
            return array[currentIndex++];
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException(EXCEPTION_REMOVE_ITERATOR);
            }
            nextWasCalled = false;
            ArrayList.this.remove(--currentIndex);
        }
    }
}
