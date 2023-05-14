public class MyHashTable<K, V> {
    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
    }

    public MyHashTable(int M) {
    }

    private int hash(K key) {
        String strKey = String.valueOf(key);
        int sum = 0;
        for (int i = 0; i < strKey.length(); i++) {
            sum += strKey.charAt(i);
        }

        return sum % M;
    }

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
}
