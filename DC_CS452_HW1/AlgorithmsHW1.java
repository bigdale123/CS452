import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.lang.Object;
import java.util.Random;
import java.util.Arrays;

/**
 *
 * @author sid
 */
public class AlgorithmsHW1 {

    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 1
     * Implement quick_sort_pick_pivot_randomly, where you pick the pivot randomly
     * Implement quick_sort_pick_first_element_as_pivot, where you always pick the first element of the array as the pivot
     * Implement mergesort
     * 
     * Plot a graph to show the runtime for both insertionsort and mergesort for different input sizes (in folder PS1)
     *
     * Correct code 10 + 10 + 10 points
     * Graph 5 points
     * 5 Points: Give reason, why for same graph the two version of quicksort takes signifantly different time
     ************************************************************************************************
     */
    private int RightPartition(int array[], int low, int high){
        int x = array[high];
        int i = low - 1;
        int temp_val = 0;
        for(int j = low; j < high; j++){
            if(array[j] <= x){
                i++;
                temp_val = array[i];
                array[i] = array[j];
                array[j] = temp_val;
            }
        }
        temp_val = array[i+1];
        array[i+1] = array[high];
        array[high] = temp_val;
        return i+1;
    }

    private int RandomizedPartition(int[] array, int low, int high){
        Random rand = new Random();
        int pivot = rand.nextInt(high-low)+low;
        int temp_val = array[low];
        array[low] = array[pivot];
        array[pivot] = temp_val;
        return RightPartition(array, low, high);
    }

    private void quicksortRandomHelper(int[] array, int low, int high){
        if(low < high){
            int q = RandomizedPartition(array, low, high);
            quicksortRandomHelper(array, low, q-1);
            quicksortRandomHelper(array, q+1, high);
        }
        return;
        //return array;
    }

    private void quicksortFirstHelper(int[] array, int low, int high){
        //System.out.println(Arrays.toString(array));
        if(low < high){
            int q = RightPartition(array, low, high);
            quicksortFirstHelper(array, low, q-1);
            quicksortFirstHelper(array, q+1, high);
        }
        return;
    }

    public void quick_sort_pick_pivot_randomly(int arr[]) {
        quicksortRandomHelper(arr, 0, arr.length-1);
    }

    public void quick_sort_pick_first_element_as_pivot(int arr[]) {
        int temp_val = arr[0];
        arr[0] = arr[arr.length-1];
        arr[arr.length-1] = arr[0];
    	quicksortFirstHelper(arr, 0, arr.length-1);
    }

    private void Merge(int[] L, int[] R, int[] arr){
        int i = 0;
        int j = 0;
        int k = 0;
        while(i<L.length && j < R.length){
            if(L[i] <= R[j]){
                arr[k]=L[i];
                i++;
                k++;
            }
            else{
                arr[k]=R[j];
                j++;
                k++;
            }
        }
        while(i<L.length){
            arr[k]=L[i];
            i++;
            k++;
        }
        while(j<R.length){
            arr[k]=R[j];
            j++;
            k++;
        }
    }

    public void mergesort(int arr[]) {
    	if(arr.length > 0){
            int midpoint = arr.length/2;
            int[] L = Arrays.copyOfRange(arr,0,midpoint);
            int[] R = Arrays.copyOfRange(arr,midpoint,arr.length);
            if(arr.length>1 && arr.length>2){
                mergesort(L);
                mergesort(R);
            }
            Merge(L,R,arr);
        }
    }

