public class MyHashTable<K, V> {

    // Nested class for storing keys and values in linked list nodes
    private class HashNode<K, V> {
        private K key; // Key
        private V value; // Value
        private HashNode<K, V> next; // Reference to the next node in the linked list

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray; // Array of linked lists (chains)
    private int M = 11; // Initial size of the array
    private int size; // Number of elements in the hash table

    public MyHashTable() {
        chainArray = new HashNode[M]; // standard constructor
        size = 0;
    }

    public MyHashTable(int M) {
        this.M = M;
        chainArray = new HashNode[M];   // constructor for a certain size
        size = 0;
    }

    // Hash function that maps a key to an index in the array
    private int hash(K key) {
        String strKey = String.valueOf(key);
        int sum = 0;
        for (int i = 0; i < strKey.length(); i++) {
            sum += strKey.charAt(i);
        }

        return sum % M;
    }

    // Inserts a key-value pair into the hash table
    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> node = new HashNode<K, V>(key, value);

        // If the chain at the computed index is empty, add the new node as the head of the chain
        if (chainArray[index] == null) {
            chainArray[index] = node;
            size++;
        }
        // Otherwise, append the new node to the end of the chain
        else {
            HashNode<K, V> currentNode = chainArray[index];
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = node;
            size++;
        }

        // If the load factor exceeds 0.7, double the size of the array and rehash all the elements
        if (M / size < 0.7) {
            M *= 2;
            HashNode<K, V>[] newChainArray = new HashNode[M];
            for (int j = 0; j < chainArray.length; j++) {
                newChainArray[j] = chainArray[j];
            }
            chainArray = newChainArray;
        }
    }

    // Retrieves the value associated with the specified key
    public V get(K key) {
        int index = hash(key);
        if (chainArray[index] == null) {
            return null;
        } else {
            HashNode<K, V> currentNode = chainArray[index];
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }
            while (currentNode != null) {
                if (currentNode.key.equals(key)) {
                    return currentNode.value;
                }
                currentNode = currentNode.next;
            }
        }
        return null;
    }

    /**
     * Removes the node with the given key and returns the associated value.
     *
     * @param key - the key to remove
     * @return the value associated with the removed key, or null if the key was not found
     */
    public V remove(K key) {
        int index = hash(key);
        // If the bucket at the hashed index is empty, the key is not in the map
        if (chainArray[index] == null) {
            return null;
        } else {
            // If the first node in the bucket matches the given key, remove it and return its value
            HashNode<K, V> currentNode = chainArray[index];
            if (currentNode.key.equals(key)) {
                chainArray[index] = currentNode.next;
                size--;
                return currentNode.value;
            } else {
                // Otherwise, search the linked list for the key
                HashNode<K, V> previousNode = currentNode;
                currentNode = currentNode.next;
                while (currentNode != null) {
                    if (currentNode.key.equals(key)) {
                        previousNode.next = currentNode.next;
                        size--;
                        return currentNode.value;
                    }
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }
                // Key was not found in the linked list
                return null;
            }
        }
    }

    /**
     * Checks whether the map contains a node with the given value.
     *
     * @param value - the value to search for
     * @return true if a node with the given value is found, false otherwise
     */
    public boolean contains(V value) {
        for (int i = 0; i < chainArray.length; i++) {
            if (chainArray[i] == null) {
                continue;
            }
            HashNode<K, V> currentNode = chainArray[i];
            if (currentNode.value.equals(value)) {
                return true;
            } else {
                while (currentNode.next != null) {
                    if (currentNode.value.equals(value)) {
                        return true;
                    }
                    currentNode = currentNode.next;
                }
            }
        }
        // Value was not found in any of the buckets
        return false;
    }

    /**
     * Returns the key associated with the given value.
     *
     * @param value - the value to search for
     * @return the key associated with the given value, or null if the value was not found
     */
    public K getKey(V value) {
        for (int i = 0; i < chainArray.length; i++) {
            if (chainArray[i] == null) {
                continue;
            }
            if (chainArray[i].value.equals(value)) {
                return chainArray[i].key;
            }
            HashNode<K, V> currentNode = chainArray[i];
            while (currentNode != null) {
                if (currentNode.value.equals(value)) {
                    return currentNode.key;
                }
                currentNode = currentNode.next;
            }
        }
        // Value was not found in any of the nodes
        return null;
    }

    public int getBucketSize(int index) {
        // Check that the index is valid
        if (index < 0 || index >= chainArray.length) {
            throw new IllegalArgumentException("Invalid index");
        }

        // Traverse the chain at the specified index and count the number of nodes
        int size = 0;
        HashNode<K, V> currentNode = chainArray[index];
        while (currentNode != null) {
            size++;
            currentNode = currentNode.next;
        }
        return size;
    }

    public int getCapacity() {
        // Return the length of the chainArray
        return chainArray.length;
    }
}
