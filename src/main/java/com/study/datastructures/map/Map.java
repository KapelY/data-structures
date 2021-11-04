package com.study.datastructures.map;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

    V put(K key, V value);

    V get(Object key);

    int size();

    boolean containsKey(Object key);

    V remove(K key);

    void clear();

    interface Entry<K, V> {
        K getKey();

        V getValue();

        void setValue(V value);
    }
}
