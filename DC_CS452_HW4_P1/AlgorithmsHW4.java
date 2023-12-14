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
 * The goal of this assignment is to implement a dictionary data structure which must support search, insert and delete operation
 * You will implement ditionary using a direct address table and a hash table
 * 
 * The universe size is 16777216 (2^24). The keys are in the range [0, 16777215]
 * 
 */


/*
  [100 points]

  A dynamic set implemented using Hash table where collisions are dealt with chaining
  Space complexity of data structure is O(n)
  Running time complexity of search, insert and delete is O(1) [average case] and O(n) [worst case]
 */
class dictionary_hash_table
{
  /* The keys are going to be saved in hash_table, which is an array of linked list */
  private LinkedList<Integer>[] hash_table;

  /* Number of keys (n) which is passed as parameter to the constructor */
  private int number_of_keys;
  
  /* Passed as a constructor parameter, 
  load_factor is used to compute the bucket count in the hash table using the formula load_factor= n/m */
  private double load_factor;

  /* Bucket count (m) is the size of the hash_table array, 
  bucket_count is automatically computed by the formula load_factor = n/m */
  private int bucket_count;

  // The hash function will return an index between 0 and bucket_count-1
  public int hash_function(int key)
  {
    return key % bucket_count;
  }

  // initializations
  // [15 Points]
  public dictionary_hash_table(int key_count, double lf)
  {
    // TODO: Implement this
    
    // initialize number_of_keys
    
    // compute bucket_count:
    // Total number of keys n = key_count
    // load_factor in a hash table is defined as n/m in the hash table
    // load_factor = n/m
    // m(bucket_count) = n/load_factor
    this.bucket_count = (int) (key_count / lf);
    this.number_of_keys = key_count;
    this.load_factor = lf;
    // initialize the hash_table (an array of linked_lists of size m)
    this.hash_table = new LinkedList[this.bucket_count];
    for(int i = 0; i < this.hash_table.length; i++) {
      this.hash_table[i] = new LinkedList<Integer>();
    }
  }
  

  // [15 Points]
  // return -1 if the key already exists
  // return if the key already exists: do not add the key and return -1 
  // return the key-value if the key is successfully inserted
  public int insert (int key)
  {    
    if(this.hash_table[this.hash_function(key)].contains(key)){
      return -1;
    }
    else{
      this.hash_table[this.hash_function(key)].addFirst(key);
      return key;
    }
  }

  // [15 Points]
  // return true if the key already exists
  // return false if the key does not exist
  public boolean search (int key)
  {
    int list_length = this.hash_table[this.hash_function(key)].size();
    for(int i = 0; i < list_length; i++){
      if(this.hash_table[this.hash_function(key)].get(i)==key){
        return true;
      }
    }
    return false;
  }

  // [15 Points]
  // return -1 id the key is out of universe range
  // return -1 if the key does not exist
  // return the key-value if the key is successfully deleted
  public int delete (int key)
  {
    if(key > 16777215 || key < 0){
      return -1;
    }
    else{
      if(this.hash_table[this.hash_function(key)].contains(key)){
        this.hash_table[this.hash_function(key)].remove(key);
        return key;
      }
      else{
        return -1;
      }
    }
  }

  // [15 Points]
  // return the maximum key-value
  // return -1 if the hash able is empty
  public int maximum ()
  {
    int max = 0;
    for(int i = 0; i < this.hash_table.length; i++){
      for(int j = 0; j < this.hash_table[i].size(); j++){
        if(this.hash_table[i].get(j) > max){
          max = this.hash_table[i].get(j);
        }
      }
    }
    return max;
  }

  // [Points 15]
  // Clear the data structure
  // delete all elements
  public void clear()
  {
    this.hash_table = new LinkedList[this.bucket_count];
  }

  // Points 10
  // return the length of the longest linked list (among the m linked lists)
  public int size_of_longest_linked_list()
  {
    int max = 0;
    for(int i = 0; i < this.hash_table.length; i++){
      if(this.hash_table[i].size() > max){
        max = this.hash_table[i].size();
      }
    }
    return max;
  }
}



/*
  100 points
  A dynamic set implemented using direct address table
  Space complexity of data structure is O(|U|)
  Running time complexity of search, insert and delete is O(1)
  Running time complexity of traversal, successor and maximum is O(|U|)
 */
class dictionary_direct_address_table
{

  // Initialize the array to 0
  // if DAT[i] == 1, key i exists in the dictionary
  // if DAT[i] == 0, key i does not exist in the dictionary
  
  private int universe_size;
  private int[] DAT;


  // initializations
  // [15 Points]
  public dictionary_direct_address_table(int us)
  {
    // create the DAT array of size universe_size
    // inititlize the DAT array with all 0s (empty)
    // TODO: Implement this
    this.universe_size = us;
    this.DAT = new int[us];
  }

  //Points 15
  // return -1 if the key already exists
  // return -1 if the key is out of the universe
  // return the key-value if the key is successfully inserted
  public int insert (int key)
  {
    if(key > this.universe_size-1 || key < 0){
      return -1;
    }
    else if(this.DAT[key] == 1){
      return -1;
    }
    else{
      this.DAT[key] = 1;
      return key;
    }
  }

  //Points 15
  // return true if the key already exists
  // return false if the key does not exist
  public boolean search (int key)
  {
    if(this.DAT[key] == 1){
      return true;
    }
    else{
      return false;
    }
  }

