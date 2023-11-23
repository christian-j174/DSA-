package Heaps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CPUWrapper {
	
	public static interface Heap<K, V> {
		public void add(K key, V value);
		public Entry<K, V> removeMin();
		public Entry<K, V> getMin();
		public void clear();
		public int size();
		public boolean isEmpty();
		public void print(int minWidth);
	}
	
	public static interface Entry<K, V> {
		public K getKey();
		public V getValue();
	}
	
	public static class HeapEntry<K, V> implements Entry<K, V> {
		private K key;
		private V value;

		public HeapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return "(" + key + "," + value + ")";
		}

	}
	
	public static class EmptyHeapException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;
		
		public EmptyHeapException(String s) {
			super(s);
		}
		
		public EmptyHeapException() {
			this("");
		}

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
	
	
	public static class ArrayList<E> implements List<E> {

		// private fields
		private E elements[];
		private int currentSize;
		private static final int DEFAULT_SIZE = 15;

		
		private class ListIterator implements Iterator<E> {
			private int currentPosition;
			
			public ListIterator() {
				this.currentPosition = 0;
			}

			@Override
			public boolean hasNext() {
				return this.currentPosition < size();
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					return (E) elements[this.currentPosition++];
				}
				else
					throw new NoSuchElementException();
			}
		}
		
		
		@SuppressWarnings("unchecked")
		public ArrayList(int initialCapacity) {
			if (initialCapacity < 1)
				throw new IllegalArgumentException("Capacity must be at least 1.");
			this.currentSize = 0;
			this.elements = (E[]) new Object[initialCapacity];
		}
		
		public ArrayList() {
			this(DEFAULT_SIZE);
		}
		
		@Override
		public void add(E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			else {
				if (this.size() == this.elements.length)
					reAllocate();
				this.elements[this.currentSize++] = obj;
			}		
		}
		
		@SuppressWarnings("unchecked")
		private void reAllocate() {
			// create a new array with twice the size
			E newElements[] = (E[]) new Object[2*this.elements.length];
			for (int i = 0; i < this.size(); i++)
				newElements[i] = this.elements[i];
			this.elements = newElements;
		}

		@Override
		public void add(int index, E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			if (index == this.currentSize)
				this.add(obj); // Use other method to "append"
			else {
				if (index >= 0 && index < this.currentSize) {
					if (this.currentSize == this.elements.length)
						reAllocate();
					// move everybody one spot to the back
					for (int i = this.currentSize; i > index; i--)
						this.elements[i] = this.elements[i - 1];
					// add element at position index
					this.elements[index] = obj;
					this.currentSize++;
				}
				else
					throw new ArrayIndexOutOfBoundsException();
			}
		}

		@Override
		public boolean remove(E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			// first find obj in the array
			int position = this.firstIndex(obj);
			if (position >= 0) // found it
				return this.remove(position);
			else
				return false;
		}

		@Override
		public boolean remove(int index) {
			if (index >= 0 && index < this.currentSize) {
				// move everybody one spot to the front
				for (int i = index; i < this.currentSize - 1; i++)
					this.elements[i] = this.elements[i + 1];
				this.elements[--this.currentSize] = null;
				return true;
			}
			else
				return false;
		}

		@Override
		public int removeAll(E obj) {
			int counter = 0;
			while (this.remove(obj))
				counter++;
			return counter;
		}

		@Override
		public E get(int index) {
			if (index >= 0 && index < this.size())
				return this.elements[index];
			else
				throw new ArrayIndexOutOfBoundsException();
		}

		@Override
		public E set(int index, E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			if (index >= 0 && index < this.size()) {
				E temp = this.elements[index];
				this.elements[index] = obj;
				return temp;
			}
			else
				throw new ArrayIndexOutOfBoundsException();
		}

		@Override
		public E first() {
			if (this.isEmpty())
				return null;
			else
				return this.elements[0];
		}

		@Override
		public E last() {
			if (this.isEmpty())
				return null;
			else
				return this.elements[this.currentSize - 1];
		}

		@Override
		public int firstIndex(E obj) {
			for (int i = 0; i < this.size(); i++)
				if (this.elements[i].equals(obj))
					return i;
			return -1;
		}

		@Override
		public int lastIndex(E obj) {
			for (int i = this.size() - 1; i >= 0; i--)
				if (this.elements[i].equals(obj))
					return i;
			return -1;
		}

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public boolean contains(E obj) {
			return this.firstIndex(obj) >= 0;
		}

		@Override
		public void clear() {
			for (int i = 0; i < this.currentSize; i++)
				this.elements[i] = null;
			this.currentSize = 0;
		}

		@Override
		public Iterator<E> iterator() {
			return new ListIterator();
		}

	}

	

	public static class ListHeap<K, V> implements Heap<K, V> {
		private List<Entry<K, V>> elements;
		private Comparator<K> comparator;

		/**
		 * Constructor to declare an empty Heap
		 * 
		 * @param initialCapacity
		 * @param elements
		 */
		public ListHeap(Comparator<K> comparator) {
			this.elements = new ArrayList<>();
			this.comparator = comparator;
		}
		
		/**
		 * Constructor to declare a heap with n elements using bottom-up heap construction
		 * @param randomElements
		 */
		@SuppressWarnings("unchecked")
		public ListHeap(List<Entry<K, V>> randomElements) {
			this.comparator = (k1, k2) -> ((Comparable<K>) k1).compareTo(k2);
			bottomUpHeapConstruction(randomElements);
		}
		private void bottomUpHeapConstruction(List<Entry<K,V>> heap) {
			/* TODO ADD YOUR CODE HERE */		
			this.elements = new ArrayList(heap.size());
			for(int i = 0; i < heap.size(); i++) {
				this.elements.add(heap.get(i));
			}
			for(int i = this.size() - 1; i >= 0; i--)
				downHeap(i);
		}

		@Override
		public void add(K key, V value) {
			/* TODO ADD YOUR CODE HERE */
			this.elements.add(new HeapEntry(key, value));
			this.upHeap(this.size() - 1);
		}

		@Override
		public Entry<K, V> removeMin() {
			if(isEmpty())
				throw new EmptyHeapException();
			Entry<K, V> min = this.elements.get(0);
			this.elements.remove(0);
			if(this.isEmpty())
				return min;
			Entry<K, V> newRoot = this.elements.get(this.elements.size() - 1);
			this.elements.remove(this.size() - 1);
			this.elements.add(0, newRoot);
			this.downHeap(0);
			return min; // Dummy Return
		}

		@Override
		public Entry<K, V> getMin() {
			/* TODO ADD YOUR CODE HERE */
			if(isEmpty())
				throw new EmptyHeapException();
			return this.elements.get(0); // Dummy Return
		}

		@Override
		public void clear() {
			/* TODO ADD YOUR CODE HERE */
			this.elements.clear();
		}

		@Override
		public int size() {
			/* TODO ADD YOUR CODE HERE */
			return this.elements.size(); // Dummy Return
		}

		@Override
		public boolean isEmpty() {
			/* TODO ADD YOUR CODE HERE */
			return this.elements.isEmpty(); // Dummy Return
		}
		
		private void upHeap(int i) {
			/* TODO ADD YOUR CODE HERE */
			int y = (int) Math.floor((i - 1) / 2);
			Entry<K, V> child = this.elements.get(i);
			Entry<K, V> parent = this.elements.get(y);
			while(i >= 0 && comparator.compare(child.getKey(), parent.getKey()) < 0 ) {
				this.elements.set(i, parent);
				this.elements.set(y, child);
				i = y;
				y = (int) Math.floor((i - 1) / 2);
				child = this.elements.get(i);
				parent = this.elements.get(y);
			}
		}
		
		private void downHeap(int i) {
			/* TODO ADD YOUR CODE HERE */
			int l = 2*i + 1;
			int r = 2*i + 2;
			Entry<K, V> parent = this.elements.get(i);
			if(l >= this.elements.size() && r >= this.elements.size()) {
				return;
			}
			else if(r >= this.elements.size() && comparator.compare(parent.getKey(), this.elements.get(l).getKey()) > 0) {
				this.elements.set(i, this.elements.get(l));
				this.elements.set(l, parent);
				downHeap(l);
				return;
			}
			else if(l >= this.elements.size() && comparator.compare(parent.getKey(), this.elements.get(r).getKey()) > 0) {
				this.elements.set(i, this.elements.get(r));
				this.elements.set(r, parent);
				downHeap(r);
				return;
			}
			else if (l< this.elements.size() && r < this.elements.size()) {
				Entry<K, V> leftChild = this.elements.get(l);
				Entry<K, V> rightChild = this.elements.get(r);
				int y = comparator.compare(leftChild.getKey(), rightChild.getKey()) < 0? l: r;
				if(comparator.compare(parent.getKey(), this.elements.get(y).getKey()) > 0) {
					this.elements.set(i, this.elements.get(y));
					this.elements.set(y, parent);
					downHeap(y);
				}
			}
			return;
		}
		
		@Override
		public String toString() {
			if(isEmpty())
				return "[ ]";
			String s = "[";
			for(int i = 0; i < size() - 1; i++) 
				s += elements.get(i).getKey() + ", ";
			
			s += elements.get(size() - 1).getKey() + "]";
			
			return s;
		}
		
		@Override
		public void print(int minWidth) {

		    int size = size();

		    int level = (int) (Math.log(size) / Math.log(2));
		    int maxLength = (int) Math.pow(2, level) * minWidth;
		    int currentLevel = -1 ;
		    int width = maxLength;

		    for (int i = 0; i < size; i++) {
		        if ((int) (Math.log(i + 1) / Math.log(2)) > currentLevel) {
		            currentLevel++;
		            System.out.println();
		            width = maxLength / (int) Math.pow(2, currentLevel);
		        }
		        System.out.print(center(String.valueOf(elements.get(i).getKey()), width));
		    }
		    System.out.println();
		}
		
		private String center(String text, int len){
		    String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
		    float mid = (out.length()/2);
		    float start = mid - (len/2);
		    float end = start + len; 
		    return out.substring((int)start, (int)end);
		}
	}
	

	public static int[] getProcessOrder(int[][] processes) {
			
			Arrays.sort(processes, Comparator.comparingInt(a -> a[0]));

			// min-heap
			PriorityQueue<int[]> tmp = new PriorityQueue<>((a, b) -> {
				if (a[1] != b[1]) {
					return a[1] - b[1]; 
				}
				return a[2] - b[2]; 
			});

			int[] order = new int[processes.length];
			int orderIndex = 0;
			int time = 0;
			int i = 0;

			while (orderIndex < processes.length) {
				while (i < processes.length && processes[i][0] <= time) {
					tmp.add(new int[]{processes[i][0], processes[i][1], i}); 
					i++;
				}

				if (tmp.isEmpty()) {
					time = processes[i][0];
				} else {
					
					int[] process = tmp.poll();
					time += process[1];
					order[orderIndex++] = process[2]; 
				}
			}

			return order;
		}
}