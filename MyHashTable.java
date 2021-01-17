

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets
    private int numBuckets;
    // load factor needed to check for rehashing
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets;

    // constructor
    public MyHashTable(int initialCapacity) {


        // ADD YOUR CODE BELOW THIS

       if(initialCapacity < 0) {
   		throw new IllegalArgumentException("Invalid bucket size! It cannot be negative.");
   		}
   	this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
   	this.numBuckets = initialCapacity;
   	this.numEntries = 0;
   	for(int i=0; i<numBuckets; i++) {
   		LinkedList<HashPair<K,V>> bucket = new LinkedList<HashPair<K,V>>();
   		buckets.add(bucket);
   	}


        //ADD YOUR CODE ABOVE THIS
    }

    public int size() {
        return this.numEntries;
    }

    public boolean isEmpty() {
        return this.numEntries == 0;
    }

    public int numBuckets() {
        return this.numBuckets;
    }

    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }

    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE


    	if(key == null || value == null) {
    		throw new NullPointerException("Invalid Key or Value!");
    	}
    	int hash_id = hashFunction(key);
    	for(HashPair<K,V> e: buckets.get(hash_id)) {
    		if(e.getKey().equals(key)) {
    			e.setValue(value);
    			return e.getValue();
    		}
    	}

    	HashPair<K,V> new_pair = new HashPair<K,V>(key,value);
    	buckets.get(hash_id).add(new_pair);
    	numEntries++;
    	if( (((double) numEntries)/((double) numBuckets)) > MAX_LOAD_FACTOR ) {
    		this.rehash();
    	}
    	return null;

        //  ADD YOUR CODE ABOVE HERE
    }

    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */

    public V get(K key) {
        //ADD YOUR CODE BELOW HERE

    	   if(key == null) {
           	throw new NullPointerException("Invalid Key!");
           }

       	int hash_id = hashFunction(key);
       	for(HashPair<K,V> e: buckets.get(hash_id)) {
       		if(e.getKey().equals(key)) {
       			V element = e.getValue();
       			return element;
       		}
       	}
           return null;
        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1)
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE

    	if(key == null) {
    		throw new NullPointerException("Invalid Key!");
    	}
    	int hash_id = hashFunction(key);
    	for(HashPair<K,V> e: buckets.get(hash_id)) {
    		if(e.getKey().equals(key)) {
    			V value = e.getValue();
    			buckets.get(hash_id).remove(e);
    			numEntries--;
    			return value;
    		}
    	}
    	return null;
        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
        MyHashTable<K,V> new_table = new MyHashTable<K,V>(this.numBuckets*2);
        int new_numBuckets = 2*numBuckets;
        int old_numBuckets = numBuckets;
        numBuckets = new_numBuckets;
        	for(int i=0; i<old_numBuckets; i++) {
        		for(HashPair<K,V> e: this.getBuckets().get(i)) {
        			new_table.put(e.getKey(), e.getValue());
        		}
        }
        this.buckets = new_table.getBuckets();
        //ADD YOUR CODE ABOVE HERE
    }


    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */

    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE

    	ArrayList<K> keys = new ArrayList<K>();
    	for(LinkedList<HashPair<K,V>> bucket: buckets) {
    		for(HashPair<K,V> e: bucket) {
    			keys.add(e.getKey());
    		}
    	}
    	return keys;

        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE

    	ArrayList<V> values = new ArrayList<V>();
    	for(LinkedList<HashPair<K,V>> bucket: buckets) {
    		for(HashPair<K,V> e: bucket) {
    			values.add(e.getValue());
    		}
    	}
    	return values;
        //ADD CODE ABOVE HERE
    }

	/**
	 * This method takes as input an object of type MyHashTable with values that
	 * are Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n^2), where n is the number
	 * of pairs in the map.
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}

        	sortedResults.add(i+1, toAdd);

        }
        return sortedResults;
    }

	/**
	 * This method takes as input an object of type MyHashTable with values that
	 * are Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n*log(n)), where n is the number
	 * of pairs in the map.
	 */

    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE

    	ArrayList<K> endResults = new ArrayList<>();

    	for(HashPair<K,V> entry: results){
			K toAdd = entry.getKey();


    		endResults.add(toAdd);

    	}
    	buildHeap(results,endResults);

    	return endResults;

        //ADD CODE ABOVE HERE
    }


    	private static <K, V extends Comparable<V>> void buildHeap(MyHashTable<K, V> results,ArrayList<K> keys){
    		int size = keys.size();

    		int startIdx = size/2-1;
    		for(int i = startIdx; i>=0;i--){
    			heapify(results,keys,size,i);
    		}

    		for(int i = size-1;i>=0;i--){


    			K tmpK = keys.get(0);
           	   keys.set(0, keys.get(i));
           	   keys.set(i, tmpK);

   			heapify(results,keys,i,0);


    		}

    		}

        private  static <K, V extends Comparable<V>> void heapify(MyHashTable<K, V> results,ArrayList<K> keys, int n, int i)
        {
            int largest = i; // Initialize largest as root
            int l = 2 * i+1; // left = 2*i + 1
            int r = 2 * i +2 ; // right = 2*i + 2


            if (r < n && (results.get(keys.get(r)).compareTo(results.get(keys.get(largest)))<=0)) {
                largest = r;
        }

            if (l < n && (results.get(keys.get(l)).compareTo(results.get(keys.get(largest)))<=0)){
                largest = l;
            }
            // If largest is not root
            if (largest != i) {
            	K tmpK = keys.get(i);
          	   keys.set(i, keys.get(largest));
          	   keys.set(largest, tmpK);

                // Recursively heapify the affected sub-tree
                heapify(results,keys, n, largest);
            }
        }


    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }

    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
        private LinkedList<HashPair<K,V>> entries;

        //ADD YOUR CODE ABOVE HERE

    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	  entries = new LinkedList<HashPair<K,V>>();

              for(LinkedList<HashPair<K,V>> bucket: buckets) {
              	for(HashPair<K,V> e: bucket) {
              		entries.add(e);
              	}
              }
            //ADD YOUR CODE ABOVE HERE
        }
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE

        	   if(!entries.isEmpty()) {
               	return true;
               }
               return false;
            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	return entries.poll();

            //ADD YOUR CODE ABOVE HERE
        }


    }
}
