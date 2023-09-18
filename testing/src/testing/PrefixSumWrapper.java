package testing;


public class PrefixSumWrapper {
	
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
	
	public static int prefixSum(Node<Integer> head, int n) {
		int total = 0;
		int counter = 0;
		Node<Integer> currentNode = head;

		while (currentNode != null) {
			total += currentNode.getElement();
			counter++;

			if (total == n) {
				return counter;
			} else if (total > n) {
				return -counter;
			}

			currentNode = currentNode.getNext();
			}

			return -counter;
		}
	
	public static int removeAfter(Node<Integer> head, int n) {
	    if (head == null) 
	        return 0; 
	    

	    int removedCount = 0;
	    Node<Integer> currentNode = head;

	    while (currentNode != null && currentNode.getNext() != null) {
	        if (currentNode.getElement().equals(n) && 
	            !currentNode.getNext().getElement().equals(n)) {

	            
	            Node<Integer> nodeToRemove = currentNode.getNext();
	            currentNode.setNext(nodeToRemove.getNext());

	            
	            nodeToRemove.clear();
	            removedCount++;
	        } else {
	            
	            currentNode = currentNode.getNext();
	        }
	    }

	    return removedCount;
	}

}




//// Creating a linked list
//LinkedList<String> animals = new LinkedList<>();
//animals.add("Cow");
//animals.add("Cat");
//animals.add("Dog");
//System.out.println("LinkedList: " + animals);
//
//// Using forEach loop
//System.out.println("Accessing linked list elements:");
//for(String animal: animals) {
//    System.out.print(animal);
//    System.out.print(", ");
//}