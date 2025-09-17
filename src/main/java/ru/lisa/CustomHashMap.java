package ru.lisa;

public class CustomHashMap<K, V> implements CustomMap<K, V> {   //реал методлов интерфейса

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;
    private final float loadFactor;


    @SuppressWarnings("unchecked") // Подавление предупреждения о неявном приведении типа
    private CustomHashMap(int capacity, float loadFactor) {

        this.buckets = (Node<K, V>[]) new Node[capacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    public CustomHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @Override
    public V get(Object key) {
        int index = getIndex(key);
        Node<K, V> bucket = buckets[index];
        if (bucket == null) {
            return null;
        } else {
            if (bucket.key.hashCode() == key.hashCode()) {
                while (bucket != null) {
                    if (bucket.key.equals(key)) {
                        return bucket.value;
                    }
                    bucket = bucket.next;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V put(Object key, Object value) {
        if (size >= loadFactor * buckets.length) {
            resize();
        }

        int index = getIndex(key);

        Node<K, V> newNode = (Node<K, V>) new Node<>(key, value);
        if (buckets[index] == null) {
            buckets[index] = newNode;
        } else {
            Node<K, V> current = buckets[index];
            Node<K, V> prev = null;

            while (current != null) {
                if (keyMatch(current.key, key)) {
                    V tempValue = current.value;
                    current.value = (V) value;
                    return tempValue;
                }
                prev = current;
                current = current.next;
            }
            prev.next = newNode;
        }
        size++;

        return null;
    }


    @Override
    public V remove(Object key) {

        int index = getIndex(key);     //считаем индекс корз
        Node<K, V> current = buckets[index]; // обходим цепь
        Node<K, V> prev = null;

        while (current != null) {

            if (keyMatch(current.key, key)) { // нашли ключ
                V removedValue = current.value;
                if (prev == null) {  // удал если 1
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next; //если нет
                }
                size--;
                return removedValue;
            }
            prev = current;
            current = current.next; // переход к сл ноде
        }
        return null;
    }

    private int getIndex(Object key) {
        int hash = key.hashCode();
        int bucketIndex = hash % buckets.length;
        return Math.abs(bucketIndex);
    }

    private boolean keyMatch(Object key1, Object key2) {
        if (key1 == null) {
            return key2 == null;
        }
        return key1.equals(key2);
    }

    @SuppressWarnings("unchecked")
    private void resize() {

        int newCapacity = buckets.length * 2;
        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[newCapacity];
        for (Node<K, V> node : buckets) {
            while (node != null) {
                Node<K, V> next = node.next;
                int newIndex = Math.abs(node.key.hashCode() % newCapacity);
                node.next = newBuckets[newIndex];
                newBuckets[newIndex] = node;
                node = next;
            }
        }
        buckets = newBuckets;
    }

        private static class Node<K, V> {  //класс для node
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
