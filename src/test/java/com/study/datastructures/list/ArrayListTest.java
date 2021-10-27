package com.study.datastructures.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.study.datastructures.list.ArrayList.*;
import static com.study.datastructures.list.LinkedList.EXCEPTION_REMOVE_ITERATOR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {
    private final List<String> arrayList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        arrayList.clear();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
    }


    @Test
    @DisplayName("When call 'add(Object value)' then size increase")
    void add() {
        assertEquals(3, arrayList.size());
        arrayList.add("A");
        assertEquals(4, arrayList.size());
    }

    @Test
    @DisplayName("When call 'add(Object value, int index)' then size increase")
    void testAdd() {
        arrayList.add("A", 0);
        assertEquals(4, arrayList.size());
    }

    @Test
    void remove() {
        assertEquals("A", arrayList.remove(0));
        assertEquals(2, arrayList.size());
    }

    @Test
    void get() {
        assertEquals("A", arrayList.get(0));
    }

    @Test
    void set() {
        assertEquals("C", arrayList.set("D", 2));
    }

    @Test
    void clear() {
        arrayList.clear();
        assertEquals(0, arrayList.size());
    }

    @Test
    void size() {
        assertEquals(3, arrayList.size());
    }

    @Test
    void isNotEmpty() {
        assertFalse(arrayList.isEmpty());
    }

    @Test
    void isNEmpty() {
        arrayList.clear();
        assertTrue(arrayList.isEmpty());
    }

    @Test
    void contains() {
        assertTrue(arrayList.contains("A"));
        assertTrue(arrayList.contains("B"));
        assertTrue(arrayList.contains("C"));
        assertFalse(arrayList.contains("D"));
    }

    @Test
    void indexOf() {
        arrayList.add("A");
        assertEquals(0, arrayList.indexOf("A"));
    }

    @Test
    void lastIndexOf() {
        arrayList.add("A");
        assertEquals(3, arrayList.lastIndexOf("A"));
    }

    @Test
    void testToString() {
        assertEquals("[A, B, C]", arrayList.toString());
    }

    @Test
    @DisplayName("When call 'iterator.next()', then correct output is shown")
    void whenCallForeachThenCorrectOutputIsShown() {
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        Iterator<String> iterator = arrayList.iterator();
        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
    }

    @Test
    @DisplayName("When call 'iterator.next' on empty list, then thrown NoSuchElementException")
    void whenCall() {
        clear();
        Iterator<String> iterator = arrayList.iterator();
        assertThrows(
                NoSuchElementException.class,
                iterator::next);

    }

    @Test
    @DisplayName("When call iterator.remove(), then it must be removed correctly")
    void whenCallIteratorRemoveThenItMustBeRemovedCorrectly() {
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        Iterator<String> iterator = arrayList.iterator();
        assertThat(iterator.next(), is("A"));
        iterator.remove();
        assertThat(iterator.next(), is("B"));
        assertThat(iterator.next(), is("C"));
        clear();

        arrayList.add("A");
        iterator = arrayList.iterator();
        iterator.next();
        iterator.remove();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("When call 'iterator.next' on empty list, then thrown NoSuchElementException")
    void expectedNoSuchElementException() {
        clear();
        Iterator<String> iterator = arrayList.iterator();
        assertThrows(
                NoSuchElementException.class,
                iterator::next);
    }

    @Test
    @DisplayName("When call 'iterator.remove()' without calling 'iterator.next()' before, " +
            "then custom exception must be thrown ")
    void whenCallIteratorRemoveWithoutCallingIteratorNextBeforeThenCustomExceptionMustBeThrown() {
        arrayList.add("!");
        arrayList.add("!");
        arrayList.add("!");
        Iterator<String> iterator = arrayList.iterator();
        Exception exception = assertThrows(
                IllegalStateException.class,
                iterator::remove);

        assertTrue(exception.getMessage().contains(EXCEPTION_REMOVE_ITERATOR));
    }

    @Test
    @DisplayName("When the size of the array becomes more than 10 the array capacity increase")
    void whenSizeMoreThan10() {
        arrayList.clear();
        for (int i = 0; i < 11; i++) {
            arrayList.add(String.valueOf(i));
        }

        assertEquals(11, arrayList.size());
    }

    @Test
    @DisplayName("When remove out of range, then check range throws IndexOutOfBoundsException()")
    void removeCheckRange() {
        arrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> arrayList.remove(0));

        assertTrue(exception.getMessage().contains(EXCEPTION_REMOVE_VALUE));
    }

    @Test
    @DisplayName("When add with index out of range, then check range throws IndexOutOfBoundsException()")
    void addWithIndexCheckRange() {
        arrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> arrayList.add("A", 1));

        assertTrue(exception.getMessage().contains(EXCEPTION_ADD_VALUE));
    }

    @Test
    @DisplayName("When get with index out of range, then check range throws IndexOutOfBoundsException()")
    void getCheckRange() {
        arrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> arrayList.get(0));

        assertTrue(exception.getMessage().contains(EXCEPTION_GET_VALUE));
    }

    @Test
    @DisplayName("When get with index out of range, then check range throws IndexOutOfBoundsException()")
    void setCheckRange() {
        arrayList.clear();
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> arrayList.set("", 0));

        assertTrue(exception.getMessage().contains(EXCEPTION_SET_VALUE));
    }
}