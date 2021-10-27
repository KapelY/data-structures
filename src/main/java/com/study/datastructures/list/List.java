package com.study.datastructures.list;


// Abstract Data Type
public interface List<T> extends Iterable {
    // add value to the end of the list
    void add(T value);

    // [A, B, C, null, null] size = 3
    // add (D, [0,1,2,3])
    // we can add value by index between [0, size]
    // otherwise throw new IndexOutOfBoundsException
    void add(T value, int index);

    // we can remove value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException

    // [A, B, C] remove = 0
    // [B (index = 0) , C (index = 1)]
    T remove(int index);

    // [A, B, C, null, null] size = 3
    // we can get value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    T get(int index);

    // we can set value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    T set(T value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(T value);

    // [A, B, A, C] indexOf(A) -> 0
    // -1 if not exist
    int indexOf(T value);

    // [A, B, A, C] lastIndexOf(A) -> 2
    int lastIndexOf(T value);

    // [A, B, C]
    String toString();
}