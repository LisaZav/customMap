package ru.lisa;

public interface CustomMap <K, V> {
    V get(K key);
    V put(K key, V value);
    V remove(K key);
}
