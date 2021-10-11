package com.study.datastructures.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.datastructures.list.ArrayList.*;
import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {
    private final List defaultArrayList = new ArrayList();

    @BeforeEach
    void setUp() {
        defaultArrayList.clear();
        defaultArrayList.add("A");
        defaultArrayList.add("B");
        defaultArrayList.add("C");
    }


    @Test
    @DisplayName("When call 'add(Object value)' then size increase")
    void add() {
        assertEquals(3, defaultArrayList.size());
        defaultArrayList.add("A");
        assertEquals(4, defaultArrayList.size());
    }

    @Test
    @DisplayName("When call 'add(Object value, int index)' then size increase")
    void testAdd() {
        defaultArrayList.add("A", 0);
        assertEquals(4, defaultArrayList.size());
    }

    @Test
    void remove() {
        assertEquals("A", defaultArrayList.remove(0));
        assertEquals(2, defaultArrayList.size());
    }

    @Test
    void get() {
        assertEquals("A", defaultArrayList.get(0));
    }

    @Test
    void set() {
        assertEquals("C", defaultArrayList.set("D", 2));
    }

    @Test
    void clear() {
        defaultArrayList.clear();
        assertEquals(0, defaultArrayList.size());
    }

    @Test
    void size() {
        assertEquals(3, defaultArrayList.size());
    }

    @Test
    void isNotEmpty() {
        assertFalse(defaultArrayList.isEmpty());
    }

    @Test
    void isNEmpty() {
        defaultArrayList.clear();
        assertTrue(defaultArrayList.isEmpty());
    }

    @Test
    void contains() {
        assertTrue(defaultArrayList.contains("A"));
        assertTrue(defaultArrayList.contains("B"));
        assertTrue(defaultArrayList.contains("C"));
        assertFalse(defaultArrayList.contains("D"));
    }

    @Test
    void indexOf() {
        defaultArrayList.add("A");
        assertEquals(0, defaultArrayList.indexOf("A"));
    }

    @Test
    void lastIndexOf() {
        defaultArrayList.add("A");
        assertEquals(3, defaultArrayList.lastIndexOf("A"));
    }

    @Test
    void testToString() {
        assertEquals("[A, B, C]", defaultArrayList.toString());
    }

    @Test
    @DisplayName("When the size of the array becomes more than 10 the array capacity increase")
    void whenSizeMoreThan10() {
        defaultArrayList.clear();
        for (int i = 0; i < 11; i++) {
            defaultArrayList.add(i);
        }

        assertEquals(11, defaultArrayList.size());
    }

    @Test
    @DisplayName("When remove out of range, then check range throws IndexOutOfBoundsException()")
    void removeCheckRange() {
        defaultArrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> defaultArrayList.remove(0));

        assertTrue(exception.getMessage().contains(EXCEPTION_REMOVE_VALUE));
    }

    @Test
    @DisplayName("When add with index out of range, then check range throws IndexOutOfBoundsException()")
    void addWithIndexCheckRange() {
        defaultArrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> defaultArrayList.add("A", 1));

        assertTrue(exception.getMessage().contains(EXCEPTION_ADD_VALUE));
    }

    @Test
    @DisplayName("When get with index out of range, then check range throws IndexOutOfBoundsException()")
    void getCheckRange() {
        defaultArrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> defaultArrayList.get(0));

        assertTrue(exception.getMessage().contains(EXCEPTION_GET_VALUE));
    }
}