    public boolean verifySortedness(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 2
     *
     * Given the array of integers numbers, Return the minimum possible value of
     * (nums[i])*(nums[j]); such that i != j
     *
     * Implementation 1 minProduct_quadratic must run in O(n^2) running time
     * Implementation 2 minProduct_nlogn must run in O(n log n) running time
     * 
     * Plot a graph to show the runtime for all two implementations for different input sizes (in folder PS2)
     *
     * Correct code 10 + 10 points
     * Graph 5 points
     *
     ************************************************************************************************
     */
    public int minProduct_quadratic(int[] nums) {
        for(int i = 0; i < nums.length; i++){
            int insert_val = nums[i];
            int empty_val = i;
            while(empty_val > 0 && nums[empty_val-1] > insert_val){
                nums[empty_val] = nums[empty_val-1];
                empty_val--;
            }
            nums[empty_val] = insert_val;
        }
        return nums[0]*nums[1];
    }

    public int minProduct_nlogn(int[] nums) {
        mergesort(nums);
        //System.out.println(nums[0]*nums[1]);
        return nums[0]*nums[1];
    }


    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 3 
     * 
     * Given an integer array numbers sorted in non-decreasing order, 
     * Implement a function squaresSorted that returns an array of the
     * squares of each number sorted in non-decreasing order
     *
     * Implement a function cubesSorted that returns an array of the cubes of
     * each number sorted in non-decreasing order
     * 
     * Plot a graph to show the runtime for these two implementations for different input sizes (in folder PS3)
     *
     * Correct code 10 + 10 points
     * Graph 5 points
     ************************************************************************************************
     */
    public int[] powerSort(int[] numbers, int power){
        mergesort(numbers);
        for(int i = 0; i < numbers.length; i++){
            Math.pow(numbers[i], power);
        } 
        return numbers;
    }
    
    public int[] squaresSorted(int[] numbers) {
        powerSort(numbers, 2);
        return numbers;
    }

    public int[] cubesSorted(int[] numbers) {
        powerSort(numbers, 3);
        return numbers;
    }


    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 4 
     * 
     * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is
     * distinct.
     *
     * Implementation 1 (duplicateFinder_nlogn) should run in O(n logn) running
     * time Implementation 2 (duplicateFinder_quadratic) should run in O(n^2) running time
     *
     * You are not allowed to use any builtin function or data structure
     * 
     * Plot a graph to show the runtime for these two implementations for different input sizes (in folder PS4)
     *
     * Correct code 10 + 10 points
     * Graph 5 points
     ************************************************************************************************
     */
    public boolean duplicateFinder_nlogn(int[] array) {
        mergesort(array);
        for(int i = 0; i < array.length-1; i++){
            if(array[i] == array[i+1]){
                return true;
            }
        }
        return false;
    }

    public boolean duplicateFinder_quadratic(int[] array) {
    	for(int i = 0; i < array.length; i++){
            for(int j = i + 1; j  < array.length; j++){
                if(array[i] == array[j]){
                    return true;
                }
            }
        }
        return false;
    }

       

    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 5 
     * 
     * Given an array nums. We define a running sum of an array as runningSum[i] = sum(nums[0]…nums[i]).
     * Return the running sum of nums.
     *
     * Implementation 1 (runningSum_linear) should run in O(n) running time
     * Implementation 2 (runningSum_quadratic) should run in O(n^2) running time
     * 
     * Plot a graph to show the runtime for these two implementations for different input sizes (in folder PS4)
     *
     * Correct code 10 + 10 points
     * Graph 5 points
     ************************************************************************************************
     */
    public int[] runningSum_linear(int[] nums) {
        int arr[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            if(i > 0){
                arr[i] = nums[i] + arr[i-1];
            }
            else{
                arr[i] = nums[i];
            }
        }
        return arr;
    }
    public int[] runningSum_quadratic(int[] nums) {
        int arr[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            for(int j = 0; j <= i; j++){
                arr[i] += nums[j];
            }
        }
        return arr;
    }

    /**
     * ***********************************************************************************************
     *
	 * PROBLEM SET 6
	 * Given the array nums consisting of 2n elements in the form [x1,x2,...,xn,y1,y2,...,yn].
	 * Return the array in the form [x1,y1,x2,y2,...,xn,yn].
	 * 
     * Implementation 1 (shuffle_linear) should run in O(n) running time
	 * Correct code 10 points
	 ************************************************************************************************
     */
	 public int[] shuffle_linear(int[] nums) {
        int arr[] = new int[nums.length];
        int i = 0;
        int j = nums.length/2;
        for(int x = 0; x < arr.length; x+=2){
            arr[i] = nums[i];
            arr[i+1] = nums[j];
        }
        return arr;        
    }



    public static void main(String[] args) throws IOException {

        AlgorithmsHW1 ob = new AlgorithmsHW1();

        System.out.println();
        System.out.println("---------------Problem Set 1--------------------");

        // Problem set 1
        for (int j = 0; j < 4; j++) {
            try {
                String filename = "PS1/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                int[] array2 = new int[allLines.size()];
                int[] array3 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                    array2[i] = Integer.parseInt(allLines.get(i));
                    array3[i] = Integer.parseInt(allLines.get(i));
                }

                long start_qs1 = System.nanoTime();
                ob.quick_sort_pick_pivot_randomly(array1);
                long end_qs1 = System.nanoTime();

                long start_qs2 = System.nanoTime();
                ob.quick_sort_pick_first_element_as_pivot(array2);
                long end_qs2 = System.nanoTime();

                long start_ms = System.nanoTime();
                ob.mergesort(array3);
                long end_ms = System.nanoTime();

                System.out.println("Quicksort sort (Random)          :\t" + array1.length + "\t" + (end_qs1 - start_qs1));
                System.out.println("Quicksort sort (first element)   :\t" + array1.length + "\t" + (end_qs2 - start_qs2));
                System.out.println("Mergesort                        :\t" + array2.length + "\t" + (end_ms - start_ms));

                if (ob.verifySortedness(array1) != true) {
                    System.out.println("Alert!!! Quick sort (random) is not working correctly for " + filename);
                }

                if (ob.verifySortedness(array2) != true) {
                    System.out.println("Alert!!! Quick sort (first element as pivot) is not working correctly for " + filename);
                }

                if (ob.verifySortedness(array3) != true) {
                    System.out.println("Alert!!! Mergesort is not working correctly for " + filename);
                }

                array1 = null;
                array2 = null;
                array3 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("---------------Problem Set 2--------------------");
        

        // Problem set 2
        for (int j = 0; j < 4; j++) {
            try {
                String filename = "PS2/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                int[] array2 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                    array2[i] = Integer.parseInt(allLines.get(i));
                }

                long start_p1 = System.nanoTime();
                int p1 = ob.minProduct_quadratic(array1);
                long end_p1 = System.nanoTime();

                long start_p2 = System.nanoTime();
                int p2 = ob.minProduct_nlogn(array2);
                long end_p2 = System.nanoTime();


                System.out.println("Quadratic\t" + array1.length + "\t" + (end_p1 - start_p1));
                System.out.println("log-linear\t" + array2.length + "\t" + (end_p2 - start_p2));

                
                if (p1 != p2) {
                    System.out.println("Soft fail");
                }

                array1 = null;
                array2 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println();
        System.out.println("---------------Problem Set 3--------------------");

        
        // Problem set 3
        for (int j = 0; j < 4; j++) {
            try {
                String filename = "PS3/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                int[] array2 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                    array2[i] = Integer.parseInt(allLines.get(i));
                }
                ob.mergesort(array1);
                ob.mergesort(array2);

                long start_p1 = System.nanoTime();
                int[] res1 = ob.squaresSorted(array1);
                long end_p1 = System.nanoTime();

                long start_p2 = System.nanoTime();
                int[] res2 = ob.cubesSorted(array2);
                long end_p2 = System.nanoTime();

                System.out.println("Squared\t" + array1.length + "\t" + (end_p1 - start_p1));
                System.out.println("Cubed\t" + array1.length + "\t" + (end_p2 - start_p2));

                if (ob.verifySortedness(res1) != true) {
                    System.out.println("squares are not sorted for for " + filename);
                }

                if (ob.verifySortedness(res2) != true) {
                    System.out.println("cubes are not sorted for for " + filename);
                }

                array1 = null;
                array2 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("---------------Problem Set 4--------------------");
        
        // Problem set 4
        for (int j = 0; j < 5; j++) {
            try {
                String filename = "PS4/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                int[] array2 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                    array2[i] = Integer.parseInt(allLines.get(i));
                }

                long start_p1 = System.nanoTime();
                boolean res1 = ob.duplicateFinder_nlogn(array1);
                long end_p1 = System.nanoTime();

                long start_p2 = System.nanoTime();
                boolean res2 = ob.duplicateFinder_quadratic(array2);
                long end_p2 = System.nanoTime();

                System.out.println("Quadratic\t" + array1.length + "\t" + (end_p2 - start_p2));
                System.out.println("log-linear\t" + array2.length + "\t" + (end_p1 - start_p1));

                if (res1 != res2) {
                    System.out.println("Soft fail");
                }

                array1 = null;
                array2 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("---------------Problem Set 5--------------------");

        // Problem set 5
        for (int j = 0; j < 5; j++) {
            try {
                String filename = "PS5/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                int[] array2 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                    array2[i] = Integer.parseInt(allLines.get(i));
                }

                long start_is = System.nanoTime();
                int[] linear_result = ob.runningSum_linear(array1);
                long end_is = System.nanoTime();

                long start_ms = System.nanoTime();
                int[] quadratic_result = ob.runningSum_quadratic(array2);
                long end_ms = System.nanoTime();

                if (linear_result[linear_result.length-1] == quadratic_result[quadratic_result.length-1]){
                    System.out.println("The last element of runningSum_linear\t\t" +  + linear_result.length + "\t" + linear_result[linear_result.length-1] + "\t" + (end_is - start_is));
                    System.out.println("The last element of runningSum_quadratic\t" + quadratic_result.length + "\t" + quadratic_result[quadratic_result.length-1] + "\t" + (end_ms - start_ms));
                }else{
                    System.out.println("Wrong answer");
                }                
                
                array1 = null;
                array2 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("---------------Problem Set 6--------------------");

	 	// Problem set 6
         for (int j = 0; j < 5; j++) {
            try {
                String filename = "PS6/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                
                }
				// Write the code to print the shuffled array
                // Comment your code, so that you do not print large arrays to the console
                // System.out.println(Arrays.toString(array1))
				
			} catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
