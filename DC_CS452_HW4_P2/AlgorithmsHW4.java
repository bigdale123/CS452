import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.Object;
import java.util.LinkedList;
import java.io.*;
import java.util.*;
import java.lang.Math;

/**
 *
 * @author sidharth
 */
/*
 * The goal of this assignment is to implement a dynamic map (key, value), with positive integer keys and positive integer values.
   The universe size for the keys is 16777216 (2^24), with the keys being in the range [0, 16777215]

   You must implement the following functions:
   insert (int key, int value) : insert the (key, value) pair. While inserting (K1, V1), if there already exists a key K1 in the map, then replace the existing value with the value V1
   search (int key) : returns the value corresponding to the key and if the key does not exist then returns -1
   minimum () : returns the (key value) pair which has the minimal key
   maximum () : returns the (key value) pair which has the maximum key

 * The map must be implemented using a Binary search tree and using an augmented direct address table
 *
 */


/*
  [100 points]
  A map implemented via a binary search tree
 */
class map_BST {

    // TODO: Create your private data variables
    public map_BST left_node = null;
    public map_BST right_node = null;
    public int key = -1;
    public int value = -1;

    // TODO: Implement your constructor
    public map_BST(int key, int value) {
        this.key = key;
        this.value = value;
    }


    // [20 Points]
    // return -1 if the key already exists, and you had to perform a replace operation
    // return the key if the key is successfully inserted
    // (Feel free to change the function signature)
    public int insert (int key, int value) {
        // TODO: Implement this
        if (this.key == -1) { // empty tree
            this.key = key;
            this.value = value;
            return key;
        } else if (key < this.key && this.left_node != null) { // left node exists, recurse
            return this.left_node.insert(key, value);
        } else if (key < this.key && this.left_node == null) { // left node does not exist
            this.left_node = new map_BST(key, value);
            return key;
        } else if (key > this.key && this.right_node != null) { //right node exists, recurse
            return this.right_node.insert(key, value);
        } else if (key > this.key && this.right_node == null) { // right node does not exist
            this.right_node = new map_BST(key, value);
            return key;
        } else { // key exists, is current node
            this.value = value;
            return -1;
        }
    }

    // [20 Points]
    // return value if the key already exists
    // return -1 if the key does not exist
    public int search (int key) {
        // TODO: Implement this
        if (key == this.key) { // key == current node
            return this.value;
        } else if (key < this.key && this.key != -1 && this.left_node != null) {
            return this.left_node.search(key);
        } else if (key > this.key && this.key != -1 && this.right_node != null) {
            return this.right_node.search(key);
        } else {
            return -1;
        }

    }

    // [20 Points]
    // return the maximum key-value
    // return -1 if the hash able is empty
    // (Feel free to change the function signature)
    public int maximum () {
        // TODO: Implement this
        if (this.key == -1) { // empty tree
            return -1;
        } else if (this.right_node != null) {
            return this.right_node.maximum();
        } else {
            return this.key;
        }

    }

    // [20 Points]
    // return the minimum key-value
    // return -1 if the hash able is empty
    // (Feel free to change the function signature)
    public int minimum () {
        // TODO: Implement this

        if (this.key == -1) { // empty tree
            return -1;
        } else if (this.left_node != null) {
            return this.left_node.minimum();
        } else {
            return this.key;
        }
    }


    // [20 Points]
    // Return an array arr of size 2
    // arr[0] the key which has most number of occurence,
    // arr[1] the number of occurence of arr[0]
    public int[] return_key_with_maximum_repetition() {
        // You will have to traverse the data structure
        // TODO: Implement this
        int[] arr = new int[2]; // combining both statements in one
        arr[0] = this.key;
        arr[1] = this.value;
        if (this.left_node == null && this.right_node == null) {
            return arr;
        } else {
            int[] l_max = null;
            int[] r_max = null;
            if (this.left_node != null) l_max = this.left_node.return_key_with_maximum_repetition();
            if (this.right_node != null) r_max = this.right_node.return_key_with_maximum_repetition();

            if (l_max != null && r_max != null) {
                if (arr[1] < r_max[1] && l_max[1] < r_max[1]) {
                    arr = r_max;
                } else if (arr[1] < l_max[1] && r_max[1] < l_max[1]) {
                    arr = l_max;
                }
            } else if (l_max == null && r_max != null) {
                if (arr[1] < r_max[1]) {
                    arr = r_max;
                }
            } else {
                if (arr[1] < l_max[1]) {
                    arr = l_max;
                }
            }

            return arr;
        }

    }
}



/*
  100 points

  Map is implemented using direct address table of size |U| and a summary array of size |U|^(0.5) [square root of |U|]

  Example:
  Given a universe of [0, 15], |U| = 16, with n = {(1,5),(2,38),(11,9),(13,100),(14,-1)}, the data structure
  The map is made of summary and value array.
  The value array will be as follows:
  -1, 5, 38, -1, -1, -1, -1, -1, -1, -1, -1, 9, -1, 100, 200, -1, -1
  The summary array will be as follows:
  1,0,1,1
  the first 1 tells you that there exists atleast one key in the range of (0-3)
  the second 0 tells you that there exists does not exist any key in the range of (4-7)
  and so on

  Use the summary array to efficiently implement minimum and maximum in O(|U|^(0.5)) time

 */
