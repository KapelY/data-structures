package com.study.datastructures.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.datastructures.list.LinkedList.*;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    List linkedList = new LinkedList();

    @BeforeEach
    void setUp() {
        clear();
    }

    @Test
    @DisplayName("When call clear(), then it should be cleaned")
    void whenCallClearThenItShouldBeCleaned() {
         assertEquals(0, linkedList.size());
         linkedList.add("1");
         assertEquals(1, linkedList.size());
         clear();
        assertEquals(0, linkedList.size());
    }

    @Test
    @DisplayName("When call 'add(Object value)' then size increase")
    void add() {
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        linkedList.add("D");
        linkedList.add("E");
        linkedList.add("F");
        assertEquals("[A, B, C, D, E, F]", linkedList.toString());
    }

    @Test
    @DisplayName("When call 'add(Object value, int index)' then correct insert")
    void addByIndex() {
        linkedList.add("A",0);
        linkedList.add("B",1);
        linkedList.add("C",2);
        linkedList.add("D",0);
        assertEquals("[D, A, B, C]", linkedList.toString());
        linkedList.add("F",1);
        assertEquals("[D, F, A, B, C]", linkedList.toString());
        linkedList.add("R",4);
        assertEquals("[D, F, A, B, R, C]", linkedList.toString());

    }

    @Test
    @DisplayName("When call 'add(Object value)' then size increase")
    void addAndSize() {
        assertEquals(3, linkedList.size());
        linkedList.add("A");
        assertEquals(4, linkedList.size());
    }

    @Test
    @DisplayName("When call 'add(Object value, int index)' then size increase")
    void testAdd() {
        linkedList.add("A", 0);
        assertEquals(4, linkedList.size());
    }

    @Test
    void remove() {
        assertEquals("A", linkedList.remove(0));
        assertEquals(2, linkedList.size());
    }

    @Test
    void get() {
        assertEquals("A", linkedList.get(0));
    }

    @Test
    void set() {
        assertEquals("C", linkedList.set("D", 2));
    }

    @Test
    void clear() {
        linkedList.clear();
        assertEquals(0, linkedList.size());
    }

    @Test
    void size() {
        assertEquals(3, linkedList.size());
    }

    @Test
    void isNotEmpty() {
        assertFalse(linkedList.isEmpty());
    }

    @Test
    void isNEmpty() {
        linkedList.clear();
        assertTrue(linkedList.isEmpty());
    }

    @Test
    void contains() {
        assertTrue(linkedList.contains("A"));
        assertTrue(linkedList.contains("B"));
        assertTrue(linkedList.contains("C"));
        assertFalse(linkedList.contains("D"));
    }

    @Test
    void indexOf() {
        linkedList.add("A");
        assertEquals(0, linkedList.indexOf("A"));
    }

    @Test
    void lastIndexOf() {
        linkedList.add("A");
        assertEquals(3, linkedList.lastIndexOf("A"));
    }

    @Test
    void testToString() {
        assertEquals("[A, B, C]", linkedList.toString());
    }

    @Test
    @DisplayName("When the size of the array becomes more than 10 the array capacity increase")
    void whenSizeMoreThan10() {
        linkedList.clear();
        for (int i = 0; i < 11; i++) {
            linkedList.add(i);
        }

        assertEquals(11, linkedList.size());
    }

    @Test
    @DisplayName("When remove out of range, then check range throws IndexOutOfBoundsException()")
    void removeCheckRange() {
        linkedList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> linkedList.remove(0));

        assertTrue(exception.getMessage().contains(EXCEPTION_REMOVE_VALUE));
    }

    @Test
    @DisplayName("When add with index out of range, then check range throws IndexOutOfBoundsException()")
    void addWithIndexCheckRange() {
        linkedList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> linkedList.add("A", 1));

        assertTrue(exception.getMessage().contains(EXCEPTION_ADD_VALUE));
    }

    @Test
    @DisplayName("When get with index out of range, then check range throws IndexOutOfBoundsException()")
    void getCheckRange() {
        linkedList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> linkedList.get(0));

        assertTrue(exception.getMessage().contains(EXCEPTION_GET_VALUE));
    }

    @Test
    @DisplayName("When get with index out of range, then check range throws IndexOutOfBoundsException()")
    void setCheckRange() {
        linkedList.clear();
        System.out.println(linkedList.size());
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> linkedList.set(0, 0));

        assertTrue(exception.getMessage().contains(EXCEPTION_SET_VALUE));
    }
}