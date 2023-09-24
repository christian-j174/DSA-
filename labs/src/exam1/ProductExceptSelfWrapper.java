package exam1;

public class ProductExceptSelfWrapper {
	
	public static int[] productExceptSelf(int[] nums) {
		// creamos el array 
		int n = nums.length;
		int[] result = new int[n];
		
		// calculamos el producto de all elems to the left of each elems
		
		int leftProduct = 1;
		for(int i = 0; i < n; i++){
			result[i] = leftProduct;
			leftProduct *= nums[i];
		}
		
		// Calculate the product of all elements to the right of each element
		
		int rightProduct = 1;
		for(int i = n -1; i >= 0; i--) {
			
			result[i] *= rightProduct;
			rightProduct *= nums[i];
		}
		
		return result;
    }

}