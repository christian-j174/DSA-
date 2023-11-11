package BST;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class MinAbsDifferenceWrapper {

	public static class BTNode<K, V> implements PrintableNode {
		private K key;
		private V value;
		private BTNode<K, V> leftChild, rightChild, parent;

		public BTNode() {
			this(null, null, null);
		}
		public BTNode(K key, V value) {
			this(key, value, null);
		}

		public BTNode(K key, V value, BTNode<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.leftChild = this.rightChild = null;
		}

		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public BTNode<K, V> getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(BTNode<K, V> leftChild) {
			this.leftChild = leftChild;
		}
		public BTNode<K, V> getRightChild() {
			return rightChild;
		}
		public void setRightChild(BTNode<K, V> rightChild) {
			this.rightChild = rightChild;
		}
		public BTNode<K, V> getParent() {
			return parent;
		}
		public void setParent(BTNode<K, V> newParent) {
			parent = newParent;
		}
		public void clear() {
			key = null;
			value = null;
			leftChild = rightChild = parent = null;
		}
		@Override
		public PrintableNode getLeft() {
			return getLeftChild();
		}
		@Override
		public PrintableNode getRight() {
			return getRightChild();
		}
		@Override
		public String getText() {
			return key.toString() + ":" + value.toString();
		}

	}

	public static interface PrintableNode {
		PrintableNode getLeft();
		PrintableNode getRight();
		String getText();
	}

	public static class BinaryTreePrinter {

		public static void print(PrintableNode root) {
			List<List<String>> lines = new ArrayList<List<String>>();

			List<PrintableNode> level = new ArrayList<PrintableNode>();
			List<PrintableNode> next = new ArrayList<PrintableNode>();

			level.add(root);
			int nn = 1;

			int widest = 0;

			while (nn != 0) {
				List<String> line = new ArrayList<String>();

				nn = 0;

				for (PrintableNode n : level) {
					if (n == null) {
						line.add(null);

						next.add(null);
						next.add(null);
					} else {
						String aa = n.getText();
						line.add(aa);
						if (aa.length() > widest) widest = aa.length();

						next.add(n.getLeft());
						next.add(n.getRight());

						if (n.getLeft() != null) nn++;
						if (n.getRight() != null) nn++;
					}
				}

				if (widest % 2 == 1) widest++;

				lines.add(line);

				List<PrintableNode> tmp = level;
				level = next;
				next = tmp;
				next.clear();
			}

			int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
			for (int i = 0; i < lines.size(); i++) {
				List<String> line = lines.get(i);
				int hpw = (int) Math.floor(perpiece / 2f) - 1;

				if (i > 0) {
					for (int j = 0; j < line.size(); j++) {

						// split node
						char c = ' ';
						if (j % 2 == 1) {
							if (line.get(j - 1) != null) {
								c = (line.get(j) != null) ? '┴' : '┘';
							} else {
								if (j < line.size() && line.get(j) != null) c = '└';
							}
						}
						System.out.print(c);

						// lines and spaces
						if (line.get(j) == null) {
							for (int k = 0; k < perpiece - 1; k++) {
								System.out.print(" ");
							}
						} else {

							for (int k = 0; k < hpw; k++) {
								System.out.print(j % 2 == 0 ? " " : "─");
							}
							System.out.print(j % 2 == 0 ? "┌" : "┐");
							for (int k = 0; k < hpw; k++) {
								System.out.print(j % 2 == 0 ? "─" : " ");
							}
						}
					}
					System.out.println();
				}

				// print line of numbers
				for (int j = 0; j < line.size(); j++) {

					String f = line.get(j);
					if (f == null) f = "";
					int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
					int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

					// a number
					for (int k = 0; k < gap1; k++) {
						System.out.print(" ");
					}
					System.out.print(f);
					for (int k = 0; k < gap2; k++) {
						System.out.print(" ");
					}
				}
				System.out.println();

				perpiece /= 2;
			}
		}
	}
	
	public static int getMinimumDifference(BTNode<Integer, Integer> root) {		
		List<Integer> values = new ArrayList<>();
		recTravel(root, values);

		int min = Integer.MAX_VALUE;

		for(int i = 0; i < values.size() -1; i++){
			int diff = values.get(i + 1) - values.get(i);
			min = Math.min(min,diff);
		}

		return min;
	}

	private static void recTravel(BTNode<Integer, Integer> node, List<Integer> values) {
		if(node == null)
			return;

		recTravel(node.getLeftChild(), values);
		values.add(node.getKey());
		recTravel(node.getRightChild(), values);
	}
}