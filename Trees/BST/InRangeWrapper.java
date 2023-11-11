package BST;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InRangeWrapper {

	// List
	public interface List<E> extends Iterable<E> {
		
		public int size();
		
		public boolean isEmpty();
		
		public boolean isMember(E e);

		public void add(E e);
		
		public void add(E e, int index);
		
		public E first();
		
		public E last();
		
		public int firstIndex(E e);
		
		public int lastIndex(E e);
		
		public E get(int index);
		
		public E replace(E e, int index);
		
		public E remove(int index);
		
		public boolean remove(E e);
		
		public int removeAll(E e);
		
		public void clear();
		
		public Object[] toArray();
		

	}
	
	
	///////////////////// SinglyLinkedList
	///////////
	public static class SinglyLinkedList<E> implements List<E> {
		
		@SuppressWarnings("hiding")
		private class SinglyLinkedListIterator<E> implements Iterator<E>{
			
			private Node<E> nextNode;
			

			@SuppressWarnings("unchecked")
			public SinglyLinkedListIterator() {
				super();
				this.nextNode = (Node<E>) header.getNext();
			}

			@Override
			public boolean hasNext() {
				return this.nextNode != null;
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					E result = this.nextNode.getElement();
					this.nextNode = this.nextNode.getNext();
					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}
			
		}
		
		
		// node class
		private static class Node<E>{
			private E element;
			private Node<E> next;
			public Node(E element, Node<E> next) {
				super();
				this.element = element;
				this.next = next;
			}
			public Node() {
				super();
				this.element = null;
				this.next = null;
			
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
			
			
		}
		
		// private fields
		private Node<E> header;
		private int currentSize;
		
		

		public SinglyLinkedList() {
			this.header = new Node<>();
			this.currentSize = 0;
			
		}

		@Override
		public Iterator<E> iterator() {
			return new SinglyLinkedListIterator<E>();
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
			return this.firstIndex(e) >= 0;
		}

		@Override
		public void add(E e) {
			
			Node<E> temp = this.header;
			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			temp.setNext(new Node<E>(e, null));
			this.currentSize++;
			
			// cool
			//this.getPosition(this.size() -1).setNext(new Node<E>(e, null));
			//this.currentSize++;
		}

		@Override
		public void add(E e, int index) {
			if ((index <0) || (index > this.size())){
				throw new IndexOutOfBoundsException();
			}
			if (index == this.size()) {
				this.add(e);
			}
			else {
				Node<E> temp = null; 
				if (index == 0) {
					temp = this.header;
				}
				else {
					temp = this.getPosition(index -1);
				}
				// add new node
				Node<E> newNode = new Node<>();
				newNode.setElement(e);
				newNode.setNext(temp.getNext());
				temp.setNext(newNode);
				this.currentSize++;
			}
		}

		@Override
		public E first() {
			if (this.isEmpty()) {
				return null;
			}
			else {
				return this.header.getNext().getElement();
			}
		}

		@Override
		public E last() {
			if (this.isEmpty()) {
				return null;
			}
			else {
				Node<E> temp = this.header.getNext();
				while (temp.getNext() != null) {
					temp = temp.getNext();
				}
								
				return temp.getElement(); 
			}
		}

		@Override
		public int firstIndex(E e) {
			int currentPosition = 0;
			Node<E> temp = this.header.getNext();
			
			while(temp != null) {
				if (temp.getElement().equals(e)) {
					return currentPosition;
				}
				else {
					temp = temp.getNext();
					currentPosition++;
				}
			}
			return -1;
			
		}

		@Override
		public int lastIndex(E e) {
			int i=0;
			int lastIndex = -1;
			Node<E> temp = this.header.getNext();
			while (temp != null) {
				if (temp.getElement().equals(e)) {
					lastIndex = i;
				}
				i++;
				temp = temp.getNext();
			}
			return lastIndex;
		}
		
		private Node<E> getPosition(int index){
			int currentPosition = 0;
			Node<E> temp = this.header.getNext();
			
			while (currentPosition != index) {
				temp = temp.getNext();
				currentPosition++;
			}
			return temp;
		}

		@Override
		public E get(int index) {
			if ((index <0) || (index >= this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			return this.getPosition(index).getElement();
		}

		@Override
		public E replace(E e, int index) {
			if ((index <0) || (index >= this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			Node<E> temp = this.getPosition(index);
			E result = temp.getElement();
			temp.setElement(e);
			return result;
		}

		@Override
		public E remove(int index) {
			if ((index < 0) || (index >= this.currentSize)){
				throw new IndexOutOfBoundsException();
			}
			int currentPosition =0;
			Node<E> temp = this.header;
			while(currentPosition != index) {
				temp = temp.getNext();
				currentPosition++;
			}
			Node<E> target = temp.getNext();
			E result = target.getElement();
			temp.setNext(target.getNext());
			target.setElement(null);
			target.setNext(null);
			this.currentSize--;
			return result;

		}

		@Override
		public boolean remove(E e) {
			int target = this.firstIndex(e);
			if (target < 0) {
				return false;
			}
			else {
				this.remove(target);
				return true;
			}
		}

		@Override
		public int removeAll(E e) {
			int count = 0;
			while (this.remove(e)) {
				count++;
			}
			return count;
		}

		@Override
		public void clear() {
			while(!this.isEmpty()) {
				this.remove(0);
			}

		}

		@Override
		public Object[] toArray() {
			Object[] result = new Object[this.size()];
			for (int i=0; i < this.size(); ++i) {
				result[i]= this.get(i);
			}
			return result;
		}

	}
	
	///////////////  MAP
	////
	public interface Map<K, V> {
		
		public int size();
		
		public boolean isEmpty();
		
		public V get(K key);
		
		public V put(K key, V value);
		
		public V remove(K key);
		
		public boolean contains(K key);
		
		public List<K> getKeys();
		
		public List<V> getValues();	

	}
	
	////// Tree Node
	public interface TreeNode<E> {
		
		public E getValue();

	}
	
	
	/////////
	// Binary Tree Node
	public interface BinaryTreeNode<E> extends TreeNode<E> {
		
		public BinaryTreeNode<E> getLeftChild();
		
		public BinaryTreeNode<E> getRightChild();
		
		public BinaryTreeNode<E> getParent();
		

	}

	//////
	/// BinaryTreeNodeImp
	
	public static class BinaryTreeNodeImp<E> implements BinaryTreeNode<E> {
		
		private E value;
		
		private BinaryTreeNode<E>  leftChild;
		private BinaryTreeNode<E>  rightChild;
		private BinaryTreeNode<E>  parent;


		public BinaryTreeNodeImp(E value, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild,
				BinaryTreeNode<E> parent) {
			super();
			this.value = value;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.parent = parent;
		}

		@Override
		public E getValue() {
			return this.value;
		}

		@Override
		public BinaryTreeNode<E> getLeftChild() {
			return this.leftChild;
		}

		@Override
		public BinaryTreeNode<E> getRightChild() {
			return this.rightChild;
		}

		@Override
		public BinaryTreeNode<E> getParent() {
			return this.parent;
		}

		public void setValue(E value) {
			this.value = value;
		}

		public void setLeftChild(BinaryTreeNode<E> leftChild) {
			this.leftChild = leftChild;
		}

		public void setRightChild(BinaryTreeNode<E> rightChild) {
			this.rightChild = rightChild;
		}

		public void setParent(BinaryTreeNode<E> parent) {
			this.parent = parent;
		}


	}
	
	////// Key Value Pair
	public interface KeyValuePair<K, V> {
		
		public K getKey();
		public V getValue();

	}
	
	
	/// Binary Search Tree
	public static class BinarySearchTree<K, V> implements Map<K, V> {
		
		// MapEntry class implements KeyValuePair
		
		private static class MapEntry<K,V> implements KeyValuePair<K,V> {
			private K key;
			private V value;
			
			
			public MapEntry(K key, V value) {
				super();
				this.key = key;
				this.value = value;
			}
			public K getKey() {
				return key;
			}
			@SuppressWarnings("unused")
			public void setKey(K key) {
				this.key = key;
			}
			public V getValue() {
				return value;
			}
			@SuppressWarnings("unused")
			public void setValue(V value) {
				this.value = value;
			}		

		}
			
		
		private int currentSize;
		private BinaryTreeNode<MapEntry<K,V>> root;
		private Comparator<K> keyComparator;
		
		public BinarySearchTree(Comparator<K> keyComparator) {
			this.root = null;
			this.currentSize = 0;
			this.keyComparator = keyComparator;
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
		public V get(K key) {
			return this.getAux(key, this.root);
			
		}

		private V getAux(K key, BinaryTreeNode<MapEntry<K, V>> N) {
			if (N == null) {
				return null; // not found
			}
			else {
				int comparison = this.keyComparator.compare(key, N.getValue().getKey());
				if (comparison == 0) {
					return N.getValue().getValue();
				}
				else if (comparison < 0) {
					return this.getAux(key, N.getLeftChild());
				}
				else {
					return this.getAux(key, N.getRightChild());
				}
			}
		}

		@Override
		public V put(K key, V value) {
			if (this.root == null) {
				MapEntry<K,V> M = new MapEntry<K,V>(key, value);
				this.root = new 
						BinaryTreeNodeImp<MapEntry<K,V>>(M, null, null, null);
				this.currentSize++;
				return value;
			}
			else {
				return this.putAux(key, value, (BinaryTreeNodeImp<MapEntry<K, V>>) this.root);
			}
		}

		private V putAux(K key, V value, BinaryTreeNodeImp<MapEntry<K, V>> N) {
			int comparison = this.keyComparator.compare(key,  N.getValue().getKey());
			if (comparison < 0) {
				// left
				if (N.getLeftChild() == null) {
					MapEntry<K,V> M = new MapEntry<K,V>(key, value);
					BinaryTreeNodeImp<MapEntry<K,V>> newNode =
							new BinaryTreeNodeImp<MapEntry<K,V>> 
					(M, null, null, N);
					N.setLeftChild(newNode);
					this.currentSize++;
					return value;
				}
				else {
					return this.putAux(key, value, 
							(BinaryTreeNodeImp<MapEntry<K, V>>) N.getLeftChild());
				}
			}
			else {
				// right
				if (N.getRightChild() == null) {
					MapEntry<K,V> M = new MapEntry<K,V>(key, value);
					BinaryTreeNodeImp<MapEntry<K,V>> newNode =
							new BinaryTreeNodeImp<MapEntry<K,V>> 
					(M, null, null, N);
					N.setRightChild(newNode);
					this.currentSize++;
					return value;
				}
				else {
					return this.putAux(key, value, 
							(BinaryTreeNodeImp<MapEntry<K, V>>) N.getRightChild());
				}
				
			}
		}

		@Override
		public V remove(K key) {
			throw new UnsupportedOperationException("You will implement this in a later exercise");
		}

		@Override
		public boolean contains(K key) {
			return this.get(key) != null;
		}

		@Override
		public List<K> getKeys() {
			List<K> result = new SinglyLinkedList<K>();
			this.getKeysAux(this.root, result);
			return result;
			
		}

		private void getKeysAux(BinaryTreeNode<MapEntry<K, V>> N, List<K> result) {
			if (N == null) {
				return;
			}
			else {
				this.getKeysAux(N.getLeftChild(), result);
				result.add(N.getValue().getKey());
				this.getKeysAux(N.getRightChild(), result);
			}
		}

		@Override
		public List<V> getValues() {
			List<V> result = new SinglyLinkedList<V>();
			this.getValuesAux(this.root, result);
			return result;
			

		}

		private void getValuesAux(BinaryTreeNode<MapEntry<K, V>> N, List<V> result) {
			if (N == null) {
				return;
			}
			else {
				this.getValuesAux(N.getLeftChild(), result);
				result.add(N.getValue().getValue());
				this.getValuesAux(N.getRightChild(), result);
			}
			
		}

		public void print(PrintStream out) {
			printAux(this.root, out, 0);
			
			
		}
		
		public void printAux(BinaryTreeNode<MapEntry<K, V>> N, 
				PrintStream out, int spaces) {
			if (N == null) {
				return;
			}
			else {
				printAux(N.getRightChild(), out, spaces + 4);
				// print this values
				for (int i=0; i< spaces; ++i) {
					//out.print(" ");
				}
				//out.println(N.getValue().getKey());
				printAux(N.getLeftChild(), out, spaces + 4);
				
			}
		}


		// Inside the BinarySearchTree class




public ArrayList<KeyValuePair<K, V>> inRangeValues(K key1, K key2) {

    ArrayList<KeyValuePair<K, V>> totalRange = new ArrayList<>(10);
    helper(this.root, key1, key2, totalRange);
    return totalRange;
}


private void helper(BinaryTreeNode<MapEntry<K, V>> node, K elem1, K elem2, ArrayList<KeyValuePair<K, V>> Totalrange) {
    if (node == null) 
        return;
    
    if (keyComparator.compare(node.getValue().getKey(), elem1) >= 0 && keyComparator.compare(node.getValue().getKey(), elem2) < 0) 
        Totalrange.add(node.getValue());
    

    
    if (keyComparator.compare(node.getValue().getKey(), elem1) >= 0) 
        helper(node.getLeftChild(), elem1, elem2, Totalrange);
    


    if (keyComparator.compare(node.getValue().getKey(), elem2) < 0) 
        helper(node.getRightChild(), elem1, elem2, Totalrange);
    
}


	}

}
