import hashtable.HashTable;
import hashtable.Entry;

public class Main {
    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable<>();

        // 1. Insert key-value pairs
        table.put("Alice", 20);
        table.put("Bob", 25);
        table.put("Charlie", 30);
        System.out.println(table);

        // 2. Retrieve a value
        System.out.println("Bob's age: " + table.get("Bob"));

        // 3. Check whether a key exists
        System.out.println("Contains Alice: "
                + table.containsKey("Alice"));

        // 4. Update an existing value
        table.put("Bob", 26);
        System.out.println("Updated Bob: " + table.get("Bob"));

        // 5. Remove an entry
        table.remove("Charlie");
        System.out.println("After removal: " + table);

        // 6. Iterate over entries
        for (Entry<String, Integer> entry : table) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("Number of elements: " + table.getElementCount());
    }
}