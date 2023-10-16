package labs.lab5;

public class HasCycleWrapper {
	/**
	 * Class that holds a single piece of data and a single reference to the node that goes after it.
	 * @author Gretchen Bonilla
	 *
	 * @param <E>
	 */
	public static class Node<E> {
		// References the node that goes after it
		private Node<E> next;
		// The data
		private E element;
		
		/**
		 * Create empty node.
		 */
		public Node() {
			this.next = null;
			this.element =null;
		}
		/**
		 * Creates node with data elm and whose next node is next
		 * @param next - (Node) The node the goes after this one
		 * @param elm - (E) the data that this node holds
		 */
		public Node(Node<E> next, E elm) {
			this.next = next;
			this.element = elm;
		}
		/**
		 * Creates a node that holds the element elm, but doesn't have a next node yet.
		 * @param elm - (E) the data that the node will hold
		 */
		public Node(E elm) {
			this.element = elm;
			this.next=null;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element;
		}
		public void setElement(E element) {
			this.element = element;
		}
		/**
		 * Empties the node to help with clean up
		 */
		public void clear() {
			this.next = null;
			this.element=null;
		}	
		
	}

	public static boolean hasCycle(Node<Integer> head) {
	    if (head == null) {
	        return false;
	    }

	    Node<Integer> tortoise = head; 
	    Node<Integer> hare = head;     

	    while (hare != null && hare.getNext() != null) {
	        tortoise = tortoise.getNext();      
	        hare = hare.getNext().getNext();     

	        if (tortoise == hare) {
	            return true;
	        }
	    }

	    return false; //  no cycle
	}

	

}