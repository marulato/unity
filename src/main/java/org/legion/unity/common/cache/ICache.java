package org.legion.unity.common.cache;

public interface ICache <K, V> {

    V get(K key);

    void set(K key, V value);
}
