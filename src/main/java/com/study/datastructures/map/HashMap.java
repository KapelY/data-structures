package com.study.datastructures.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {
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

        var entry = findEntry(buckets[bucketIndex], key);
        if (entry != null) {
            entry.value = value;
        } else {
            buckets[bucketIndex].add(new Entry<>(key, value));
            size++;
        }

        return value;
    }

    @Override
    public V get(Object key) {
        var bucketIndex = getIndex(key);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        if (bucket != null) {
            return findEntryValue(bucket, key);
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(Object key) {
        var bucket = buckets[getIndex(key)];
        if (bucket != null) {
            return findEntry(bucket, key) != null;
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

    int capacity() {
        return buckets.length;
    }

    private void checkCapacity() {
        if (size >= capacity() * 3 / 4) {
            allocateNewMap();
        }
    }

    private void allocateNewMap() {
        Iterator<Entry<K, V>> mapIterator = this.iterator();
        HashMap<K, V> newMap = new HashMap<>( capacity() * 2);
        Entry<K, V> entry;
        while (mapIterator.hasNext()) {
            entry = mapIterator.next();
            newMap.put(entry.key, entry.value);
        }
        this.buckets = newMap.buckets;

    }

    private V findEntryValue(List<Entry<K, V>> list, Object key) {
        for (Entry<K, V> entry : list) {
            if (entry != null && entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    private Entry<K, V> findEntry(List<Entry<K, V>> list, Object key) {
        Entry<K, V> entry;
        for (Entry<K, V> kvEntry : list) {
            entry = kvEntry;
            if (entry != null && entry.key.equals(key)) {
                return entry;
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
        private static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";
        private int bucketCounter;
        private int entryCounter;
        private Iterator<Entry<K, V>> entryIterator;
        private boolean nextWasCalled;

        public HashMapIterator() {
        }

        @Override
        public boolean hasNext() {
            return entryCounter < size;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextWasCalled = true;
            entryCounter++;
            return getNextEntry();
        }

        private Entry<K, V> getNextEntry() {
            return nextEntry();
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException(EXCEPTION_REMOVE_ITERATOR);
            }
            nextWasCalled = false;
            entryIterator.remove();
            size--;
        }

        private Entry<K, V> nextEntry() {
            if (entryIterator == null || !entryIterator.hasNext()) {
                nextBucket();
            }
            return entryIterator.next();
        }

        private void nextBucket() {
            for (int i = bucketCounter; i < buckets.length; i++) {
                bucketCounter++;
                if (buckets[i] != null) {
                    entryIterator = buckets[i].iterator();
                    break;
                }
            }
        }
    }
}
