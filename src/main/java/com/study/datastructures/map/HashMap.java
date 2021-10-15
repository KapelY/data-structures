package com.study.datastructures.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringJoiner;

public class HashMap implements Map {
    private static final int DEFAULT_CAPACITY = 5;

    private List[] buckets;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        buckets = new ArrayList[capacity];
    }

    @Override
    public Object put(Object key, Object value) {
        var bucketIndex = findBucketIndex(key);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList<Entry>();
            buckets[bucketIndex].add(new Entry(key, value));
            size++;
        } else {
            var entryIndex = findEntryIndex(buckets[bucketIndex], key);
            if (entryIndex != null) {
                buckets[bucketIndex].set(entryIndex, new Entry(key, value));
            } else {
                buckets[bucketIndex].add(new Entry(key, value));
                size++;
            }
        }
        return value;
    }

    @Override
    public Object get(Object key) {
        var bucketIndex = findBucketIndex(key);
        Object result = null;
        if (buckets[bucketIndex] != null) {
            result = findEntryValue(buckets[bucketIndex], key);
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(Object key) {
        return buckets[findBucketIndex(key)] != null;
    }

    @Override
    public Object remove(Object key) {
        Object oldValue = null;
        var bucketIndex = findBucketIndex(key);
        List<Entry> list = buckets[bucketIndex];
        if (list != null && findEntryIndex(list, key) != null) {
            ListIterator iterator = list.listIterator();
            while (iterator.hasNext()){
                Entry entry = ((Entry)iterator.next());
                if (entry.key == key) {
                    oldValue = entry.value;
                    iterator.remove();
                    size--;
                }
            }
            if (list.isEmpty()) {
                buckets[bucketIndex] = null;
            }
        }
        return oldValue;
    }

    @Override
    public void clear() {
        buckets = new ArrayList[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < buckets.length; i++) {
            stringJoiner.add(String.valueOf(buckets[i]));
        }
        return stringJoiner.toString();
    }

    protected int capacity() {
        return buckets.length;
    }

    private Object findEntryValue(List<Entry> list, Object key) {
        for (Entry entry : list) {
            if (entry != null && entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    private Integer findEntryIndex(List<Entry> list, Object key) {
        Entry entry;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (entry != null && entry.key == key) {
                return i;
            }
        }
        return null;
    }

    private int findBucketIndex(Object key) {
        return key.hashCode() % buckets.length;
    }

    @Data
    @AllArgsConstructor
    public static final class Entry {
        Object key;
        Object value;
    }
}
