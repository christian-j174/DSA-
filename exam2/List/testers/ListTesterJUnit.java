package testers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import interfaces.List;
import lists.ArrayList;
import lists.DoublyLinkedList;
import lists.SinglyLinkedList;

public class ListTesterJUnit {
	
	private List<String> testList;
	
	/**
	 * This method will be called before each test method
	 * 
	 * Creates an empty list
	 */
	@Before
	public void setUp() {
		this.testList = new ArrayList<String>();
//		 this.testList = new SinglyLinkedList<String>();
//		this.testList = new DoublyLinkedList<String>();
		this.testList.add("A");
		this.testList.add("B");
	}
	/**
	 * Check if isEmpty works properly. True when empty and false when not.
	 */
	@Test
	public void testIsEmpty() {
		// The default list shouldn't be empty
		boolean notEmpty = this.testList.isEmpty();
		// Create an empty list
		List<String> secondList = new ArrayList<String>();
		// This list should be empty
		boolean isEmpty = secondList.isEmpty();
		
		assertTrue("Method is Empty failed to return correct value.", isEmpty && !notEmpty);
	}
	
	/**
	 * Check if add() works properly, as well as if the list returns the correct size
	 */
	@Test
	public void testAddAndSize() {
		
		assertTrue("Called add(\"A\") and add(\"B\") in that order. Failed test. "
				+ "Possible reasons: Did not add at end of List, size not correct value, or get() not working properly.", 
				this.testList.get(0).equals("A") && this.testList.get(1).equals("B") && this.testList.size() == 2);
	}
	
	/**
	 * Check if add(index, obj) works properly, as well as if the list returns the correct size
	 */
	@Test
	public void testAddIndexAndSize() {
		// Add element at start of list
		this.testList.add(0, "C");
		assertTrue("Called add(\"A\") and add(\"B\"), and add(0, \"C\") in that order. Failed test. "
				+ "Possible reasons: Did not add correctly and List is in incorrect order, size not correct value, or get() not working properly.", 
				this.testList.get(0).equals("C") && this.testList.get(1).equals("A") && this.testList.get(2).equals("B") 
				&& this.testList.size() == 3);
	}
	/**
	 * Check if add(index, obj) works properly we adding at then of list, as well as if the list returns the correct size
	 */
	@Test
	public void testAddIndexAndSize2() {
		// Add elements at start and end of list
		this.testList.add(0, "C");
		this.testList.add(this.testList.size(), "D");
		assertTrue("Called add(\"A\") and add(\"B\"), add(0, \"C\"), and add(list.size(), \"D\") in that order. Failed test. "
				+ "Possible reasons: Did not add correctly and List is in incorrect order, size not correct value, "
				+ "or get() not working properly.", 
				this.testList.get(0).equals("C") && this.testList.get(1).equals("A") && 
				this.testList.get(2).equals("B") && this.testList.get(3).equals("D") && this.testList.size() == 4);
	}
	
	/**
	 * Check if get is working properly
	 */
	@Test
	public void testGet() {
		assertEquals("A", this.testList.get(0), "Did not return correct value at index 0, for list [A, B]");
	}
	/**
	 * Check if get is working properly
	 */
	@Test
	public void testGet2() {
		this.testList.add(0, "C");
		assertEquals("B", this.testList.get(2), "Did not return correct value at index 2, for list [C, A, B]");
	}
	
	/**
	 * Test if remove(index) works properly at position 0
	 */
	@Test
	public void testRemoveIndex() {
		// Add more values
		this.testList.add(0, "C");
		this.testList.add(3, "D");
		assertTrue("Called remove(0) for list [C, A, B, D], and failed to return true. "
				+ "Possible reasons: Did not remove correctly or size isn't correct.", 
				this.testList.remove(0) && this.testList.get(0).equals("A") && this.testList.get(1).equals("B") && 
				this.testList.get(2).equals("D") && this.testList.size() == 3);
	}
	
	/**
	 * Test if remove(index) works properly at end of list
	 */
	@Test
	public void testRemoveIndex2() {
		// Add more values
		this.testList.add(0, "C");
		this.testList.add(3, "D");
		assertTrue("Called remove(3) for list [C, A, B, D], and failed to return true. "
				+ "Possible reasons: Did not remove correctly, list in incorrect order or size isn't correct.", 
				this.testList.remove(3) && this.testList.get(0).equals("C") && this.testList.get(1).equals("A") && 
				this.testList.get(2).equals("B") && this.testList.size() == 3);
	}
	/**
	 * Test if remove(index) works when removing when there is only 1 element left in the list
	 */
	@Test
	public void testRemoveIndex3() {
		// Add more values
		this.testList.remove(0);
		assertTrue("Called remove(0) for list [B], and failed to return true. "
				+ "Possible reasons: Did not remove correctly, list in incorrect order or size isn't correct.", 
				this.testList.remove(0) && this.testList.isEmpty());
	}
	
	/**
	 * Test if remove() works properly for first element on the list
	 */
	@Test
	public void testRemove() {
		// Add more values
		this.testList.add(0, "C");
		this.testList.add(3, "D");
		assertTrue("Called remove(C) for list [C, A, B, D], and failed to return true. "
				+ "Possible reasons: Did not remove correctly or size isn't correct.", 
				this.testList.remove("C") && this.testList.get(0).equals("A") && this.testList.get(1).equals("B") && 
				this.testList.get(2).equals("D") && this.testList.size() == 3);
	}
	
