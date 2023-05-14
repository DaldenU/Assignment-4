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
        int hash = 0;
        String strKey = key.toString();
        for (int i = 0; i < strKey.length(); i++) {
            hash = (hash * 31) + strKey.charAt(i);
        }
        return hash % M;
    }

    public void put(K key, V value) {
    }

    public V get(K key) {
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
