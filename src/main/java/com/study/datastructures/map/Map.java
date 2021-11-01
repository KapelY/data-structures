package com.study.datastructures.map;

public interface Map<K, V> extends Iterable<HashMap.Entry<K, V>> {

    V put(K key, V value);

    V get(Object key);

    int size();

    boolean containsKey(Object key);

    V remove(K key);

    void clear();
}
