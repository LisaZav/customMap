package ru.lisa;

public interface CustomMap <K, V> {// сигнатура мет
    V get(Object key);
    V put(Object key, Object value);
    V remove(Object key);
}
