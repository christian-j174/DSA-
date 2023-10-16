package testers;

import java.util.function.Function;

import lists.ArrayList;

public class LambdaTester {
	
	public static void main(String[] args) {
		
		// We create an array of Integers
		// Notice we use type Integer for the List and not int, why is that?
		ArrayList<Integer> L1 = new ArrayList<Integer>(5);
		
		// We add values 1, 2, 3, 4, 5
		L1.add(1);
		L1.add(2);
		L1.add(3);
		L1.add(4);
		L1.add(5);
		
		// We create a List of type String
		ArrayList<String> L2 = new ArrayList<String>(5);
		
		// We add values MEL, AL, WILL, BOB, JIL
		L2.add("MEL");
		L2.add("AL");
		L2.add("WILL");
		L2.add("BOB");
		L2.add("JIL");
		
		// Lambda function that receives an integer x and returns x^2 
		Function<Integer, Integer> F1 = (x) -> x*x;
		// Lambda function that receives a String and returns that string will ALL letters lowercase
		Function<String, String> F2 = (X) -> X.toLowerCase();
		
		/*
		 * Notice that for the functions above we used the Function<T, R> interface. 
		 * 
		 * The Function interface acts as a placeholder type for the lambda function. Remember in Java we must ALWAYS 
		 * specify the TYPE of a variable, otherwise Java won't understand how to store it. This is why we use Functional
		 * Interfaces like Function.
		 * 
		 * Each functional interface has a single method, this method is what gets replaced by the lambda function.
		 * So for example, in the functional interface Function it has the method apply() which returns type R and receives 
		 * a parameter of type T. So when we declare 
		 *          F1 = (x) -> x*x, 
		 * we are essentially saying that the method apply() is now this:
		 *          int apply(int x) { returns x*x; }
		 *          
		 * R and T get replaced with the type assigned in the lambda function and what apply() does is whatever
		 * the lambda function dictates. 
		 * 
		 * Note that for this to work the labmda function must follow the signature expected by the functional
		 * interface, in this case, receives a parameter and returns some value.
		 */
		
		
		// We print the Integer List
		System.out.println("Original content:");
		for(Integer i: L1)
			System.out.println(i);
		// We print the modified List
		System.out.println("Modified to x^2:");
		L1.printModifiedList(F1);
		
		// We print the String List
		System.out.println("Original content:");
		for(String i: L2)
			System.out.println(i);
		// We print the modified List
		System.out.println("Modified to lower case:");
		L2.printModifiedList(F2);
		
		/*
		 * Notice that we used two Lists of different types, Integer and String, and
		 * in both cases the lambda functions worked. Why is that?
		 * 
		 * If we look at the signature of printModifiedList:
		 *       public void printModifiedList(Function<E, E> F)
		 * Notice that it receives a Function of type <E, E>, and the ArrayList also uses type E. 
		 * What does this mean? Remember type E is a placeholder for the actual type of the List,
		 * So if we create an ArrayList<String> that means that E now represents a String. Therefore,
		 * for a List of type String the method printModifiedList() will expect to receive a lambda
		 * function of type <String, String>. 
		 * 
		 * This means that this method is not only generic (can work with any object type), but we can create 
		 * functions outside of the class to manipulate the data no matter the type.
		 */
		
		// We print the Integer List, again.
		System.out.println("Original content:");
		for(Integer i: L1)
			System.out.println(i);
		// We print the modified List using an existing method instead
		System.out.println("Modified to x + 5:");
		L1.printModifiedList(LambdaTester::F3);
		/*
		 * This last one is an example of using an existing method. This is technically not lambda, since it's not 
		 * a throw-away method, but can work if the signature is compatible. This type of declaration can have 
		 * side effect though.
		 * 
		 * What this syntax (LambdaTester::F3) does is that it creates an instance of the method F3 from class
		 * LambdaTester. This is interpreted as x -> LambdaTester.F3(x)
		 * 
		 */
	}
	
	private static int F3(int i) {
		return i + 5;
	}

}
