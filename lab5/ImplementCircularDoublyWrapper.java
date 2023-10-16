package labs.lab5;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ImplementCircularDoublyWrapper {
	
	public static interface List<E> extends Iterable<E>{
		public void add(E elm);
		public void add(E elm, int index);
		public boolean remove(int index);
		public boolean remove(E elm);
		public E get(int index);
		public E set(E elm, int index);
		public int size();
		public int removeAll(E elm);
		public void clear();
		public boolean contains(E elm);
		public int firstIndex(E elm);
		public int lastIndex(E elm);
		public boolean isEmpty();

	}

	public static class CircularDoublyLinkedList<E> implements List<E> {

		private class Node {
			private E value;
			private Node next, prev;

			public Node(E value, Node next, Node prev) {
				this.value = value;
				this.next = next;
				this.prev = prev;
			}

			public Node(E value) {
				this(value, null, null); // Delegate to other constructor
			}

			public Node() {
				this(null, null, null); // Delegate to other constructor
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

			public Node getPrev() {
				return prev;
			}

			public void setPrev(Node prev) {
				this.prev = prev;
			}

			public void clear() {
				value = null;
				next = prev = null;
			}				
		} // End of Node class


		private class ListIterator implements Iterator<E> {

			private Node nextNode;

			public ListIterator() {
				nextNode = header.getNext();
			}

			@Override
			public boolean hasNext() {
				return nextNode != header;
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

		} // End of ListIterator class, DO NOT REMOVE, TEST WILL FAIL
		
		/* private fields */
		private Node header; // "dummy" node
		private int currentSize;


		public CircularDoublyLinkedList() {
			
			header = new Node(null);
			
			
			// Nos aseguramos que sea una estructura ciclica 
			header.setNext(header);
			header.setPrev(header);
			
			
			/* TODO
			 * What should the header point to??
			 * Is suppose to point to both the last and first element. 
			 * Remember the List is supposed to act like a circle
			 */
			currentSize = 0;
		}

		@Override
        public void add(E obj) {
            Node newNode = new Node(obj);
            Node last = header.getPrev();
            
            last.setNext(newNode);
            newNode.setPrev(last);
            newNode.setNext(header);
            header.setPrev(newNode);
            
            currentSize++;
        }


		@Override
        public void add(E elm, int index) {
            if (index < 0 || index > currentSize)
                throw new IndexOutOfBoundsException();
            
            Node newNode = new Node(elm);
            Node current = header;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            
            newNode.setNext(current.getNext());
            newNode.setPrev(current);
            current.getNext().setPrev(newNode);
            current.setNext(newNode);
            
            currentSize++;
        }
		
		

	     @Override
	        public boolean remove(E obj) {
	            Node current = header.getNext();
	            while (current != header) {
	                if (current.getValue().equals(obj)) {
	                    current.getPrev().setNext(current.getNext());
	                    current.getNext().setPrev(current.getPrev());
	                    current.clear();
	                    currentSize--;
	                    return true;
	                }
	                current = current.getNext();
	            }
	            return false;
	        }

	        @Override
	        public boolean remove(int index) {
	            if (index < 0 || index >= currentSize)
	                throw new IndexOutOfBoundsException();

	            Node current = header.getNext();
	            for (int i = 0; i < index; i++) {
	                current = current.getNext();
	            }
	            current.getPrev().setNext(current.getNext());
	            current.getNext().setPrev(current.getPrev());
	            current.clear();
	            currentSize--;
	            return true;
	        }

	        @Override
	        public int removeAll(E obj) {
	            int count = 0;
	            Node current = header.getNext();
	            while (current != header) {
	                if (current.getValue().equals(obj)) {
	                    current.getPrev().setNext(current.getNext());
	                    current.getNext().setPrev(current.getPrev());
	                    Node temp = current;
	                    current = current.getNext();
	                    temp.clear();
	                    currentSize--;
	                    count++;
	                } else {
	                    current = current.getNext();
	                }
	            }
	            return count;
	        }


        @Override
        public E get(int index) {
            if (index < 0 || index >= currentSize)
                throw new IndexOutOfBoundsException();

            Node current = header.getNext();
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getValue();
        }

        @Override
        public E set(E elm, int index) {
            if (index < 0 || index >= currentSize)
                throw new IndexOutOfBoundsException();

            Node current = header.getNext();
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            E oldValue = current.getValue();
            current.setValue(elm);
            return oldValue;
        }

        @Override
        public int firstIndex(E obj) {
            Node current = header.getNext();
            int index = 0;
            while (current != header) {
                if (current.getValue().equals(obj)) {
                    return index;
                }
                current = current.getNext();
                index++;
            }
            return -1;
        }

        @Override
        public int lastIndex(E obj) {
            Node current = header.getPrev();
            int index = currentSize - 1;
            while (current != header) {
                if (current.getValue().equals(obj)) {
                    return index;
                }
                current = current.getPrev();
                index--;
            }
            return -1;
        }

	      @Override
	        public int size() {
	            return currentSize;
	        }

	        @Override
	        public boolean isEmpty() {
	            return currentSize == 0;
	        }

	        @Override
	        public boolean contains(E obj) {
	            Node current = header.getNext();
	            while (current != header) {
	                if (current.getValue().equals(obj)) {
	                    return true;
	                }
	                current = current.getNext();
	            }
	            return false;
	        }

	        @Override
	        public void clear() {
	            header.setNext(header);
	            header.setPrev(header);
	            currentSize = 0;
	        }
	        
	        
		@Override
		public Iterator<E> iterator() {
			return new ListIterator();
		} //DO NOT DELETE, TESTS WILL FAIL
		public String toString() {
			Node temp = this.header.getNext();
			String r = "{";
			while(temp != header) {
				r += " " + temp.getValue() + ",";
				temp = temp.getNext();
			}
			r = r.substring(0, r.length()-1) + "}";
			return r;
		}
	}

}
