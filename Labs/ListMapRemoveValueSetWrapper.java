import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ListMapRemoveValueSetWrapper {
	
	public static interface Position<E> {
		
		public E getElement();

	}

	public static interface PositionalList<T> extends Iterable<T> {
		
		public int size();
		
		public boolean isEmpty();
		
		/** 
		 * Returns first Position in the List
		 * 
		 * @return (Position<T>) Returns the first position in the Lists. 
		 */
		public Position<T> first();
		
		/** 
		 * Returns the last Position in the List
		 * 
		 * @return (Position<T>) Returns the first Position in the Lists.
		 */
		public Position<T> last();
		
		/** 
		 * Returns the Position before the given Position p. If the given node
		 * is the header it returns null.
		 * 
		 * Example: A <-> B <-> C
		 * The call before(B) returns A (the node, not the value)
		 * Same as calling B.getPrev() in a LinkedList
		 *   
		 * @param p - (Position<T>) A position from the List. Assumed to be valid.
		 * @return (Position<T>) The position previous to the given Position p. 
		 */
		public Position<T> before(Position<T> p);

		/** 
		 * Returns the Position after the given Position p. If the Position 
		 * given is the tail it returns null.
		 * 
		 * Example: A <-> B <-> C
		 * The call before(B) returns C (the node, not the value)
		 * Same as calling B.getNext() in a LinkedList
		 * 
		 * @param p - (Position<T>) A position from the List. Assumed to be valid.
		 * @return (Position<T>) The position that goes after the give Position p.
		 */
		public Position<T> after(Position<T> p);

		/** 
		 * Adds a new first node
		 * 
		 * @param e - (T) Value to be added at the beginning of the List
		 */
		public void addFirst(T e);
		
		/** 
		 * Adds a new last node
		 * 
		 * @param e - (T) Value to be added at the end of the List
		 */
		public void addLast(T e);
		
		/**
		 * Adds a new Position with value e before the given Position p.
		 *  
		 * Example: A <-> B <-> C
		 * The call addBefore(B, Z) changes the list to: A <-> Z <-> B <-> C 
		 * Same as calling B.setPrev(Z) in a LinkedList  
		 * 
		 * @param p - (Position<T>) A position from the List. Assumed valid.
		 * @param e - (T) Value we want to add to the List.
		 */
		public void addBefore(Position<T> p, T e);
		 
		/** 
		 * Adds a new Position with value e after the given Position p.
		 * 
		 * Example: A <-> B <-> C
		 * The call addAfter(B, Z) changes the list to: A <-> B <-> Z <-> C 
		 * Same as calling B.setNext(Z) in a LinkedList  
		 * @param p - (Position<T>) A position from the List. Assumed valid.
		 * @param e - (T) Value we want to add to the List.
		 */
		public void addAfter(Position<T> p, T e);
		
		/** Changes the value of the given Position
		 * 
		 * @param p - (Position<T>) Position whose value we want to change.
		 * @param e - Value we want to assign to Position p.
		 */
		public T set(Position<T> p, T e);
		
		/** Removes Position p from the List
		 * 
		 * @param p - (Position<T>) Position in the List we want to remove.
		 */
		public T remove(Position<T> p);
		/**
		 * Returns an Iterable with all the Positions from the List.
		 * The Iterable is a collection that can be iterated over using a
		 * for-each.
		 * @return (Iterable<Position<T>>) Collection we can iterate over.
		 */
		public Iterable<Position<T>> getPositions();


	}
	
	@SuppressWarnings("unused")
	public static class DLLPositionalList<T> implements PositionalList<T> {
		private static class Node<T> implements Position<T>{
			
			private T element;
			private Node<T> next;
			private Node<T> prev;
			
			

			public Node(T element, Node<T> next, Node<T> prev) {
				super();
				this.element = element;
				this.next = next;
				this.prev = prev;
			}

			public Node() {
				super();
				this.element = null;
				this.next = null;
				this.prev = null;
			}


			public Node<T> getNext() {
				return next;
			}



			public void setNext(Node<T> next) {
				this.next = next;
			}



			public Node<T> getPrev() {
				return prev;
			}



			public void setPrev(Node<T> prev) {
				this.prev = prev;
			}



			public void setElement(T element) {
				this.element = element;
			}



			@Override
			public T getElement() {
				return this.element;
			}
			
		}

		@SuppressWarnings("hiding")
		private class PositionalListIterator<T> implements Iterator<T>{
			private Node<T> currentPosition;
			
			@SuppressWarnings("unchecked")
			private PositionalListIterator() {
				this.currentPosition = (Node<T>) header.getNext();
			}
			@Override
			public boolean hasNext() {
				return this.currentPosition != tail;
			}

			@Override
			public T next() {
				if (hasNext()) {
					T result = this.currentPosition.getElement();
					this.currentPosition = this.currentPosition.getNext();
					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}
			
		}
		private int size;
		private Node<T> header;
		private Node<T> tail;
		
		public DLLPositionalList() {
			this.size= 0;
			this.header = new Node<T>();
			this.tail= new Node<T>();
			this.header.setNext(this.tail);
			this.tail.setPrev(this.header);
		}
		
		@Override
		public int size() {
			return this.size;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public Position<T> first() {
			if (this.isEmpty()) {
				return null;
			}
			else {
				return this.header.getNext();
			}
		}

		@Override
		public Position<T> last() {
			if (this.isEmpty()) {
				return null;
			}
			else {
				return this.tail.getPrev();
			}
		}

		@Override
		public Position<T> before(Position<T> p) {
			if (p == null) {
				throw new IllegalArgumentException();
			}
			Node<T> temp = (Node<T>) p;
			if (temp.getPrev() == this.header) {
				return null;
			}
			else {
				return temp.getPrev();
			}
		}

		@Override
		public Position<T> after(Position<T> p) {
			if (p == null) {
				throw new IllegalArgumentException();
			}
			Node<T> temp = (Node<T>) p;
			if (temp.getNext() == this.tail) {
				return null;
			}
			else {
				return temp.getNext();
			}
		}

		@Override
		public void addFirst(T e) {
			this.addBetween(this.header, this.header.getNext(), e);
		}

		@Override
		public void addLast(T e) {
			this.addBetween(this.tail.getPrev(), this.tail, e);
		}

		@Override
		public void addBefore(Position<T> p, T e) {
			Node<T> temp = (Node<T>) p;
			if (temp == this.header) {
				throw new IllegalArgumentException();
			}
			else {
				this.addBetween(temp.getPrev(), temp, e);
			}

		}

		@Override
		public void addAfter(Position<T> p, T e) {
			Node<T> temp = (Node<T>) p;
			if (temp == this.tail) {
				throw new IllegalArgumentException();
			}
			else {
				this.addBetween(temp, temp.getNext(), e);
			}

		}

		@Override
		public T set(Position<T> p, T e) {
			Node<T> temp = (Node<T>) p;
			if ((temp == this.header) ||
					(temp == this.tail)) {
				throw new IllegalArgumentException();
			}
			else {
				T result = temp.getElement();
				temp.setElement(e);
				return result;
			}
		}

		@Override
		public T remove(Position<T> p) {
			Node<T> temp = (Node<T>) p;
			if ((temp == this.header) ||
					(temp == this.tail)) {
				throw new IllegalArgumentException();
			}	
			else {
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				T result = temp.getElement();
				temp.setElement(null);
				temp.setNext(null);
				temp.setPrev(null);
				this.size--;
				return result;
			}
		}

		@Override
		public Iterator<T> iterator() {
			return new PositionalListIterator<T>();
		}

		private void addBetween(Node<T> prev, Node<T> next, T e) {
			Node<T> temp = new Node<T>();
			temp.setElement(e);
			temp.setNext(next);
			temp.setPrev(prev);
			prev.setNext(temp);
			next.setPrev(temp);
			this.size++;
		}

		@Override
		public Iterable<Position<T>> getPositions() {
			List<Position<T>> result = new ArrayList<Position<T>>();
			if (this.isEmpty()) {
				return result;
			}
			else {
				for (Node<T> temp = header.getNext(); temp != tail; temp = temp.getNext()) {
					result.add(temp);
				}
				return result;
			}
		}
	}

	public static interface Comparator<E> {
		int compare(E a, E b);

	}

	public static class DefaultComparator<K> implements Comparator<K> {

		@SuppressWarnings("unchecked")
		@Override
		public int compare(K a, K b) {
			return ((Comparable<K>) a).compareTo(b);
		}
		

	}

	public static interface Entry<K, V> {
		
		public K getKey();
		
		public V getValue();

	}

	public static interface Map<K, V> {
		
		int size(); 
	    boolean isEmpty(); 
	    V get(K key);    
	    V put(K key, V value); 
	    V remove(K key); 
//	    Iterable<K> keySet(); 
	    Iterable<V> valueSet(); 
//	    Iterable<Entry<K,V>> entrySet(); 
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static class PLMap<K, V> implements Map<K, V> {
		private static class PLMapEntry<K,V> implements Entry<K,V> {

			private K key;
			private V value;
			
			
			public PLMapEntry(K key, V value) {
				super();
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
			
		}

		private PositionalList<Entry<K,V>> L;
		private Comparator<K> comp;
		
		public PLMap() {
			super();
			this.L = new DLLPositionalList<Entry<K,V>>();
			this.comp = new DefaultComparator<K>();
		}

		public PLMap(Comparator<K> comp) {
			super();
			this.L = new DLLPositionalList<Entry<K,V>>();
			this.comp = comp;
		}
		@Override
		public int size() {
			return L.size();
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public V get(K key) {
			for (Entry<K,V> e : this.L) {
				if (this.comp.compare(key, e.getKey()) == 0) {
					return e.getValue();
				}
			}
			return null;
		}

		@Override
		public V put(K key, V value) {
			
			this.remove(key);
			
			for (Position<Entry<K,V>> p: this.L.getPositions()) {
				int c = this.comp.compare(key,  p.getElement().getKey());
				if (c <= 0) {
					this.L.addBefore(p, new PLMapEntry<K,V>(key, value));
					return value;
				}
			}
			this.L.addLast(new PLMapEntry<K,V>(key, value));
			return value;
		}
		
		@Override
		public V remove(K key) {
			
			
			for (Position<Entry<K,V>> p : this.L.getPositions()) {
				if (this.comp.compare(key, p.getElement().getKey()) == 0) {
					V value = p.getElement().getValue();
					this.L.remove(p);
					return value;
				}
			}

			return null; 
		}
		
		@Override
		public Iterable<V> valueSet() {
			List<V> values = new ArrayList<>();
			
			
			for (Entry<K,V> entry : this.L) 
				values.add(entry.getValue());
			
			
			return values;
		}
		
	}
}