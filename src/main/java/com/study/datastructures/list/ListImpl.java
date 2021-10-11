package com.study.datastructures.list;

import java.util.Arrays;

public class ListImpl implements List {
    private Object[] array;
    private int size = 0;

    public ListImpl(int capacity) {
        this.array = new Object[capacity];
    }

    @Override
    public void add(Object value) {
        array[size] = value;
        size++;
    }

    @Override
    public void add(Object value, int index) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException();
        for (int i = array.length - 1; i > index + 1; i--) {
            array[i] = array[i - 1];
        }
        array[index] = value;

    }

    @Override
    public Object remove(int index) {
        if (index > size - 1 || index < 0) throw new IndexOutOfBoundsException();
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        array[array.length - 1] = null;

        return array;
    }

    @Override
    public Object get(int index) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException();
        return array[index];
    }

    @Override
    public Object set(Object value, int index) {
        return array[index] = value;
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public boolean contains(Object value) {
        boolean result = false;
        for (Object i : array) {
            if (i == value) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public int indexOf(Object value) {
        int result = -1;
        for (int j = 0; j < array.length; j++) {
            if (array[j] == value) {
                result = j;
                break;
            }
        }
        return result;
    }

    @Override
    public int lastIndexOf(Object value) {
        int result = -1;
        for (int j = array.length - 1; j > -1; j--) {
            if (array[j] == value) {
                result = j;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size - 1; i++) {
            sb.append(array[i]).append(", ");
        }
        sb.append(array[size - 1]).append("]");
        return sb.toString();
    }
}
