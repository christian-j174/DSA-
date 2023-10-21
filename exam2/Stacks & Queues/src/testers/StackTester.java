package testers;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import interfaces.Stack;
import stacks.ArrayListStack;
import stacks.LinkedListStack;
import stacks.LinkedStack;

public class StackTester {
	
	Stack<String> stack;
	
	@Before
	public void setUp() {
//		stack = new ArrayListStack<>();
//		stack = new LinkedListStack<>();
		stack = new LinkedStack<>();
	}
	
	/*
	 * Check if isEmpty works
	 */
	@Test
	public void test1() {
		assertTrue("FAILED: Stack should be empty, nothing has been added.", stack.isEmpty());
	}
	/*
	 * Check if push and pop work
	 */
	@Test
	public void test2() {
		stack.push("Ben");
		stack.push("Jil");
		stack.push("Ron");
		assertTrue("FAILED: Stack failed to insert Ben, Jil, Ron in that order.\n Or remove them in the reverse other.", 
				!stack.isEmpty()&& stack.size() == 3 && stack.pop().equals("Ron") && stack.pop().equals("Jil") 
				&& stack.pop().equals("Ben"));
	}
	/*
	 * Check if push and pop work
	 */
	@Test
	public void test3() {
		stack.push("Ben");
		stack.push("Jil");
		stack.push("Ron");
		stack.push("Ken");
		stack.pop();
		stack.pop();
		assertTrue("FAILED: Stack failed to return Jil after inserting Ben, Jil, Ron and Ken in that order and colling pop() twice.", 
				!stack.isEmpty()&& stack.size() == 2 && stack.top().equals("Jil"));
	}
	/*
	 * Check if clear() works
	 */
	@Test
	public void test4() {
		stack.push("Ben");
		stack.push("Jil");
		stack.push("Ron");
		stack.push("Ken");
		stack.clear();
		assertTrue("FAILED: Stack should be empty after clear()", stack.isEmpty());
	}
	/*
	 * Silly test to see if all working
	 */
	@Test
	public void test5() {
		stack.push("Jil");
		stack.push(stack.top());
		stack.push("Ron");
		stack.push(stack.pop());
		stack.push("Ken");
		assertTrue("FAILED: Did not have Stack with values Ken, Ron, Jil, Jil after calling: push(\"Jil\"), push(top())"
				+ " push(\"Ron\"), push(pop()), and Push(\"Ken\") ", stack.size() == 4 && 
				stack.pop().equals("Ken")&&stack.pop().equals("Ron") && stack.pop().equals("Jil") 
				&& stack.pop().equals("Jil"));
	}
	

}
