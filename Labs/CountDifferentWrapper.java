import java.io.PrintStream;

public class CountDifferentWrapper {


	public interface Queue<E> {
		
		public int size();
		
		public boolean isEmpty();
		
		public E front();
		
		public void enqueue(E e);
		
		public E dequeue();
		
		public void makeEmpty();
		
		public void print(PrintStream P);
		
		public int countDifferentFrom(E e);

	}

	public static class DoublyLinkedQueue<E> implements Queue<E> {
		
		private static class Node<E>{
			private E element;
			private Node<E> next;
			private Node<E> prev;
			
			public Node() {
				this.element = null;
				this.next = this.prev = null;
				
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
			public Node<E> getPrev() {
				return prev;
			}
			public void setPrev(Node<E> prev) {
				this.prev = prev;
			}
			
		}

		private Node<E> header;
		private Node<E> tail;
		private int currentSize;
		
		public DoublyLinkedQueue() {
			this.currentSize = 0;
			this.header = new Node<>();
			this.tail = new Node<>();
			
			this.header.setNext(this.tail);
			this.tail.setPrev(this.header);
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
		public E front() {
			return (this.isEmpty() ? null : this.header.getNext().getElement());
		}

		@Override
		public E dequeue() {
			if (this.isEmpty()) {
				return null;
			}
			else {
				Node<E> target = null;
				target = this.header.getNext();
				E result = target.getElement();
				this.header.setNext(target.getNext());
				target.getNext().setPrev(this.header);
				target.setNext(null);
				target.setPrev(null);
				target.setElement(null);
				this.currentSize--;
				return result;
			}
		}

		@Override
		public void enqueue(E e) {
			Node<E> newNode = new Node<E>();
			newNode.setElement(e);
			newNode.setNext(this.tail);
			newNode.setPrev(this.tail.getPrev());
			this.tail.setPrev(newNode);
			newNode.getPrev().setNext(newNode);
			this.currentSize++;
		}

		@Override
		public void makeEmpty() {
			while (this.dequeue() != null);

		}

		@Override
		public void print(PrintStream P) {
			// TODO Auto-generated method stub
			Node<E> temp = this.header.getNext();
			while(temp != this.tail) {
				P.println(temp.getElement());
				temp = temp.getNext();
			}
		}
		@Override
		public String toString() {
			String str = "{ ";
			Node<E> temp = this.header.getNext();
			while(temp != this.tail) {
				str += temp.getElement() + " ";
				temp = temp.getNext();
			}
			return str + "}";
		}


		/**
		 * Extend the functionality of the Queue implemented with Doubly Linked List by adding a 
		 * member method named countDifferentFrom. This method returns the number of elements in the
		 * queue that are different from an element e in the queue. The method returns 0 if the element 
		 * e is not found in the queue. For example if the queue Q = {Joe, Ned, Bob, Kim, Bob}, then 
		 * Q.countDifferent(Bob) returns 3.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public int countDifferentFrom(E e) {
			int counter = 0;
			Node<E> temp = this.header.getNext();

			while(temp != this.tail) {
				if(!e.equals(temp.getElement())) {
					counter++;
				} temp = temp.getNext();
			}
			return counter;
		}



	}

		public static void main(String[] args){
		DoublyLinkedQueue<String> Q = new DoublyLinkedQueue<>();
		Q.enqueue("Joe");
		Q.enqueue("Ned");
		Q.enqueue("Bob");
		Q.enqueue("Mel");
		Q.enqueue("Kim");

		Q.enqueue(Q.front());

	Q.enqueue("Al");
	Q.enqueue(Q.dequeue());
	Q.enqueue(Q.dequeue());
	Q.enqueue(Q.front());
	Q.enqueue(Q.dequeue());
	Q.dequeue();
	Q.dequeue();

	System.out.println(Q);

	}

}