  //Points 15
  // return -1 if the key does not exist
  // return the key-value if the key is successfully deleted
  public int delete (int key)
  {
    if(this.DAT[key] == 0 || key > this.universe_size || key < this.universe_size){
      return -1;
    }
    else{
      this.DAT[key] = 0;
      return key;
    }
  }

  //Points 15
  // return the maximum key-value
  // return -1 if the hash able is empty
  public int maximum ()
  {
    for(int i = this.DAT.length-1; i >= 0; i--){
      if(this.DAT[i] == 1){
        return i;
      }
    }
    return -1;
  }

  //Points 15
  // Clear the data structure (delete all keys)
  public void clear()
  {
    this.DAT = new int[this.universe_size];
    // TODO: Implement this
  }

  // Points 10
  // return the minimum key-value
  // return -1 if the hash able is empty
  public int minimum ()
  {
    for(int i = 0; i < this.DAT.length; i++){
      if(this.DAT[i] == 1){
        return i;
      }
    }
    return -1;
  }

}


public class AlgorithmsHW4
{

  // [30 points]
  public static int deduplicate (int[] keys_to_deduplicate)
  {
    
    dictionary_direct_address_table table = new dictionary_direct_address_table(keys_to_deduplicate.length);
    int duplicates = 0;
    for(int i = 0; i < keys_to_deduplicate.length; i++){
      if(table.search(keys_to_deduplicate[i]) == true){
        duplicates++;
      }
      else{
        table.insert(keys_to_deduplicate[i]);
      }
    }
    
    return duplicates;
  }

  public static void main (String[] args)
  {
    int key_count = 400000;
    int univers_size = 16777216; 
    dictionary_hash_table ht_set1 = new dictionary_hash_table (key_count, 0.5);
    dictionary_direct_address_table dat_set1 = new dictionary_direct_address_table (16777216);

    /********************* CREATE THE KEYS ********************/
    // Create key_count keys (n), randomly
    int[] keys_set1 = new int[key_count];
    for (int k=0; k<key_count; k++)
      keys_set1[k] = (int)(Math.random()*univers_size) % univers_size;
    

    /********************* INSERT ********************/
    // Insert the keys in the hash based dictionary
    long start_p1 = System.nanoTime();
    int insert_counter_ht = 0;
    for (int k=0; k<key_count; k++)
    {
      if (ht_set1.insert(keys_set1[k]) != -1)
        insert_counter_ht++;
    }
    long end_p1 = System.nanoTime();

    // Insert the keys in the direct address table based dictionary
    long start_p2 = System.nanoTime();
    int insert_counter_dat = 0;
    for (int k=0; k<key_count; k++)
    {
      if (dat_set1.insert(keys_set1[k]) != -1)
        insert_counter_dat++;
    }
    long end_p2 = System.nanoTime();
    
    // checking that the total number of keys in both the data structures is same
    if (insert_counter_ht != insert_counter_dat)
      System.out.println("Error!!!!\t" + insert_counter_ht + "\t" + insert_counter_dat);

    System.out.println("[Insert]");
    System.out.println("Total number of keys inserted\t" + insert_counter_ht + "\nInsertion time for hash table\t" + (end_p1 - start_p1) + "\nInsertion time for DAT\t" + (end_p2 - start_p2) );
    System.out.println("Maximum value using hash table\t"+ht_set1.maximum());
    System.out.println("Size of the longest linked list in the hash table\t"+ht_set1.size_of_longest_linked_list());

    System.out.println("Maximum value using DAT\t"+dat_set1.maximum());
    System.out.println("Minimum value using DAT\t"+dat_set1.minimum());

    // [20 Points]
    // // TODO: Implement this
    // add code to delete the 10 largest value in both the DAT and hash table
    // You must only use the functions implemented

    System.out.println("New maximum for DAT\t"+dat_set1.maximum());
    System.out.println("New maximum for HT\t"+ht_set1.maximum());

    /********************* SEARCH ********************/

    long start_p4 = System.nanoTime();
    int matches_ht=0;
    for (int k=0; k<key_count; k++)
      if (ht_set1.search(keys_set1[k] + 1) == true)
        matches_ht++;
    long end_p4 = System.nanoTime();
    
    long start_p5 = System.nanoTime();
    int matches_dat=0;
    for (int k=0; k<key_count; k++)
      if (dat_set1.search(keys_set1[k] + 1) == true)
        matches_dat++;
    long end_p5 = System.nanoTime();
    
    if (matches_ht != matches_dat)
      System.out.println("Error\t" + matches_ht + " " + matches_dat);
    
    System.out.println();
    System.out.println("[Search]");
    System.out.println("Time for searches in HT\t" + (end_p4 - start_p4) + "\nTime for searches in DAT\t" + (end_p5 - start_p5));

    keys_set1 = null;
    dat_set1.clear();
    ht_set1.clear();


    /********************* DEDUPLICATE ********************/
    // Use the hash table to count the number of duplicates in the keys_to_deduplicate array

    int[] keys_to_deduplicate = new int[1600000];
    for (int k=0; k<800000; k++)
      keys_to_deduplicate[k] = (int)(Math.random()*1048576) % 1048576;
    for (int k=0; k<800000; k++)
      keys_to_deduplicate[800000 + k] = keys_to_deduplicate[k];
    
    System.out.println();
    System.out.println("[DEDUPLICATE]");
    System.out.println("Duplicate key count " + deduplicate(keys_to_deduplicate));
  }
}