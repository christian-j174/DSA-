import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class MostFrequentFollowingWrapper {

	public static interface List<E> extends Iterable<E> {

		public void add(E obj);
		public void add(int index, E obj);
		public boolean remove(E obj);
		public boolean remove(int index);
		public int removeAll(E obj);
		public E get(int index);
		public E set(int index, E obj);
		public E first();
		public E last();
		public int firstIndex(E obj);
		public int lastIndex(E obj);
		public int size();
		public boolean isEmpty();
		public boolean contains(E obj);
		public void clear();
	}

	public static interface Map<K, V> {
		V get(K key);
		void put(K key, V value);
		V remove(K key);
		boolean containsKey(K key);
		List<K> getKeys();
		List<V> getValues();
		int size();
		boolean isEmpty();
		void clear();
		void print(PrintStream out); /* For debugging purposes */
	}

	public static interface HashFunction<K>{
		public int hashCode(K key);
	}

	public static class SimpleHashFunction<K> implements HashFunction<K> {

		@Override
		public int hashCode(K key) {
			String temp = key.toString();
			int result = 0;
			for (int i = 0; i < temp.length(); i++)
				result += temp.charAt(i);
			return result;
		}

	}

	public static class LinkedList<E> implements List<E> {

		private class Node {
			private E value;
			private Node next;

			public Node(E value, Node next) {
				this.value = value;
				this.next = next;
			}

			public Node(E value) {
				this(value, null); // Delegate to other constructor
			}

			public Node() {
				this(null, null); // Delegate to other constructor
			}

			public E getValue() {
				return value;
			}

			public void setValue(E value) {
				this.value = value;
			}

			public Node getNext() {
				return next;
			}

			public void setNext(Node next) {
				this.next = next;
			}

			public void clear() {
				value = null;
				next = null;
			}				
		} // End of Node class


		private class ListIterator implements Iterator<E> {

			private Node nextNode;

			public ListIterator() {
				nextNode = header.getNext();
			}

			@Override
			public boolean hasNext() {
				return nextNode != null;
			}

			@Override
			public E next() {
				if (hasNext()) {
					E val = nextNode.getValue();
					nextNode = nextNode.getNext();
					return val;
				}
				else
					throw new NoSuchElementException();				
			}

		} // End of ListIterator class


		// private fields
		private Node header;	
		private int currentSize;


		public LinkedList() {
			header = new Node();
			currentSize = 0;
		}

		@Override
		public Iterator<E> iterator() {
			return new ListIterator();
		}

		@Override
		public void add(E obj) {
			Node curNode, newNode;
			// Need to find the last node
			for (curNode = header; curNode.getNext() != null; curNode = curNode.getNext());
			// Now curNode is the last node
			// Create a new Node and make curNode point to it
			newNode = new Node(obj);
			curNode.setNext(newNode);
			currentSize++;
		}

		@Override
		public void add(int index, E obj) {
			Node curNode, newNode;

			// First confirm index is a valid position
			// We allow for index == size() and delegate to add(object).
			if (index < 0 || index > size())
				throw new IndexOutOfBoundsException();
			if (index == size())
				add(obj); // Use our "append" method
			else {
				// Get predecessor node (at position index - 1)
				curNode = get_node(index - 1);
				// The new node must be inserted between curNode and curNode's next
				// Note that if index = 0, curNode will be header node
				newNode = new Node(obj, curNode.getNext());
				curNode.setNext(newNode);
				currentSize++;
			}
		}

		@Override
		public boolean remove(E obj) {
			Node curNode = header;
			Node nextNode = curNode.getNext();

			// Traverse the list until we find the element or we reach the end
			while (nextNode != null && !nextNode.getValue().equals(obj)) {
				curNode = nextNode;
				nextNode = nextNode.getNext();
			}

			// Need to check if we found it
			if (nextNode != null) { // Found it!
				// If we have A -> B -> C and want to remove B, make A point to C 
				curNode.setNext(nextNode.getNext());
				nextNode.clear(); // free up resources
				currentSize--;
				return true;
			}
			else
				return false;
		}

		@Override
		public boolean remove(int index) {
			Node curNode, rmNode;
			// First confirm index is a valid position
			if (index < 0 || index >= size())
				throw new IndexOutOfBoundsException();
			curNode = get_node(index - 1);
			rmNode = curNode.getNext();
			// If we have A -> B -> C and want to remove B, make A point to C 
			curNode.setNext(rmNode.getNext());
			rmNode.clear();
			currentSize--;		

			return true;
		}

		/* Private method to return the node at position index */
		private Node get_node(int index) {
			Node curNode;

			/* First confirm index is a valid position
			   Allow -1 so that header node may be returned */
			if (index < -1 || index >= size())
				throw new IndexOutOfBoundsException();
			curNode = header;
			// Since first node is pos 0, let header be position -1
			for (int curPos = -1; curPos < index; curPos++)
				curNode = curNode.getNext();
			return curNode;
		}

		@Override
		public int removeAll(E obj) {
			int counter = 0;
			Node curNode = header;
			Node nextNode = curNode.getNext();

			/* We used the following in ArrayList, and it would also work here,
			 * but it would have running time of O(n^2).
			 * 
			 * while (remove(obj))
			 * 		counter++;
			 */

			// Traverse the entire list
			while (nextNode != null) { 
				if (nextNode.getValue().equals(obj)) { // Remove it!
					curNode.setNext(nextNode.getNext());
					nextNode.clear();
					currentSize--;
					counter++;
					/* Node that was pointed to by nextNode no longer exists
					   so reset it such that it's still the node after curNode */
					nextNode = curNode.getNext();
				}
				else {
					curNode = nextNode;
					nextNode = nextNode.getNext();
				}
			}
			return counter;
		}

		@Override
		public E get(int index) {
			// get_node allows for index to be -1, but we don't want get to allow that
			if (index < 0 || index >= size())
				throw new IndexOutOfBoundsException();
			return get_node(index).getValue();
		}

		@Override
		public E set(int index, E obj) {
			// get_node allows for index to be -1, but we don't want set to allow that
			if (index < 0 || index >= size())
				throw new IndexOutOfBoundsException();
			Node theNode = get_node(index);
			E theValue = theNode.getValue();
			theNode.setValue(obj);
			return theValue;
		}

		@Override
		public E first() {
			return get(0);
		}

		@Override
		public E last() {
			return get(size()-1);
		}

		@Override
		public int firstIndex(E obj) {
			Node curNode = header.getNext();
			int curPos = 0;
			// Traverse the list until we find the element or we reach the end
			while (curNode != null && !curNode.getValue().equals(obj)) {
				curPos++;
				curNode = curNode.getNext();
			}
			if (curNode != null)
				return curPos;
			else
				return -1;
		}

		@Override
		public int lastIndex(E obj) {
			int curPos = 0, lastPos = -1;
			// Traverse the list 
			for (Node curNode = header.getNext(); curNode != null; curNode = curNode.getNext()) {
				if (curNode.getValue().equals(obj))
					lastPos = curPos;
				curPos++;
			}
			return lastPos;
		}

		@Override
		public int size() {
			return currentSize;
		}

		@Override
		public boolean isEmpty() {
			return size() == 0;
		}

		@Override
		public boolean contains(E obj) {
			return firstIndex(obj) != -1;
		}

		@Override
		public void clear() {
			// Avoid throwing an exception if the list is already empty
			while (size() > 0)
				remove(0);
		}
	}

	public static class HashTableOA<K, V> implements Map<K, V> {

		private static class Bucket<K, V> {
			private K key;
			private V value;
			private boolean inUse;

			public Bucket(K key, V value, boolean inUse) {
				this.key = key;
				this.value = value;
				this.inUse = inUse;
			}
			public K getKey() {
				return key;
			}
			public V getValue() {
				return value;
			}
			public boolean isInUse() {
				return inUse;
			}
			public void release() {
				key = null;
				value = null;
				inUse = false;
			}
		}


		// private fields
		private int currentSize;
		private Bucket<K, V>[] buckets;
		private HashFunction<K> hashFunction;
		private static final double loadFactor = 0.75;
		private static final int DEFAULT_SIZE = 11;


		@SuppressWarnings("unchecked")
		public HashTableOA(int initialCapacity, HashFunction<K> hashFunction) {
			if (initialCapacity < 1)
				throw new IllegalArgumentException("Capacity must be at least 1");
			if (hashFunction == null)
				throw new IllegalArgumentException("Hash function cannot be null");

			currentSize = 0;
			this.hashFunction = hashFunction;
			buckets = new Bucket[initialCapacity];
			for (int i = 0; i < initialCapacity; i++)
				buckets[i] = new Bucket<K, V>(null, null, false);
		}
		
		public HashTableOA(HashFunction<K> hashFunction) { 
			this(DEFAULT_SIZE, hashFunction);
		}

		@Override
		public V get(K key) {
			if (key == null)
				throw new IllegalArgumentException("Parameter cannot be null.");

			/* First we determine the bucket corresponding to this key */
			int targetBucket = hashFunction.hashCode(key) % buckets.length;
			if (buckets[targetBucket].isInUse() && buckets[targetBucket].getKey().equals(key))
				return buckets[targetBucket].getValue();
			else
				return getLinearProbing(key, targetBucket);
		}

		private V getLinearProbing(K key, int start) {
			int index = getBucket(key, start);
			if (index != -1) // Found it!
				return buckets[index].getValue();
			else
				return null;
		}

		/**
		 * Helper method to find the bucket containing a specific key by probing.
		 * 
		 * @param key    The key to search for
		 * @param start  The index where the collision occurred
		 * @return       The index where the key was found (or -1 if not found)
		 */
		private int getBucket(K key, int start) {
			int n = buckets.length;
			/* Keep checking buckets until we find the key,
			 * or we wrap-around to the start position. */
			for (int i = (start + 1) % n; i != start; i = (i + 1) % n) {
				/* If we reach a bucket not in use, then our key will not be found,
				 * because no value has been added here through probing. */
				if (!buckets[i].isInUse())
					return -1;
				else if (buckets[i].getKey().equals(key))
					return i;
			}
			return -1; // Did not find it
		}

		@Override
		public void put(K key, V value) {
			if (key == null || value == null)
				throw new IllegalArgumentException("Parameter cannot be null.");

			//			if (size() == buckets.length)
			//				throw new IllegalStateException("Hash table is full.");

			/* Can't have two elements with same key,
			 * so remove existing element with the given key (if any) */
			remove(key);

			double currentLF = (double) this.size() /(double) buckets.length;
			if(currentLF > loadFactor)
				rehash();

			/* Determine the bucket corresponding to this key */
			int targetBucket = hashFunction.hashCode(key) % buckets.length;
			/* If no collision, store it there.  Otherwise, apply probing. */
			if (!buckets[targetBucket].isInUse())
				buckets[targetBucket] = new Bucket<K, V>(key, value, true);
			else // Collision
				putLinearProbing(targetBucket, key, value);
			currentSize++;
		}

		/**
		 * Perform linear probing to find an empty bucket where value can be added.
		 * It is assumed that there's room for the new value.
		 * 
		 * @param start  Index where initial collision occurred.
		 * @param key    Key of value to be added.
		 * @param value  Value to be added.
		 */
		private void putLinearProbing(int start, K key, V value) {
			int n = buckets.length;
			for (int i = (start + 1) % n; i != start; i = (i + 1) % n) {
				if (!buckets[i].isInUse()) {
					buckets[i] = new Bucket<K, V>(key, value, true);
					break;
				}
			}

		}

		@Override
		public V remove(K key) {
			if (key == null)
				throw new IllegalArgumentException("Parameter cannot be null.");

			/* First we determine the bucket corresponding to this key */
			int targetBucket = hashFunction.hashCode(key) % buckets.length;
			Bucket<K, V> b = buckets[targetBucket];
			if (b.isInUse() && b.getKey().equals(key)) {
				V v = b.getValue();
				b.release();
				currentSize--;
				return v;
			}
			else
				return removeLinearProbing(key, targetBucket);
		}

		private V removeLinearProbing(K key, int start) {
			int index = getBucket(key, start);
			if (index != -1) { // Found it!
				V v = buckets[index].getValue();
				buckets[index].release();
				currentSize--;
				return v;
			}
			else
				return null;
		}

		@Override
		public boolean containsKey(K key) {
			return get(key) != null;
		}

		@Override
		public List<K> getKeys() {
			List<K> result = new LinkedList<K>();
			for (int i = 0; i < buckets.length; i++)
				if (buckets[i].isInUse())
					result.add(0, buckets[i].getKey());
			return result;
		}

		@Override
		public List<V> getValues() {
			List<V> result = new LinkedList<V>();
			for (int i = 0; i < buckets.length; i++)
				if (buckets[i].isInUse())
					result.add(0, buckets[i].getValue());
			return result;
		}

		@Override
		public int size() {
			return currentSize;
		}

		@Override
		public boolean isEmpty() {
			return size() == 0;
		}

		@Override
		public void clear() {
			currentSize = 0;
			for (int i = 0; i < buckets.length; i++)
				buckets[i].release();
		}

		@Override
		public void print(PrintStream out) {
			/* For each bucket in use in the hash table, print the elements */
			for (int i = 0; i < buckets.length; i++) {
				Bucket<K, V> b = buckets[i];
				if (b.isInUse())
					out.printf("(%s, %s)\n", b.getKey(), b.getValue());
			}

		}

		@SuppressWarnings("unchecked")
		private void rehash() {
			int newCapacity = 2 * buckets.length; // Doubles the buckets size

			Bucket<K,V>[] newBuckets = new Bucket[newCapacity]; // New bucket with new size

			for(int i =0; i < newCapacity; i++) { // Initialized buckets to empty bucket
				newBuckets[i] = new Bucket<>(null, null, false);
			}

			//			int size = 0; // Size is made zero
			for(Bucket<K,V> b : buckets) {
				// Determine bucket for this key
				int targetBucket = hashFunction.hashCode(b.getKey()) % newBuckets.length;
				// Within that bucket there is a linked list, since we're using Separate Chaining
				newBuckets[targetBucket] = b;			
			}

			this.buckets = newBuckets;

		}

	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static int mostFrequent(int[] nums, int key) {

        Map<Integer, Integer> nTimes = new HashTableOA(new SimpleHashFunction());
        
		int frequentNumber = -1;
		int maxReps = 0;
        

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == key) {

                int nextNum = nums[i + 1];
                int count = nTimes.get(nextNum) == null ? 0 : nTimes.get(nextNum);
                nTimes.put(nextNum, count + 1);

                if (count + 1 > maxReps) {

                    maxReps = count + 1;
                    frequentNumber = nextNum;
                }
            }
        }

        return frequentNumber;
	}

	
}