package testers;


import interfaces.List;
import lists.ArrayList;
import lists.DoublyLinkedList;
import lists.SinglyLinkedList;

public class ListTesterMain {
	
	public static void main(String args[]) {
//		List<String> list1 = new ArrayList<String>(5);
//		List<String> list1 = new SinglyLinkedList<String>();
		List<String> list1 = new DoublyLinkedList<String>();


		System.out.println("New list was created.");
		System.out.println("Is the list empty?: " + list1.isEmpty());
		
		System.out.println("-------------------------------");
		
		System.out.println("Adding element A");
		list1.add("A");
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);
		System.out.println("Is the list empty?: " + list1.isEmpty());

		System.out.println("-------------------------------");
		
		System.out.println("Adding element B and C");
		list1.add("B");
		list1.add("C");
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);

		System.out.println("-------------------------------");
		
		System.out.println("Adding element V at position 0 and Z at position 2");
		list1.add(0, "V");
		list1.add(2, "Z");
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);
		
		System.out.println("-------------------------------");
		
		System.out.println("Checking if list contains V and R");
		System.out.println("Contains V?: " + list1.contains("V"));
		System.out.println("Contains R? " + list1.contains("R"));
		
		System.out.println("-------------------------------");
		
		System.out.println("Removing A");
		list1.remove("A");
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);
		
		System.out.println("-------------------------------");
		
		System.out.println("Removing position 0 (V)");
		list1.remove(0);
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);
		
		System.out.println("-------------------------------");
		
		System.out.println("Adding 3 L (no particular order), should call reallocate() if it's an ArrayList");
		list1.add("L");
		list1.add(0, "L");
		list1.add(2, "L");
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);
		
		System.out.println("-------------------------------");
		
		System.out.println("Check index of L (multiple in list)");		
		System.out.println("First Index of L?: " + list1.firstIndex("L"));
		System.out.println("Last Index of L? " + list1.lastIndex("L"));
		System.out.println("-------------------------------");
		System.out.println("Check index of C (only 1 in list)");	
		System.out.println("First Index of C?: " + list1.firstIndex("C"));
		System.out.println("Last Index of C? " + list1.lastIndex("C"));
		System.out.println("-------------------------------");
		System.out.println("Check index of V and R (not in list)");	
		System.out.println("First Index of V?: " + list1.firstIndex("V"));
		System.out.println("Last Index of R? " + list1.lastIndex("R"));
		
		System.out.println("-------------------------------");
		
		System.out.println("Remove all L");
		System.out.println("We have removed " + list1.removeAll("L") + " elements");
		System.out.println("List size: " + list1.size());
		System.out.println("List: " + list1);
		
		System.out.println("-------------------------------");
		
		System.out.println("Return first element");
		System.out.println("First element is: " + list1.first());
		System.out.println("Return last element");
		System.out.println("Last element is: " + list1.last());
		
		System.out.println("-------------------------------");
		
		System.out.println("Empty list, call clear()");
		list1.clear();
		System.out.println("Is the list empty?: " + list1.isEmpty());
		System.out.println("List: " + list1);
	}

}
