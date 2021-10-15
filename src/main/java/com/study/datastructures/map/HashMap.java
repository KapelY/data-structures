package com.study.datastructures.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class HashMap implements Map {
    private static final int DEFAULT_CAPACITY = 10;

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
        final List bucket = buckets[findBucketIndex(key)];
        if (bucket != null && findEntryIndex(bucket, key) != null) {
            findEntryIndex(bucket, key);
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
        System.out.println("length = " + buckets.length + "  " + key.hashCode() % buckets.length);
        return key.hashCode() % buckets.length;
    }

//    private Integer findIndexByValue(List<Entry> bucket, Object value) {
//        Entry entry;
//        for (int i = 0; i < bucket.size(); i++) {
//             entry =  bucket.get(i);
//            if (entry.value.equals(value)) {
//                return i;
//            }
//        }
//        return null;
//    }

    @Data
    @AllArgsConstructor
    public static final class Entry {
        Object key;
        Object value;
    }
}
