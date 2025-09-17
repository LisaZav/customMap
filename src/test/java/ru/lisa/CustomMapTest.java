package ru.lisa;

import junit.framework.TestCase;

public class CustomMapTest extends TestCase {


    public void testGet() {
        //given
        CustomMap<String, String> map = new CustomHashMap<>();
        map.put("key", "value");
        //when
        String value = map.get("key");
        //then
        assertEquals("value", value);
    }

    public void testPut() {
        //given
        CustomMap<String, String> map = new CustomHashMap<>();
        //when
        map.put("key", "value");
        //then
        String value = map.get("key");
        assertNotNull(value);
    }

    public void testRemove() {
        //given
        CustomMap<String, String> map = new CustomHashMap<>();
        map.put("key", "value");
        //when
        map.remove("key");
        //then
        String value = map.get("key");
        assertNull(value);
    }
}