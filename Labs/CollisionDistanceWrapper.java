import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CollisionDistanceWrapper {
	
	public static interface Map<K, V> {
		public V get(K key);
		public void put(K key, V value);
		public V remove(K key);
		public boolean containsKey(K key);
		public List<K> getKeys();
		public List<V> getValues();
		public int size();
		public boolean isEmpty();
		public void clear();
		public void print(PrintStream out); /* For debugging purposes */
		public int collisionDistance(K elm);
	}
	
	@FunctionalInterface
	public static interface HashFunction<K> {
		public int hashCode(K key);
	}

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

		@SuppressWarnings("hiding")
		private class Bucket<K, V>{
			private K key;
			private V value;
			private boolean inUse;

			public Bucket(K key, V value, boolean inUse) {
				this.key = key;
				this.value = value;
				this.inUse = inUse;
			}

			public Bucket() {
				this(null, null, false);
			}

			public K getKey() {
				return key;
			}

			public void setKey(K key) {
				this.key = key;
			}

			public V getValue() {
				return value;
			}

			public void setValue(V value) {
				this.value = value;
			}

			public boolean isInUse() {
				return inUse;
			}

			public void setInUse(boolean isInUse) {
				this.inUse = isInUse;
			}

			public void release() {
				this.key = null;
				this.value = null;
				this.inUse = false;

			}
		}

		private Bucket<K, V>[] buckets;
		private int currentSize;
		private HashFunction<K> hashFunction;
		private static final int DEFAULT_SIZE = 11; // Java uses a default size of 11
		private static final double loadFactor = 1.0;

		@SuppressWarnings("unchecked")
		public HashTableOA(int initialCapacity) {
			if(initialCapacity < 1)
				throw new IllegalArgumentException("Capacity must be at least 1");

			this.currentSize = 0;
			this.buckets = new Bucket[initialCapacity];
			for(int i = 0; i < buckets.length; i++) buckets[i] = new Bucket<>();
			this.hashFunction = (key) -> (Integer) key % buckets.length;

		}

		public HashTableOA() {
			this(DEFAULT_SIZE);
		}
		
		@Override
		public V get(K key) {
			if(key == null)
				throw new IllegalArgumentException("Key cannot be null");

			/**
			 * First, find the bucket this key is supposed to be in 
			 * if no collisions happened by using the hash function
			 */
			int targetBucket = hashFunction.hashCode(key) % buckets.length;

			/**
			 * If the target bucket has an entry and it is the one we are looking for
			 * return the value associated to that entrie's key
			 */
			if(buckets[targetBucket].isInUse() && buckets[targetBucket].getKey().equals(key))
				return buckets[targetBucket].getValue();
			else 
				/**
				 * If it is empty or it has a different entry than the one we are looking for, 
				 * we need to check the other buckets using linear probing.
				 */
				return getLinearProbing(key, targetBucket);
		}

		private V getLinearProbing(K key, int start) {
			int index = getBucket(key, start); // Try to find correct bucket
			if(index != -1) // Found it!
				return buckets[index].getValue();
			return null;
		}

		/**
		 * Helper method to find the bucket containing a specific key by probing.
		 * 
		 * @param key 	The key to search for
		 * @param start	The index where the collision happened
		 * @return		The index where the key was found (or -1 if not found)
		 */
		private int getBucket(K key, int start) {
			int n = buckets.length;
			/**
			 * Keep checking buckets until we find the key,
			 * or we wrap-around to the start position. 
			 */
			for (int i = (start + 1) % n; i != start; i = (i + 1) % n) {
				Bucket<K, V> bucket = buckets[i];
				/**
				 * If we reach a bucket not in use, then our key will not be found,
				 * because no value has been added here through probing. 
				 */
				if(!bucket.isInUse())
					return -1;
				else if(bucket.getKey().equals(key))
					return i; // Found it!
			}
			return -1; // Did not find it even after checking all of the remaining buckets
		}

		@Override
		public void put(K key, V value) {
			if(key == null || value == null)
				throw new IllegalArgumentException("Parameters cannot be null");

			/**
			 * Can't have 2 entries with the same key, so remove any entries with the
			 * same key passed as parameter.
			 */
			remove(key);

			double curLoadFactor = (double) size() / (double) buckets.length;
			
			if(curLoadFactor > loadFactor) 
				rehash();

			/**
			 * Find target bucket associated to given key, and insert it
			 */
			int targetBucket = hashFunction.hashCode(key) % buckets.length;
			if(!buckets[targetBucket].isInUse()) 
				buckets[targetBucket] = new Bucket<>(key, value, true);
			else 
				putLinearProbing(key, value, targetBucket);
			currentSize++;
		}

		private void putLinearProbing(K key, V value, int start) {
			int n = buckets.length;
			for (int i = (start + 1) % n; i != start; i = (i + 1) % n) {
				Bucket<K, V> bucket = buckets[i];
				if(!bucket.isInUse()) {
					buckets[i] = new Bucket<>(key, value, true);
					return;
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		private void rehash() {
			Bucket<K, V>[] newBuckets = new Bucket[buckets.length * 2];
			for (int i = 0; i < newBuckets.length; i++) {
				newBuckets[i] = new Bucket<>(null, null, false);
			}
			
			for (Bucket<K, V> bucket : newBuckets) {
				if(bucket.isInUse()) {
					int targetBucket = hashFunction.hashCode(bucket.getKey()) % newBuckets.length;
					newBuckets[targetBucket] = new Bucket<>(bucket.getKey(), bucket.getValue(), true);
				}		
			}
			
			buckets = newBuckets;
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
			List<K> result = new LinkedList<>();
			for (Bucket<K, V> bucket : buckets) {
				if(bucket.isInUse())
					result.add(0, bucket.getKey());
			}
			return result;
		}

		@Override
		public List<V> getValues() {
			List<V> result = new LinkedList<>();
			for (Bucket<K, V> bucket : buckets) {
				if(bucket.isInUse())
					result.add(0, bucket.getValue());
			}
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
			for (int i = 0; i < buckets.length; i++) 
				buckets[i].release();

			currentSize = 0;
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
		
		/**
		 * Consider the hash table implemented with open addressing.
		 *  
		 * Remember that a collision will cause an entry with a given key to be stored in a 
		 * different bucket from the one that the hash function indicates. 
		 * 
		 * The bucket that the hash function indicates is called the “proper bucket”. 
		 * Write a member method collisionDistance() which returns the number of positions 
		 * away from its proper bucket for a given key. 
		 * 
		 * The function returns -1 if key is not in the hash table, 
		 * or 0 if the element key is in the bucket that the hash function specifies.
		 * 
		 * @param key	Given Key
		 * @return		Number of positions away from its proper bucket
		 */
		@Override
		@SuppressWarnings({"unchecked", "rawtype"})
		public int collisionDistance(K key) {
		
			int initialIndex = hashFunction.hashCode(key) % buckets.length;
		
			if (buckets[initialIndex].isInUse() && buckets[initialIndex].getKey().equals(key)) {
				return 0;  
			}
		
			int foundIndex = getBucket(key, initialIndex);
		
			if(foundIndex == -1) {
				return -1;
			} else {
				if(foundIndex >= initialIndex) {
					return foundIndex - initialIndex;
				} else {
					return (buckets.length - initialIndex) + foundIndex;
				}
			}
		}
		
		
		
		
	}
}