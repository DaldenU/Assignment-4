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
        HashNode<K, V> node = chainArray[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public V remove(K key) {
        return null;
    }

    public boolean contains(V value) {
        return true;
    }

    public K getKey(V value) {
        return null;
    }
}
