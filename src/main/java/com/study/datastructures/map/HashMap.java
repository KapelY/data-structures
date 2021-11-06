package com.study.datastructures.map;

import lombok.Data;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 5;

    private Entry<K, V>[] buckets;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        buckets = (Entry<K, V>[]) new Entry[capacity];
    }

    @Override
    public V put(K key, V value) {
        checkCapacity();
        var bucketIndex = getIndex(key, buckets.length);
        Entry<K, V> entry = findEntryInBucket(buckets[bucketIndex], key);
        if (entry != null) {
            entry.value = value;
        } else {
            addEntry(bucketIndex, new Entry<>(key, value, null));
            size++;
        }
        return value;
    }

    @Override
    public V get(Object key) {
        var bucketIndex = getIndex(key, buckets.length);
        Entry<K, V> entryInBucket = findEntryInBucket(buckets[bucketIndex], key);
        return entryInBucket != null ? entryInBucket.value : null;
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
        if (buckets[bucketIndex] != null) {
            Iterator<Map.Entry<K, V>> iterator = this.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    oldValue = entry.getValue();
                    iterator.remove();
                    break;
                }
            }
        }
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        size = 0;
        buckets = (Entry<K, V>[]) new Entry[buckets.length];
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Map.Entry<K, V> entry : this) {
            stringJoiner.add(String.valueOf(entry));
        }
        return stringJoiner.toString();
    }

    private void addEntry(int bucketIndex, Entry<K, V> kvEntry) {
        if (buckets[bucketIndex] != null) {
            kvEntry.next = buckets[bucketIndex];
            buckets[bucketIndex] = kvEntry;
        }
        buckets[bucketIndex] = kvEntry;
    }

    private void checkCapacity() {
        if (size >= buckets.length * 3 / 4) {
            allocateNewMap();
        }
    }

    @SuppressWarnings("unchecked")
    private void allocateNewMap() {
        var newBuckets = (Entry<K, V>[]) new Entry[buckets.length * 2];
        for (Map.Entry<K, V> kvEntry : this) {
            innerPut(newBuckets, kvEntry);
        }
        this.buckets = newBuckets;
    }

    private void innerPut(Entry<K, V>[] newBuckets, Map.Entry<K, V> entry) {
        var bucketIndex = getIndex(entry.getKey(), newBuckets.length);

        Entry<K, V> existingEntry = findEntryInBucket(newBuckets[bucketIndex], entry.getKey());
        if (existingEntry != null) {
            existingEntry.setValue(entry.getValue());
        } else {
            newBuckets[bucketIndex] = new Entry<>(entry.getKey(), entry.getValue(), null);
        }
    }

    private Entry<K, V> findEntryInBucket(Entry<K, V> kvEntry, Object key) {
        while (kvEntry != null) {
            if (Objects.equals(kvEntry.getKey(), key)) {
                return kvEntry;
            }
            kvEntry = kvEntry.next;
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

    int capacity() {
        return buckets.length;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    @Data
    static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private final int hash;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.hash = key.hashCode();
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }
    }

    private class HashMapIterator implements Iterator<Map.Entry<K, V>> {
        private static final String EXCEPTION_REMOVE_ITERATOR = "Before call iterator.remove() must be called iterator.next()";
        private int bucketIndex;
        private int entryCounter;
        private boolean nextWasCalled;
        private Entry<K, V> previousEntryInBucket;
        private Entry<K, V> currentEntry;

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
            return nextEntry();
        }

        private Entry<K, V> nextEntry() {
            if (currentEntry == null) {
                nextBucketIndex();
                currentEntry = buckets[bucketIndex];
                return currentEntry;
            }
            if (currentEntry.next == null) {
                bucketIndex++;
                nextBucketIndex();
                previousEntryInBucket = null;
                currentEntry = buckets[bucketIndex];
                return currentEntry;
            }

            previousEntryInBucket = currentEntry;
            currentEntry = currentEntry.next;
            return currentEntry;
        }

        private void nextBucketIndex() {
            for (; bucketIndex < buckets.length; bucketIndex++) {
                if (buckets[bucketIndex] != null) {
                    break;
                }
            }
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException(EXCEPTION_REMOVE_ITERATOR);
            }
            nextWasCalled = false;

            if (previousEntryInBucket == null && currentEntry.next == null) {
                buckets[bucketIndex] = null;
                currentEntry = null;
                size--;
                return;
            }
            if (previousEntryInBucket == null) {
                buckets[bucketIndex] = currentEntry.next;
                currentEntry = currentEntry.next;
                size--;
                return;
            }
            previousEntryInBucket.next = currentEntry.next;
            currentEntry = currentEntry.next;
            size--;
        }
    }
}