	/**
	 * Test if remove() works properly at end of list
	 */
	@Test
	public void testRemove2() {
		// Add more values
		this.testList.add(0, "C");
		this.testList.add(3, "D");
		assertTrue("Called remove(D) for list [C, A, B, D], and failed to return true. "
				+ "Possible reasons: Did not remove correctly, list in incorrect order or size isn't correct.", 
				this.testList.remove("D") && this.testList.get(0).equals("C") && this.testList.get(1).equals("A") && 
				this.testList.get(2).equals("B") && this.testList.size() == 3);
	}
	/**
	 * Test if remove() works properly when obj isn't present
	 */
	@Test
	public void testRemove3() {
		// Add more values
		this.testList.add(0, "C");
		this.testList.add(3, "D");
		assertTrue("Called remove(Z) for list [C, A, B, D], and failed to return false. "
				+ "Possible reasons: Removed something when it shouldn't, did not return false, "
				+ "list in incorrect order or size isn't correct.", 
				!this.testList.remove("Z") && this.testList.get(0).equals("C") && this.testList.get(1).equals("A") && 
				this.testList.get(2).equals("B") && this.testList.get(3).equals("D") && this.testList.size() == 4);
	}
	/**
	 * Test if remove() works properly when only one object left in list
	 */
	@Test
	public void testRemove4() {
		// Add more values
		this.testList.remove(0);
		assertTrue("Called remove(B) for list [B], and failed to return true. "
				+ "Possible reasons: Removed incorrectly or size isn't correct.", 
				this.testList.remove("B") && this.testList.isEmpty());
	}
	
	/**
	 * Check if contains() works
	 */
	@Test
	public void testContains() {
		assertTrue("Called contains(\"A\") and contains(\"Z\") on list [A, B]. Failed test. "
				+ "Possible reasons: Failed to find A or Failed to return false when searching for Z.", 
				this.testList.contains("A") && !this.testList.contains("Z"));
	}
	/**
	 * Check if clear() works
	 */
	@Test
	public void testClear() {
		this.testList.clear();
		
		assertTrue("Called clear( ) on list [A, B]. Failed test. "
				+ "Possible reasons: Size not 0.", 
				this.testList.isEmpty());
	}
	
	/**
	 * Test the set() method
	 */
	@Test
	public void testSet() {
		this.testList.set(0, "Z");
		assertTrue("Called set(0, \"Z\") on list [A, B]. Failed test. Possible reasons: Did not replace correct value, "
				+ "content in wrong order, or changed size", 
				this.testList.get(0).equals("Z") && this.testList.get(1).equals("B") && this.testList.size() == 2);
	}
	/**
	 * Test the removeAll() method
	 */
	@Test
	public void testRemoveAll() {
		this.testList.add("L");
		this.testList.add(0, "L");
		this.testList.add("K");
		this.testList.add("L");
		this.testList.add(0, "Z");
		int count = this.testList.removeAll("L");
		assertTrue("Called removeAll(\"L\") on list [Z, L, A, B, L, K, L]. Failed test. "
				+ "Possible reasons: List in incorrect order, list has wrong size, did not return 3", 
				count == 3 && this.testList.get(0).equals("Z") && this.testList.get(1).equals("A") && 
				this.testList.get(2).equals("B") && this.testList.get(3).equals("K") && this.testList.size() == 4);
	}
	
	/**
	 * Test the removeAll() method when there is stuff to remove at position 0
	 */
	@Test
	public void testRemoveAll2() {
		this.testList.add("L");
		this.testList.add(0, "L");
		this.testList.add("K");
		this.testList.add("L");
		this.testList.add(0, "Z");
		this.testList.add(0, "L");
		this.testList.add(0, "L");
		int count = this.testList.removeAll("L");
		assertTrue("Called removeAll(\"L\") on list [L, L, Z, L, A, B, L, K, L]. Failed test. "
				+ "Possible reasons: List in incorrect order, list has wrong size, did not return 3", 
				count == 5 && this.testList.get(0).equals("Z") && this.testList.get(1).equals("A") && 
				this.testList.get(2).equals("B") && this.testList.get(3).equals("K") && this.testList.size() == 4);
	}
	
	/**
	 * Testing firstIndex(index)
	 */
	@Test
	public void testFirstIndex() {
		this.testList.add("L");
		this.testList.add(0, "L");
		this.testList.add("K");
		this.testList.add("L");
		this.testList.add(0, "Z");
		assertTrue("Called firstIndex(\"L\") on list [Z, L, A, B, L, K, L]. Failed test. "
				+ "Possible reasons: did not return 1", 
				this.testList.firstIndex("L") == 1);
	}
	
	/**
	 * Testing lastIndex(index)
	 */
	@Test
	public void testLastIndex() {
		this.testList.add("L");
		this.testList.add(0, "L");
		this.testList.add("K");
		this.testList.add("L");
		this.testList.add(0, "Z");
		assertTrue("Called lastIndex(\"L\") on list [Z, L, A, B, L, K, L]. Failed test. "
				+ "Possible reasons: did not return 6", 
				this.testList.lastIndex("L") == 6);
	}
	
	@Test
	public void testFirst() {
		this.testList.add("Z");
		this.testList.add(0, "L");
		assertTrue("Called first() on list [L, A, B, Z]. Failed test.", this.testList.first().equals("L"));
	}
	@Test
	public void testLast() {
		this.testList.add("Z");
		this.testList.add(0, "L");
		assertTrue("Called last() on list [L, A, B, Z]. Failed test.", this.testList.last().equals("Z"));
	}

}
