package hashtable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

public class Entry<K, V> {
    Entry(@NonNull K key, @NonNull V value, int keyHash) {
        this.key = key;
        this.value = value;
        this.keyHash = keyHash;
    }

    @Getter
    private final K key;
    @Getter
    private final V value;
    @Getter(AccessLevel.PACKAGE)
    private final int keyHash;

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