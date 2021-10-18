package com.study.datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.study.datastructures.list.LinkedList.EXCEPTION_REMOVE_ITERATOR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("When call 'toString()'")
    void whenCallToString() {
        map.put("1", "11");
        map.put("2", "12");
        assertEquals(2, map.size());
        assertEquals(
                "[[HashMap.Entry(key=2, value=12)], " +
                        "[HashMap.Entry(key=1, value=11)]]",
                map.toString());
        assertEquals("12", map.remove("2"));
        assertEquals(1, map.size());
        assertEquals(
                "[[HashMap.Entry(key=1, value=11)]]",
                map.toString());
    }

    @Test
    @DisplayName("When create HashMap with custom bucket size, then it should be created")
    void whenCreateHashMapWithCustomBucketSizeThenItShouldBeCreated() {
        HashMap customMapSize100 = new HashMap(100);
        assertEquals(100, customMapSize100.capacity());
    }

    @Test
    @DisplayName("When call iter over the map, then correct data is shown")
    void whenCallIterOverTheMapThenCorrectDataIsShown() {
        map.put("1", "A");
        final Iterator iterator = map.iterator();
        assertThat((HashMap.Entry)iterator.next(), is(new HashMap.Entry("1", "A")));
    }

    @Test
    @DisplayName("When call 'iterator.remove()', then the entry removed")
    void whenCallIteratorRemoveThenTheEntryRemoved() {
        map.put("1", "A");
        final Iterator iterator = map.iterator();
        iterator.next();
        iterator.remove();
        assertThat(map.size(), is(0));
        assertTrue(Objects.isNull(map.get("1")));
    }

    @Test
    @DisplayName("When call 'iterator.remove()' without calling 'iterator.next()' before, " +
            "then custom exception must be thrown ")
    void whenCallIteratorRemoveWithoutCallingIteratorNextBeforeThenCustomExceptionMustBeThrown() {
        map.put("1", "11");
        map.put("2", "22");
        map.put("3", 33);
        Iterator iterator = map.iterator();
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> iterator.remove());

        assertTrue(exception.getMessage().contains(EXCEPTION_REMOVE_ITERATOR));
    }

    @Test
    @DisplayName("When call 'iterator.next' on empty list, then thrown NoSuchElementException")
    void whenCall() {
        map.clear();
        Iterator iterator = map.iterator();
        assertThrows(
                NoSuchElementException.class,
                () -> iterator.next());

    }
}