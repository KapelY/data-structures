package com.study.datastructures.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashMapTest {
    private Map map = new HashMap();

    @BeforeEach
    void setUp() {
        map.clear();
    }

    @Test
    @DisplayName("When call 'put(key, value)' for the first time))")
    void put() {
        map.put("1", "11");
        map.put("1", "12");
        map.put("1", "13");
    }

    @Test
    @DisplayName("When call 'put(key, value)' for the second time and you've got a collision OMG ;o) ")
    void putWithCollision() {
        map.put("1", "collision#1"); // #1
        map.put("104", "collision#1"); // #1
        map.put("200", "collision#2"); // #2
        map.put("101", "collision#2"); // #2
        assertEquals(4, map.size());
    }

    @Test
    @DisplayName("When call 'get(key)' right after 'put(key, value)', then correct value should return")
    void get() {
        assertEquals(true, map.put("1", "11").equals("11"));
        map.put("2", "12");
        map.put("3", "13");
        assertEquals(true, map.put("4", "14").equals("14"));
        assertEquals("11", map.get("1"));
        assertEquals("14", map.get("4"));
    }

    @Test
    @DisplayName("As easy as it is, nothing special - 'size()'")
    void size() {
        map.put("1", "11");
        map.put("2", "12");
        map.put("3", "12");
        assertEquals(3, map.size());
        map.put("1", "11");
        map.put("1", "12");
        map.put("1", "13");
        map.put("2", "12");
        map.put("3", "12");
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("You should check if something is inside, and of course it should work")
    void containsKey() {
        map.put("1", "11");
        map.put("2", "12");
        assertEquals(true, map.containsKey("1"));
        assertEquals(true, map.containsKey("2"));
    }

    @Test
    @DisplayName("When call 'remove(key)' on the last entry in a bucket, then bucket should be null(not empty list)")
    void remove() {
        map.put("1", "11");
        map.put("2", "12");
        assertEquals(2, map.size());
        assertEquals(
                "[[HashMap.Entry(key=2, value=12)], " +
                        "null, " +
                        "null, " +
                        "null, " +
                        "[HashMap.Entry(key=1, value=11)]]",
                map.toString());
        assertEquals("12", map.remove("2"));
        assertEquals(1, map.size());
        assertEquals(
                "[null, " +
                        "null, " +
                        "null, " +
                        "null, " +
                        "[HashMap.Entry(key=1, value=11)]]",
                map.toString());
    }

    @Test
    @DisplayName("When create HashMap with custom bucket size, then it should be created")
    void whenCreateHashMapWithCustomBucketSizeThenItShouldBeCreated() {
        HashMap customMapSize100 = new HashMap(100);
        assertEquals(100, customMapSize100.capacity());
    }
}