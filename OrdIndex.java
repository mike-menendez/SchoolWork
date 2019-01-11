/**
 * File from database course.
 * Project was to complete the Relational Database implementation.
 * Tasked with creating the Ordered Index file.
 * Starting point was the name of the class and a couple function signatures.
 * Required to implement a binary search on the ordered index.
 *
 * Restraints: 
 *  - not allowed to use imported data structures.
 *  
 * Design:
 *  - Inline binary search.
 *  - Was given the key and block number. The database checks the ordered index to get the block number to lookup the tuple.
 *  - Used non recursive binary search to minimize lookup times and memory overhead.
 *  - Deletion times were not important which lead to a linear implementation to minimize LOC.
 *  
 * Issues:
 *  - Must create a custom object to hold the block and key because it is possible to have a collision on keys that 
 *      are stored in the same block. Rather then keeping an array of counters, it is more organized to have each object
 *      keep count independently. 
 *  - If the returned key was one of multiples (same key from hash but in different blocks) we would need to search
 *      adjacent entries to return all blocks that are appropriate. Thus a linear search out until the key mismatch.
 **/


package disk_store;

import java.util.*;

/**
 * An ordered index.  Duplicate search key values are allowed,
 * but not duplicate index table entries.  In DB terminology, a
 * search key is not a superkey.
 * 
 * A limitation of this class is that only single integer search
 * keys are supported.
 *
 */

public class OrdIndex implements DBIndex {

 ArrayList < IndexObj > list;

 public OrdIndex() {
  list = new ArrayList < > ();
 }

 @Override
 public List < Integer > lookup(int key) {
  ArrayList < Integer > temp = new ArrayList < > ();

  //gets index of a occurrence of key. (not necessarily middle entry due to being variable number of occurrences)

  int i = initIndex(key);

  if (i == -1) {
   //key wasn't found, return empty list of blocknumbers
   //heapDB doesn't use null vals and uses -1
   return temp;
  }

  //iterates out on ordered list of objects, checking if key match to add block iff block isn't already added
  temp.add(list.get(i).getBlocknum());
  for (int j = 1; i + j < list.size(); j++) {
   if (list.get(i + j).getKey() == key) {
    if (temp.contains(list.get(i + j).getBlocknum()) == false) {
     temp.add(list.get(i + j).getBlocknum());
    }
   } else {
    break;
   }
  }
  //reuse i value from init.
  for (; i >= 0; i--) {
   if (list.get(i).getKey() == key) {
    if (temp.contains(list.get(i).getBlocknum()) == false) {
     temp.add(list.get(i).getBlocknum());
    }
   } else {
    break;
   }
  }
  return temp;
 }

 //binary search function, returns index to begin iterative search from initial found obj (in case key appears >1)
 //allows to check n + j and n - k values for key matches.

 private int initIndex(int key) {
  int low = 0, high = list.size() - 1;
  boolean found = false;
  //list is sorted on insert, don't need to sort here
  //Collections.sort(list);
  while (low <= high && found == false) {
   int m = low + (high - low) / 2;

   if (list.get(m).getKey() == key) {
    return m;
   }
   if (list.get(m).getKey() < key) {
    low = m + 1;
   } else {
    high = m - 1;
   }
  }
  return -1;
 }

 @Override
 public void insert(int key, int blockNum) {

  //Inserts custom IndexObj with passed key and block numbers, if key and block exists in list, does nothing

  IndexObj temp = new IndexObj(key, blockNum);
  if (list.size() > 0 && list.contains(temp)) {
   list.get(list.indexOf(temp)).incCount();
  } else {
   list.add(temp);
   Collections.sort(list);
  }
 }

 @Override
 public void delete(int key, int blockNum) {
  //delete isn't binary to minimize code
  //only need to be quick on search since entries will be rarely dropped
  //to speed this up, we would overload the search if for some reason using binary opposed to a hash
  //in real world, would be specified in db engine's conf and would check flag bit.

  IndexObj temp = new IndexObj(key, blockNum);
  if (list.contains(temp)) {
   if (list.get(list.indexOf(temp)).getCount() > 1) {
    list.get(list.indexOf(temp)).decCount();
   } else {
    list.remove(temp);
   }
  }
 }

 public int size() {
  return list.size();
 }

 @Override
 public String toString() {
  StringBuilder temp = new StringBuilder();

  for (int i = 0; i < list.size(); i++) {
   temp.append(" " + list.get(i).getKey() + " " + list.get(i).getBlocknum());
  }
  return temp.toString();
 }

 public static class IndexObj implements Comparable < IndexObj > {

  public static List < Integer > search(ArrayList < IndexObj > list, int key) {
   ArrayList < Integer > temp = new ArrayList < > ();
   int index = Collections.binarySearch(list, new IndexObj(key, 0));

   temp.add(list.get(index).getBlocknum());

   for (int i = 1;; i++) {
    if (index + i < list.size() && list.get(index + i).getKey() == key) {
     temp.add(list.get(index + i).getBlocknum());
    } else {
     break;
    }
   }
   for (int i = 1;; i++) {
    if (index - i > 0 && list.get(index - i).getKey() == key) {
     temp.add(list.get(index - i).getBlocknum());
    } else {
     break;
    }
   }
   return temp;
  }
  private int key;
  private int blocknum;
  private int count;

  public IndexObj(int k, int b) {
   this.key = k;
   this.blocknum = b;
   this.count = 1;
  }

  public int getKey() {
   return this.key;
  }

  public int getBlocknum() {
   return this.blocknum;
  }

  public int getCount() {
   return this.count;
  }

  public void incCount() {
   count++;
  }

  public void decCount() {
   count--;
  }

  @Override
  public boolean equals(Object o) {

   if (o.getClass() == this.getClass()) {
    IndexObj temp = (IndexObj) o;

    if (temp.getKey() == this.getKey() && temp.getBlocknum() == this.getBlocknum()) {
     return true;
    }
   }
   return false;
  }
  @Override
  public int compareTo(IndexObj o) {
   if (o.getClass() == this.getClass()) {
    IndexObj temp = (IndexObj) o;

    if (temp.getKey() == this.getKey()) {
     return 0;
    } else if (temp.getKey() > this.getKey()) {
     return -1;
    } else {
     return 1;
    }
   }
   return -1;
  }
 }
}
