package testers;

import java.util.function.Function;

import interfaces.List;
import lists.ArrayList;

public class LambdaMain {

	public static void main(String[] args) {
		// Creating an ArrayList
		List<Integer> LL = new ArrayList<Integer>(5);
		LL.add(5);
		LL.add(8);
		LL.add(4);
		LL.add(1);
		LL.add(3);
		System.out.println("Original List!!");
		for(Integer i: LL) {
			System.out.println(i);
		}
		// This function returns a List with all the values in L1 that are larger or equal to 5
		Function<List<Integer>, List<Integer>> F1 = (L1) -> {
			List<Integer> L2 = new ArrayList<Integer>();
			for(Integer i: L1) { 
				if(i >= 5)
					L2.add(i);
			}
				return L2;
			
		};
		// This function returns a List with all the values in L1 that are less than 5
		Function<List<Integer>, List<Integer>> F2 = (L1) -> {
			List<Integer> L2 = new ArrayList<Integer>();
			for(Integer i: L1) {
				if(i < 5)
					L2.add(i);
			}
				return L2;
			
		};
		System.out.println("First function!!");
		printList(LL, F1);
		System.out.println("Secondfunction!!");
		printList(LL, F2);
		
	}
	
	private static void printList(List<Integer> L, Function<List<Integer>, List<Integer>> F) {
		List<Integer> L2 = F.apply(L);
		for(Integer i: L2)
			System.out.println(i);
	}
}
