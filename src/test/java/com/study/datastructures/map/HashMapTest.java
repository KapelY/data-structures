package com.study.datastructures.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {
    private Map map = new HashMap();

    @BeforeEach
    void setUp() {
        map.clear();
    }

    @Test
    void put() {
        map.put("1", "11");
        map.put("1", "12");
        map.put("1", "13");
    }

    @Test
    void get() {
        assertEquals("11", map.get("1"));
    }

    @Test
    void size() {
    }

    @Test
    void containsKey() {
    }

    @Test
    void remove() {
    }
}