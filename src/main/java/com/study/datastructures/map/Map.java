package com.study.datastructures.map;

public interface Map extends Iterable {

    Object put(Object key, Object value);

    Object get(Object key);

    int size();

    boolean containsKey(Object key);

    Object remove(Object key);

    void clear();
}
