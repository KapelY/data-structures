package com.study.datastructures.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.study.datastructures.list.LinkedList.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    LinkedList<String> linkedList = new LinkedList<>();

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
    @DisplayName("When call 'add(Object value)' then size increases")
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
    @DisplayName("Given list.of(A), when call add(B, 0) expected [B, A]")
    void addBeforeElement() {
        clear();
        linkedList.add("A");
        linkedList.add("B", 0);
        assertEquals("[B, A]", linkedList.toString());
    }

    @Test
    @DisplayName("Given list.of(A), when call add(B, 1) expected [A, B]")
    void addAfterElement() {
        clear();
        linkedList.add("A");
        linkedList.add("B", 1);
        assertEquals("[A, B]", linkedList.toString());
    }

    @Test
    @DisplayName("When call 'add(Object value, int index)' then correct insert")
    void addByIndex() {
        linkedList.add("A", 0);
        linkedList.add("B", 1);
        linkedList.add("C", 2);
        linkedList.add("D", 0);
        assertEquals("[D, A, B, C]", linkedList.toString());
        linkedList.add("F", 1);
        assertEquals("[D, F, A, B, C]", linkedList.toString());
        linkedList.add("R", 4);
        assertEquals("[D, F, A, B, R, C]", linkedList.toString());
    }

    @Test
    @DisplayName("When add value by index 1 in the list of more then 2 elements")
    void whenAddValueByIndex1InTheListOfMoreThen2Elements() {
         linkedList.add("A");
         linkedList.add("B");
         linkedList.add("D");
         linkedList.add("E");
         linkedList.add("C", 1);
         assertEquals("[A, C, B, D, E]", linkedList.toString());
    }

    @Test
    @DisplayName("When call 'add(Object value)' then size increases")
    void addAndSize() {
        assertEquals(0, linkedList.size());
        linkedList.add("A");
        assertEquals(1, linkedList.size());
    }

    @Test
    void remove() {
        linkedList.add("A");
        assertEquals("A", linkedList.remove(0));
        assertEquals(0, linkedList.size());
        linkedList.add("B");
        linkedList.add("C");
        assertEquals("C", linkedList.remove(1));
        assertEquals(1, linkedList.size());
        assertEquals("B", linkedList.remove(0));
    }
    @Test
    void removeHeadTail() {
        linkedList.add("A");
        linkedList.add("B");
        assertEquals("A", linkedList.remove(0));
        clear();
        linkedList.add("A");
        linkedList.add("B");
        assertEquals("B", linkedList.remove(1));
    }

    @Test
    void get() {
        linkedList.add("1");
        assertEquals("1", linkedList.get(0));
        linkedList.add("2");
        linkedList.add("3");
        assertEquals("2", linkedList.get(1));
        assertEquals("3", linkedList.get(2));

    }

    @Test
    void set() {
        linkedList.add("1");
        assertEquals("1", linkedList.set("2", 0));
        linkedList.add("3");
        linkedList.add("4");
        assertEquals("4", linkedList.set("0", 2));
    }

    @Test
    void clear() {
        linkedList.clear();
        assertEquals(0, linkedList.size());
    }

    @Test
    void size() {
        assertEquals(0, linkedList.size());
        linkedList.add("100");
        assertEquals(1, linkedList.size());
    }

    @Test
    void isNotEmpty() {
        linkedList.add("#");
        assertFalse(linkedList.isEmpty());
        linkedList.clear();
        assertEquals(0, linkedList.size());
    }

    @Test
    void isEmpty() {
        linkedList.clear();
        assertTrue(linkedList.isEmpty());
    }

    @Test
    void contains() {
        linkedList.add("A");
        assertTrue(linkedList.contains("A"));
        assertFalse(linkedList.contains("B"));
        linkedList.add("C");
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
        linkedList.add("A");
        linkedList.add("A");
        linkedList.add("A");
        linkedList.add("A");
        assertEquals(4, linkedList.lastIndexOf("A"));
    }

    @Test
    void testToString() {
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        assertEquals("[A, B, C]", linkedList.toString());
    }

    @Test
    @DisplayName("When call 'iterator.next()', then correct output is shown")
    void whenCallForeachThenCorrectOutputIsShown() {
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        Iterator<String> iterator = linkedList.iterator();
        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
    }

    @Test
    @DisplayName("When call 'iterator.next' on empty list, then thrown NoSuchElementException")
    void expectedNoSuchElementException() {
        Iterator<String> iterator = linkedList.iterator();
        assertThrows(
                NoSuchElementException.class,
                iterator::next);
    }

    @Test
    @DisplayName("When call 'iterator.remove()' without calling 'iterator.next()' before, " +
            "then custom exception mustBe thrown ")
    void whenCallIteratorRemoveWithoutCallingIteratorNextBeforeThenCustomExceptionMustBeThrown() {
        linkedList.add("!");
        Iterator<String> iterator = linkedList.iterator();
        Exception exception = assertThrows(
                IllegalStateException.class,
                iterator::remove);

        assertTrue(exception.getMessage().contains(EXCEPTION_REMOVE_ITERATOR));
    }

    @Test
    @DisplayName("When call iterator.remove(), then it must be removed correctly")
    void whenCallIteratorRemoveThenItMustBeRemovedCorrectly() {
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        Iterator<String> iterator = linkedList.iterator();
        assertThat(iterator.next(), is("A"));
        iterator.remove();
        assertThat(iterator.next(), is("B"));
        assertThat(iterator.next(), is("C"));
        clear();

        linkedList.add("A");
        iterator = linkedList.iterator();
        assertThat(iterator.next(), is("A"));
        iterator.remove();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("When call 'iterator.remove()' at the end of the list, then correct remove.")
    void whenCallIteratorRemoveAtTheEndOfTheListThenCorrectRemove() {
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        Iterator<String> iterator = linkedList.iterator();
        assertThat(iterator.next(), is("A"));
        assertThat(iterator.next(), is("B"));
        assertThat(iterator.next(), is("C"));
        iterator.remove();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    @DisplayName("When call 'iterator.remove()' in the middle of the list, then correct remove.")
    void whenCallIteratorRemoveInTheMiddleOfTheListThenCorrectRemove() {
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");
        Iterator<String> iterator = linkedList.iterator();
        assertThat(iterator.next(), is("A"));
        assertThat(iterator.next(), is("B"));
        iterator.remove();
        assertThat(iterator.next(), is("C"));
    }

    @Test
    @DisplayName("When the size of the array becomes more than 10 the array capacity increases")
    void whenSizeMoreThan10() {
        linkedList.clear();
        for (int i = 0; i < 11; i++) {
            linkedList.add(String.valueOf(i));
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
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> linkedList.set("0", 0));

        assertTrue(exception.getMessage().contains(EXCEPTION_SET_VALUE));
    }
}