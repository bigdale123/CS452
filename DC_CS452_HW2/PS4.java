import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Sidharth
 */
public class PS4 {
	
	/**
     * ***********************************************************************************************
     *
     * PROBLEM SET 4 (20 Points)
     *
	 * Given an array, maintain the relative order of the elements and move the zeroes to
	 * the left, if there are any zeroes and all the ones to the right if there are any.
	 * Return the modified array.
     *
	 * Implementation should run in O(n) running time
	 * Example 1:
	 * Input : [1, 0, 6, 9, 0, 15]
	 * Output: [0, 0, 6, 9, 15, 1]
	 *
	 * Example 2:
     * Input : [0, 0, 15, 0, 1, 11]
	 * Output : [0, 0, 0, 15, 11, 1]
     * 
     * 10 Points for correct code
     ************************************************************************************************
     */
	public int[] moving_Numbers_linear(int[] nums) {
        int[] edited_nums = new int[nums.length];
        int[] result = new int[nums.length];
        int left_pointer = 0;
        int right_pointer = nums.length-1;
        int edited_nums_len = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == 0){
                result[left_pointer] = 0;
                left_pointer++;
            }
            else if(nums[i] == 1){
                result[right_pointer] = 1;
                right_pointer--;
            }
            else{
                edited_nums[edited_nums_len] = nums[i];
            }

        }
        int j = 0;
        for(int i = left_pointer; i < right_pointer; i++){
            result[i] = edited_nums[j];
        }
        return result;
    }
	
	/**
     * ***********************************************************************************************
     * 
     * runningDoubleSum: double sum for an array arr is defined as: runningDoubleSum[i] = 2 * sum(nums[0]…nums[i])
     * Implement a function that return the last element of runningDoubleSum array.
     * Input: arr = [0,1,2,3] Output: 12, as runningDoubleSum is [0,2,6,12]
     *
     * 10 Points for correct implementation
     ************************************************************************************************
     */
    public long runningDoubleSum(int arr[]) {
        int arr2[] = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr.length; j++){
                arr2[i] += arr[j];
            }
            arr2[i] *= 2;
        }
        return arr2[arr2.length-1];
    }


    public static void main(String args[]){

        PS4 ob = new PS4();
        for (int j = 0; j < 4; j++) {
            try {
				String filename = "PS4/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                }
                long start_p1 = System.nanoTime();
                int[] temp1 = ob.moving_Numbers_linear(array1);
                long end_p1 = System.nanoTime();

                System.out.println("Linear\t" + (end_p1 - start_p1));
    
                long last_element = ob.runningDoubleSum(array1);
                System.out.println("Last element of running sum " + last_element);

                array1 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }
}
