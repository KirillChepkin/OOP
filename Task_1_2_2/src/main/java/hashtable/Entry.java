
package hashtable;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/**
 * Represents a key-value pair stored in a hash table.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class Entry<K, V> {
    private final K key;
    private final V value;
    private final int keyHash;

    Entry(@NonNull K key, @NonNull V value, int keyHash) {
        this.key = key;
        this.value = value;
        this.keyHash = keyHash;
    }

    @Getter
    public K getKey() {
        return key;
    }

    @Getter
    public V getValue() {
        return value;
    }

    @Getter(AccessLevel.PACKAGE)
    public int getKeyHash() {
        return keyHash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        Entry<?, ?> entry = (Entry<?, ?>) obj;

        return Objects.equals(key, entry.key)
                && Objects.equals(value, entry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    boolean keyEquals(@NonNull K key, int keyHash) {
        if (keyHash != this.keyHash) {
            return false;
        }
        return this.key.equals(key);
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
