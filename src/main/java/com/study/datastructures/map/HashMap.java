package com.study.datastructures.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {
    public static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";
    private static final int DEFAULT_CAPACITY = 5;

    private List<Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        buckets = (List<Entry<K, V>>[]) new ArrayList[capacity];
    }

    @Override
    public V put(K key, V value) {
        checkCapacity();
        var bucketIndex = getIndex(key);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList<>();
        }
        var entryIndex = findEntryIndex(buckets[bucketIndex], key);
        if (entryIndex != null) {
            buckets[bucketIndex].set(entryIndex, new Entry<>(key, value));
        } else {
            buckets[bucketIndex].add(new Entry<>(key, value));
            size++;
        }

        return value;
    }

    @Override
    public V get(Object key) {
        var bucketIndex = getIndex(key);
        V result = null;
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        if (bucket != null) {
            result = findEntryValue(bucket, key);
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(Object key) {
        var bucket = buckets[getIndex(key)];
        if (bucket != null) {
            return findEntryValue(bucket, key) != null;
        }
        return false;
    }

    @Override
    public V remove(Object key) {
        V oldValue = null;
        var bucketIndex = getIndex(key);

        List<Entry<K, V>> list = buckets[bucketIndex];
        if (list != null) {
            Iterator<Entry<K, V>> iterator = list.listIterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.key.equals(key)) {
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

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        buckets = (List<Entry<K, V>>[]) new ArrayList[capacity()];
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (List<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                stringJoiner.add(String.valueOf(bucket));
            }
        }
        return stringJoiner.toString();
    }

    protected int capacity() {
        return buckets.length;
    }

    private void checkCapacity() {
        if (size > buckets.length * 3 / 4) {

        }
    }

    private V findEntryValue(List<Entry<K, V>> list, Object key) {
        for (Entry<K, V> entry : list) {
            if (entry != null && entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    private Integer findEntryIndex(List<Entry<K, V>> list, Object key) {
        Entry<K, V> entry;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (entry != null && entry.key.equals(key)) {
                return i;
            }
        }
        return null;
    }

    private int getIndex(Object key) {
        int hash = key.hashCode();
        if (hash == Integer.MIN_VALUE) {
            hash = 42;
        }
        return hash % buckets.length;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    @Data
    @AllArgsConstructor
    public static class Entry<K, V> {
        private final K key;
        private V value;
    }

    private class HashMapIterator implements Iterator<Entry<K, V>> {
        private Iterator bucketIterator;
        private int bucketIndex;
        private List<Entry<K, V>> bucketsCopy;
        private boolean nextWasCalled;

        public HashMapIterator() {
//            bucketsCopy = new ArrayList<>();
//            for (List<Entry<K, V>> bucket : buckets) {
//                if (bucket != null) {
//                    for (Entry<K, V> entry : bucket) {
//                        bucketsCopy.add(entry);
//                    }
//                }
//            }
        }

        @Override
        public boolean hasNext() {
            return bucketIndex < size;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextWasCalled = true;
            return bucketsCopy.get(bucketIndex++);
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException(EXCEPTION_REMOVE_ITERATOR);
            }
            nextWasCalled = false;
            K key = bucketsCopy.remove(--bucketIndex).getKey();
            HashMap.this.remove(key);
        }
    }
}
