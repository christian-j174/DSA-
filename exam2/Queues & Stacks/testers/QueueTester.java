package testers;

import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import interfaces.Queue;
import queues.CircularArrayQueue;
import queues.ListQueue;

public class QueueTester {
	
	Queue<String> queue;
	
	@Before
	public void setUp() {
		queue = new CircularArrayQueue<String>();
//		queue = new ListQueue<String>();
	}
	/*
	 * Testing enqueue and dequeue of 3 items
	 */
	@Test
	public void test1() {
		queue.enqueue("Bob");
		queue.enqueue("Ned");
		queue.enqueue("Ben");
		
		assertTrue("Failed to enqueue and then dequeue {Bob, Ned, Ben} properly", 
				queue.size() == 3 && queue.dequeue().equals("Bob") && queue.dequeue().equals("Ned")
				&& queue.dequeue().equals("Ben"));
	}
	/*
	 * Testing isEmpty()
	 */
	@Test
	public void test2() {
		assertTrue("Failed to return true when queue is empty.", queue.isEmpty());
	}
	/*
	 * Testing dequeue when empty
	 */
	@Test(expected = NoSuchElementException.class)
	public void test3() {
		queue.dequeue();
		
	}
	/*
	 * Testing front when empty
	 */
	@Test(expected = NoSuchElementException.class)
	public void test4() {
		queue.front();
	}
	/*
	 * Testing enqueue with null value
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test5() {
		queue.enqueue(null);
	}
	/*
	 * Testing front after adding 3 elements
	 */
	@Test
	public void test6() {
		queue.enqueue("Bob");
		queue.enqueue("Ned");
		queue.enqueue("Ben");
		assertTrue("Failed to return Bob when calling front() for q = {Bob, Ned, Ben}", 
				queue.size() == 3 && queue.front().equals("Bob"));
	}
	/*
	 * Testing clear
	 */
	@Test
	public void test7() {
		queue.enqueue("Bob");
		queue.enqueue("Ned");
		queue.enqueue("Ben");
		queue.clear();
		assertTrue("Failed to empty queue after calling clear() for q = {Bob, Ned, Ben}", 
				queue.isEmpty());
	}

}
