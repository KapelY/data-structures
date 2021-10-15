package com.study.datastructures.list;


// Abstract Data Type
public interface List extends Iterable {
    // add value to the end of the list
    void add(Object value);

    // [A, B, C, null, null] size = 3
    // add (D, [0,1,2,3])
    // we can add value by index between [0, size]
    // otherwise throw new IndexOutOfBoundsException
    void add(Object value, int index);

    // we can remove value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException

    // [A, B, C] remove = 0
    // [B (index = 0) , C (index = 1)]
    Object remove(int index);

    // [A, B, C, null, null] size = 3
    // we can get value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    Object get(int index);

    // we can set value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    Object set(Object value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(Object value);

    // [A, B, A, C] indexOf(A) -> 0
    // -1 if not exist
    int indexOf(Object value);

    // [A, B, A, C] lastIndexOf(A) -> 2
    int lastIndexOf(Object value);

    // [A, B, C]
    String toString();
}