class map_augmented_direct_address_table {

    // Initialize the value array to -1
    // Initialize the summary array to 0

    // if value[i] == -1, key i does not exist in the map
    // if value[i] != -1, key i exist in the map

    private int universe_size;
    private int[] summary;
    private int[] value;


    public map_augmented_direct_address_table(int us) {
        universe_size = us;
        value = new int[us];
        // TODO: initialize the value array with all -1 (empty)
        for (int i = 0; i < us; i++)
            value[i] = -1;

        int summary_size = (int)Math.sqrt(universe_size);
        summary = new int[summary_size];
        // TODO: initialize the summary array with all 0 (empty)
        for (int i = 0; i < summary_size; i++)
            summary[i] = 0;
    }


    // [20 Points]
    // return -1 if the key already exists, and you have to perform a replace operation
    // return the key if the key is successfully inserted
    public int insert (int key, int value) {
        // TODO: Implement this
        return -1;
    }

    // [20 Points]
    // return value if the key already exists
    // return -1 if the key does not exist
    public int search (int key) {
        // TODO: Implement this
        return -1;
    }

    // [20 Points]
    // return the maximum key-value
    // return -1 if the hash table is empty
    public int maximum () {
        // TODO: Implement this
        return -1;
    }


    // [20 Points]
    // return the minimum key-value
    // return -1 if the hash table is empty
    public int minimum () {
        // TODO: Implement this
        return -1;
    }

    // [20 Points]
    // Return an array arr of size 2
    // arr[0] the key which has most number of occurence,
    // arr[1] the number of occurence of arr[0]
    public int[] return_key_with_maximum_repetition() {
        int[] arr = new int[2]; // combining both statements in one

        // You will have to traverse the data structure
        // TODO: Implement this
        return arr;
    }

}


public class AlgorithmsHW4 {
    public static void main (String[] args) {
        int key_count = 400000;
        int univers_size = 16777216;

        map_BST ht_set1 = new map_BST (-1, -1);

        map_augmented_direct_address_table map_adatm = new map_augmented_direct_address_table (16777216);

        /********************* CREATE THE KEYS ********************/
        // Create key_count keys (n), randomly
        int[] keys_set1 = new int[key_count];
        for (int k = 0; k < key_count; k++)
            keys_set1[k] = (int)(Math.random() * univers_size) % univers_size;


        /********************* INSERT ********************/
        // Insert the keys in the augmented direct address table
        // you are inserting key and the number of occurence of the key as the value
        long start_p1 = System.nanoTime();
        for (int k = 0; k < key_count; k++) {
            int value = map_adatm.search(keys_set1[k]);
            if (value == -1)
                map_adatm.insert(keys_set1[k], 1);
            else
                map_adatm.insert(keys_set1[k], value + 1);
        }
        long end_p1 = System.nanoTime();

        // TODO: add insert code for BST based data structure

        // Create key_count keys (n), randomly
        int[] keys_set2 = new int[key_count];
        for (int k = 0; k < key_count; k++)
            keys_set2[k] = (int)(Math.random() * univers_size) % univers_size;

        long start_p2 = System.nanoTime();
        for (int k = 0; k < key_count; k++) {
            int value = ht_set1.search(keys_set2[k]);
            if (value == -1)
                ht_set1.insert(keys_set2[k], 1);
            else
                ht_set1.insert(keys_set2[k], value + 1);
        }
        long end_p2 = System.nanoTime();

        System.out.println("------ BST ------");
        System.out.println("Insert: " + (end_p2 - start_p2));

        // TODO: add code to verify correctness of minimum, maximum functions
        Arrays.sort(keys_set2);
        int actual_max = keys_set2[keys_set2.length - 1];
        int actual_min = keys_set2[0];
        long start_max = System.nanoTime();
        int calc_max = ht_set1.maximum();
        long end_max = System.nanoTime();
        long start_min = System.nanoTime();
        int calc_min = ht_set1.minimum();
        long end_min = System.nanoTime();

        System.out.println("Max:    " + (end_max - start_max));

        if (actual_max != ht_set1.maximum()) {
            System.out.println("Max Failed! " + actual_max + " != " + ht_set1.maximum());
        }

        System.out.println("Min:    " + (end_min - start_min));
        if (actual_min != ht_set1.minimum()) {
            System.out.println("Min Failed! " + actual_min + " != " + ht_set1.minimum());
        }

        // TODO: Call the function return_key_with_maximum_repetition to return the key which repeats the most along with the number of repetitions
        // AND PRINT THE PAIR
        long start_repeat = System.nanoTime();
        int[] result = ht_set1.return_key_with_maximum_repetition();
        long end_repeat = System.nanoTime();

        System.out.println("Repeat: " + (end_repeat - start_repeat));
        System.out.println("   Result: (" + result[0] + ", " + result[1] + ")");
    }
}
