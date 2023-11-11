import java.io.PrintStream;


public class P2Wrapper {
	public static interface TreeNode<E> {
		
		public E getValue();

	}

	public static class BinaryTreeNode<E> implements TreeNode<E> {

		private E value;
		private BinaryTreeNode<E> leftChild;
		private BinaryTreeNode<E> rightChild;
		private BinaryTreeNode<E> parent;

		

		public BinaryTreeNode(E value) {
			super();
			this.value = value;
			this.leftChild = null;
			this.rightChild = null;
			this.parent = null;

		}

		
		public BinaryTreeNode(E value, BinaryTreeNode<E> parent, BinaryTreeNode<E> leftChild, BinaryTreeNode<E> rightChild) {
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


		public BinaryTreeNode<E> getLeftChild() {
			return leftChild;
		}


		public void setLeftChild(BinaryTreeNode<E> leftChild) {
			this.leftChild = leftChild;
		}


		public BinaryTreeNode<E> getRightChild() {
			return rightChild;
		}


		public void setRightChild(BinaryTreeNode<E> rightChild) {
			this.rightChild = rightChild;
		}


		public void setValue(E value) {
			this.value = value;
		}


		public BinaryTreeNode<E> getParent() {
			return parent;
		}


		public void setParent(BinaryTreeNode<E> parent) {
			this.parent = parent;
		}

	}

	public static interface SimpleBinaryTree<E> {

		// get tree root
		public TreeNode<E> root();
		
		// get left child of node
		public TreeNode<E> left(TreeNode<E> p);
		
		// get right child of node
		public TreeNode<E> right(TreeNode<E> p);
		
		// get sibling
		public TreeNode<E> sibling(TreeNode<E> p);

		// 
		public boolean isEmpty();
		
		// public int depth(E e);
		public int size();
		
		public boolean verifyCousins(E e1, E e2);



	}	

	public static class SimpleBinaryTreeImp<E> implements SimpleBinaryTree<E> {
		
		private BinaryTreeNode<E> root;
		

		
		public SimpleBinaryTreeImp(BinaryTreeNode<E> root) {
			super();
			this.root = root;
		}
		
		public SimpleBinaryTreeImp(BinaryTreeNode<E> root, 
				SimpleBinaryTree<E> T1, SimpleBinaryTree<E> T2) {
			super();
			this.root = root;
			if (T1 != null) {
				BinaryTreeNode<E> temp = (BinaryTreeNode<E>)T1.root();
				this.root.setLeftChild(temp);
				temp.setParent(this.root);
				
			}
			if (T2 != null) {
				BinaryTreeNode<E> temp = (BinaryTreeNode<E>)T2.root();

				this.root.setRightChild(temp);
				temp.setParent(this.root);

			}

		}



		@Override
		public TreeNode<E> root() {
			return this.root;
		}


		private void check(TreeNode<E> p) {
			if (p==null) {
				throw new IllegalArgumentException();
			}
		}
		@Override
		public TreeNode<E> left(TreeNode<E> p) {
			this.check(p);
			BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
			return temp.getLeftChild();
		}


		@Override
		public TreeNode<E> right(TreeNode<E> p) {
			this.check(p);
			BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
			return temp.getRightChild();

		}

		@Override
		public TreeNode<E> sibling(TreeNode<E> p) {
			this.check(p);
			BinaryTreeNode<E> temp = (BinaryTreeNode<E>)p;
			if (temp.getParent().getLeftChild() != temp) {
				return temp.getRightChild();
			}
			else {
				return temp.getLeftChild();
			}

		}
		
		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public int size() {
			// ADD YOUR CODE HERE
			return this.sizeAux(this.root);

			//return 0;
		}

		private int sizeAux(BinaryTreeNode<E> N) {
			if (N == null) {
				return 0;
			}
			else {
				return 1 + this.sizeAux(N.getLeftChild()) + this.sizeAux(N.getRightChild());
			}
			
		}
	
		
		public void print(PrintStream out) {
			this.printAux(this.root, 0, out);
		}

		private void printAux(BinaryTreeNode<E> N, int i,PrintStream  out) {
			if (N != null) {
				this.printAux(N.getRightChild(), i + 4, out);
				for (int j=0; j < i; ++j) {
					System.err.print(" ");
				}
				System.err.println(N.getValue());
				this.printAux(N.getLeftChild(), i + 4, out);
			}
			
		}
		
		@SuppressWarnings("unused")
		private BinaryTreeNode<E> find(E e, BinaryTreeNode<E> N) {
			if (N == null) {
				return null;
			}
			else if (N.getValue().equals(e)) {
				return N;
			}
			else {
				BinaryTreeNode<E> temp = find(e, N.getLeftChild());
				if (temp != null) {
					return temp;
				}
				else {
					return find(e, N.getRightChild());
				}
	 		}
		}
		//////////////////////////////////////////////////

	

		// For Students
		//
		/*
		 * Two elements e1 and e2 are said to be cousins in a binary tree if they
		 * are located in nodes that are the same level (depth) in a binary tree.
		 * Nodes with the same parent are considered as a special case of cousins.
		 * Write a method  verifyCousins which returns true if two values are cousins
		 * or false otherwise. The values are passed as parameters. Assume that 
		 * the tree has no duplicates, and that e1 and e2 are present in the tree.
		 * (Hint: First write a method to find the depth of a node. 
		 */

        private int recDepth(E value, BinaryTreeNode<E> node, int depth) {
            if (node == null) 
                return -1;
            

            if (node.getValue().equals(value)) 
                return depth;
            

            int leftDepth = recDepth(value, node.getLeftChild(), depth + 1);

            if (leftDepth != -1) 
                return leftDepth;
            
            return recDepth(value, node.getRightChild(), depth + 1);
        }

    
        private BinaryTreeNode<E> helperParent(E value, BinaryTreeNode<E> node) {
            
            if (node == null || (node.getLeftChild() == null && node.getRightChild() == null)) 
                return null;
            

            if ((node.getLeftChild() != null && node.getLeftChild().getValue().equals(value)) || 
                (node.getRightChild() != null && node.getRightChild().getValue().equals(value))) 
                return node;
            

            BinaryTreeNode<E> leftParent = helperParent(value, node.getLeftChild());
            if (leftParent != null) 
                return leftParent;
            
            return helperParent(value, node.getRightChild());
        }

        @Override
        public boolean verifyCousins(E e1, E e2) {
            
            int Q1 = recDepth(e1, root, 0);
            int Q2 = recDepth(e2, root, 0);

            if (Q1!= Q2) 
                return false;
            


            BinaryTreeNode<E> tmp1 = helperParent(e1, root);
            BinaryTreeNode<E> tmp2 = helperParent(e2, root);

            return tmp1 != tmp2;
        }

	}

}