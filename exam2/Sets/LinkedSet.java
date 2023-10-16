import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Set ADT using a Singly Linked Structure
 * @author Fernando J. Bermudez - bermed28
 *
 * @param <E>
 */
public class LinkedSet<E> implements Set<E> {

	@SuppressWarnings("hiding")
	private class Node<E> {
		private E element;
		private Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

		public Node(E element) {
			this(element, null);
		}

		public Node() {
			this(null, null);
		}

		public E getElement() {
			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public void clear() {
			this.element = null;
			this.next = null;
		}
	}

	private class SetIterator implements Iterator<E> {
		Node<E> curNode;
		
		public SetIterator() {
			curNode = header.getNext();
		}
		
		@Override
		public boolean hasNext() {
			return curNode != null;
		}

		@Override
		public E next() {
			if(hasNext()) {
				E elm = curNode.getElement();
				curNode = curNode.getNext();
				return elm;
			}
			throw new NoSuchElementException();
		}

	}

	private Node<E> header;
	private int currentSize;

	public LinkedSet() {
		header = new Node<>();
		currentSize = 0;
	}

	@Override
	public boolean add(E newEntry) {
		if(newEntry == null)
			throw new IllegalArgumentException("Parameter cannot be null");
		if(!isMember(newEntry)) {
			Node<E> newNode = new Node<>(newEntry, header.getNext());
			header.setNext(newNode);
			currentSize++;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean remove(E element) {
		Node<E> curNode;
		for(curNode = header; curNode.getNext() != null; curNode = curNode.getNext()) {
			if(curNode.getNext().getElement().equals(element)) {
				Node<E> rmNode = curNode.getNext();
				curNode.setNext(rmNode.getNext());
				rmNode.clear();
				rmNode = null;
				currentSize--;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isMember(E element) {
		for(Node<E> curNode = header.getNext(); curNode != null; curNode = curNode.getNext())
			if(curNode.getElement().equals(element)) 
				return true;
			
		return false;
		
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
		Node<E> curNode = header.getNext();
		Node<E> nextNode = curNode.getNext();

		while (nextNode.getNext() != null) {
			curNode = nextNode;
		}

		nextNode.clear();
		nextNode = null;

		currentSize = 0;
	}



	@Override
	public Set<E> union(Set<E> S2) {
		Set<E> result = new ArraySet<>();
		for (E e : this) 
			result.add(e);


		for (E e : S2) 
			if(!result.isMember(e))
				result.add(e);

		return result;
	}

	@Override
	public Set<E> intersection(Set<E> S2) {
		Set<E> result = new ArraySet<>();
		for (E e : this) 
			if(S2.isMember(e))
				result.add(e);

		return result;
	}

	@Override
	public Set<E> difference(Set<E> S2) {
		Set<E> result = new ArraySet<>();
		for (E e : this) 
			if(!S2.isMember(e))
				result.add(e);

		return result;
	}

	@Override
	public boolean isSubset(Set<E> S2) {
		for (E e : this) {
			if(!S2.isMember(e))
				return false;
		}
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new SetIterator();
	}

}
