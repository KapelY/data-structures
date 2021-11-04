package com.study.datastructures.map;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 5;

    private List<Map.Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        buckets = (List<Map.Entry<K, V>>[]) new ArrayList[capacity];
    }

    @Override
    public V put(K key, V value) {
        checkCapacity();
        var bucketIndex = getIndex(key, buckets.length);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList<>();
        }

        Map.Entry<K, V> entry = findEntryInBucket(buckets[bucketIndex], key);
        if (entry != null) {
            entry.setValue(value);
        } else {
            buckets[bucketIndex].add(new Entry<>(key, value));
            size++;
        }
        return value;
    }

    @Override
    public V get(Object key) {
        var bucketIndex = getIndex(key, buckets.length);
        List<Map.Entry<K, V>> bucket = buckets[bucketIndex];
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
        var bucket = buckets[getIndex(key, buckets.length)];
        if (bucket != null) {
            return findEntryInBucket(bucket, key) != null;
        }
        return false;
    }

    @Override
    public V remove(Object key) {
        V oldValue = null;
        var bucketIndex = getIndex(key, buckets.length);

        List<Map.Entry<K, V>> list = buckets[bucketIndex];
        if (list != null) {
            Iterator<Map.Entry<K, V>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    oldValue = entry.getValue();
                    iterator.remove();
                    size--;
                    break;
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
        size = 0;
        buckets = (List<Map.Entry<K, V>>[]) new ArrayList[capacity()];
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Map.Entry<K, V> entry : this) {
                stringJoiner.add(String.valueOf(entry));
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

    @SuppressWarnings("unchecked")
    private void allocateNewMap() {
        List<Map.Entry<K, V>>[] newBuckets = (List<Map.Entry<K, V>>[]) new ArrayList[capacity() * 2];
        for (Map.Entry<K, V> entry : this) {
            innerPut(newBuckets, entry);
        }
        this.buckets = newBuckets;
    }

    private void innerPut(List<Map.Entry<K, V>>[] newBuckets, Map.Entry<K, V> entry) {
        var bucketIndex = getIndex(entry.getKey(), newBuckets.length);
        if (newBuckets[bucketIndex] == null) {
            newBuckets[bucketIndex] = new ArrayList<>();
        }
        Map.Entry<K, V> existingEntry = findEntryInBucket(newBuckets[bucketIndex], entry.getKey());
        if (existingEntry != null) {
            existingEntry.setValue(entry.getValue());
        } else {
            newBuckets[bucketIndex].add(entry);
        }
    }

    private V findEntryValue(List<Map.Entry<K, V>> list, Object key) {
        for (Map.Entry<K, V> entry : list) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private Map.Entry<K, V> findEntryInBucket(List<Map.Entry<K, V>> list, Object key) {
        for (Map.Entry<K, V> kvEntry : list) {
            if (Objects.equals(kvEntry.getKey(), key)) {
                return kvEntry;
            }
        }
        return null;
    }

    private int getIndex(Object key, int bucketsLength) {
        int hash = key.hashCode();
        if (hash == Integer.MIN_VALUE) {
            hash = 42;
        }
        return hash % bucketsLength;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    @Data
    @AllArgsConstructor
    public static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        @Override
        public void setValue(V value) {
            this.value = value;
        }
    }

    private class HashMapIterator implements Iterator<Map.Entry<K, V>> {
        private static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";
        private int bucketCounter;
        private int entryCounter;
        private Iterator<Map.Entry<K, V>> entryIterator;
        private boolean nextWasCalled;

        @Override
        public boolean hasNext() {
            return entryCounter < size;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextWasCalled = true;
            entryCounter++;
            return getNextEntry();
        }

        private Map.Entry<K, V> getNextEntry() {
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

        private Map.Entry<K, V> nextEntry() {
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
