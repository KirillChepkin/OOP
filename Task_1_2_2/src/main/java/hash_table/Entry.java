package hash_table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

public class Entry<K, V> {
    Entry(@NonNull K key, V value, int keyHash) {
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

        Entry<K, V> entry = (Entry) obj;

        if (entry.keyHash != keyHash) {
            return false;
        }
        return Objects.equals(entry.key, key) && Objects.equals(entry.value, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, keyHash);
    }


    boolean keyEquals(Entry<K, V> entry) {
        if (entry.keyHash != this.keyHash) {
            return false;
        }
        return this.key.equals(entry.key);
    }
}