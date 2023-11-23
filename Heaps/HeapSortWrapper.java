package Heaps;

public class HeapSortWrapper {
	
    public static int[] heapSort(int[] arr) {
        int container = arr.length;

        for (int i = container / 2 - 1; i >= 0; i--)
            helperHeap(arr, container, i);

        
        for (int j = container - 1; j > 0; j--) {
           
            int temp = arr[0];
            arr[0] = arr[j];
            arr[j] = temp;

            
            helperHeap(arr, j, 0);

        }

        return arr;
    }
	
    static void helperHeap(int[] arg1, int n, int i) {

        int bigest = i;
		int right = (2 * i) + 2; 
        int left = (2 * i) + 1; 
        

        
        if (left < n && arg1[left] > arg1[bigest])
            bigest = left;

       
        if (right < n && arg1[right] > arg1[bigest])
            bigest = right;

       
        if (bigest != i) {
            int swap = arg1[i];
            arg1[i] = arg1[bigest];
            arg1[bigest] = swap;


            helperHeap(arg1, n, bigest);
        }
    }
}
