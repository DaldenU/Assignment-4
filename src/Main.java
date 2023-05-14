import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();
        Random rand = new Random();

        // Add 10000 random elements to the hash table
        for (int i = 0; i < 10000; i++) {
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            MyTestingClass key = new MyTestingClass(x, y);
            Student value = new Student("Student " + i, i);
            table.put(key, value);
        }

        // Print the number of elements in each bucket
        for (int i = 0; i < table.getCapacity(); i++) {
            System.out.println("Bucket " + i + ": " + table.getBucketSize(i));
        }
    }


}
