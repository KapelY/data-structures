package com.study.datastructures.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashMap implements Map {
    private static final int DEFAULT_CAPACITY = 10;

    private ArrayList[] buckets;
    private int size;

    public HashMap() {
        buckets = new ArrayList[DEFAULT_CAPACITY];
    }

    @Override
    public Object put(Object key, Object value) {
        var list = buckets[findBucket(key)];
        if (list == null) {
            list = new ArrayList<>();
            size++;
        }
        list.add(new Entry(key, value));
        return value;
    }

    @Override
    public Object get(Object key) {
        var bucket = buckets[findBucket(key)];
        findEntry(bucket, key);
        return findEntry(bucket, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(Object key) {
        return buckets[findBucket(key)] != null;
    }

    @Override
    public Object remove(Object key) {
        if (buckets[findBucket(key)] != null) {

        }
        return null;
    }

    @Override
    public void clear() {
        buckets = new ArrayList[DEFAULT_CAPACITY];
    }

    private Object findEntry(List<Entry> list, Object key) {
        for (Entry entry : list) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    private int findBucket(Object key) {
        return key.hashCode() % buckets.length;
    }

    @Data
    @AllArgsConstructor
    public static final class Entry {
        Object key;
        Object value;
    }
}
