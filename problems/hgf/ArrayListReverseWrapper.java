import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayListReverseWrapper {
	public static interface List<E> extends Iterable<E> {
		public int size();
		public boolean isEmpty();
		public boolean isMember(E e);
		public int firstIndexOf(E e);
		public int lastIndexOf(E e);
		public void add(E e);
		public void add(E e, int index);
		public E get(int index);
		public void set(int index, E e);
		public E remove(int index);
	    public boolean remove(E e);
	    public int removeAll(E e);
		public void clear();		
		public void reverse();
	}
	
	public static class ArrayList<E> implements List<E> {
		@SuppressWarnings("hiding")
		private class ArrayListIterator<E> implements Iterator<E> {
			private int currentPosition;
			public ArrayListIterator() {
				super();
				this.currentPosition = 0;
			}
			@Override
			public boolean hasNext() {
				return this.currentPosition < currentSize;
			}

			@Override
			@SuppressWarnings("unchecked")
			public E next() {
				if (this.hasNext()) {
					E result = (E) elements[this.currentPosition++]; // elements is array in enclosing class
					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}
		}
	 	
		private E[] elements;
		private int currentSize;
		private static final int DEFAULT_SIZE = 10;
		
		@SuppressWarnings("unchecked")
		public ArrayList(int initialSize) {
			if (initialSize < 1) {
				throw new IllegalArgumentException("Size must be at least 1.");
			}
			this.elements = (E[]) new Object[initialSize];
			this.currentSize = 0;
		}

		public ArrayList() {
			this(DEFAULT_SIZE);
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
		public boolean isMember(E e) {
			return this.firstIndexOf(e) >= 0;
		}

		@Override
		public int firstIndexOf(E e) {
			for (int i=0; i < this.size(); ++i) {
				if (this.elements[i].equals(e)) {
					return i;
				}
			}
			return -1;
		}

		@Override
		public void add(E e) {
			if (this.size() == this.elements.length) {
				this.reAllocate();
			}
			this.elements[this.currentSize++]  = e;
		}

		@SuppressWarnings("unchecked")
		private void reAllocate() {
			E[] temp = (E[]) new Object[2*this.size()];
			for (int i=0; i < this.size(); ++i) {
				temp[i] = this.elements[i];
			}
			this.elements = temp;
		}

		@Override
		public void add(E e, int position) {
			if ((position < 0) || (position > this.currentSize)){
				throw new IndexOutOfBoundsException("Illegal position");
			}
			if (position == this.currentSize) {
				this.add(e);
			}
			else {
				if (this.size() == this.elements.length) {
					this.reAllocate();
				}
				for (int i=this.currentSize; i > position; --i) {
					this.elements[i] = this.elements[i-1];
				}
				this.elements[position] = e;
				this.currentSize++;
			}	
		}

		@Override
		public E get(int position) {
			if ((position < 0) || (position >= this.currentSize)) {
				throw new IndexOutOfBoundsException("Illegal position");
			}
			return this.elements[position];
		}
		
		@Override
		public void set(int index, E e) {
			if((index < 0) || (index >= this.currentSize)) 
				throw new IndexOutOfBoundsException("Illegal position");
			
			elements[index] = e;
		}
		@Override
	    public boolean remove(E e) {
			int position = -1;
			E elmRemoved = null;
	    	for (int i = 0; i < elements.length; i++) {
	    		if(elements[i].equals(e)) {
	    			position = i;
	    			break;
	    		}
			}
	    	
	    	if(position >= 0) {
	    		elmRemoved = remove(position);
	    	}
	    	
	    	return elmRemoved != null;
	    }

		@Override
		public E remove(int position) {
			if ((position < 0) || (position >= this.currentSize)) {
				throw new IndexOutOfBoundsException("Illegal position");
			}
			E result = this.elements[position];

			for (int i=position; i < this.size() - 1; ++i) {
				this.elements[i] = this.elements[i + 1];
			}
			this.elements[this.currentSize-1] = null;
			this.currentSize--;
			return result;
		}
		
		@Override
		public int removeAll(E e) {
			int count = 0;
			while(remove(e)) count++;
			return count;
		}

		@Override
		public void clear() {
			while(!this.isEmpty()) {
				this.remove(0);
			}
		}

		@Override
		public Iterator<E> iterator() {
			return new ArrayListIterator<E>();
		}

		@Override
		public int lastIndexOf(E e) {
			for (int i=this.currentSize-1; i>= 0; --i) {
				if (this.elements[i].equals(e)) {
					return i;
				}
			}
			// not found
			return -1;
		}

		/**
		 * Implement a member method called reverse() which reverses the elements in a list. 
		 * For example, if L = {Ken, Al, Bob, Mel} then a call to L.reverse() turns L  into 
		 * L = {Mel, Bob, Al, Ken}.
		 */
		@Override
		public void reverse() {
			int size1 = this.currentSize;
			for(int i = 0; i < this.currentSize; i++){
			this.add(this.get(size1 -i), i);
			}
			
		}
	}
}



/*
hago un while y paso alfrente al ultimo asi suscesivamente


*/