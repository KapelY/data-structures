package com.study.datastructures.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    List linkedList = new LinkedList();

    @BeforeEach
    void setUp() {
        linkedList.clear();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
    }

    @Test
    void addAndGet() {
        assertEquals("1", linkedList.get(0));
        assertEquals("2", linkedList.get(1));
        assertEquals("3", linkedList.get(2));
    }

    @Test
    void testAddByIndex() {

    }

    @Test
    void remove() {
    }

    @Test
    void get() {
    }

    @Test
    void set() {
    }

    @Test
    void clear() {
    }

    @Test
    void size() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void contains() {
    }

    @Test
    void indexOf() {
    }

    @Test
    void lastIndexOf() {
    }
}