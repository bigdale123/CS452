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
public class PS3 {


	
	/**
     * ***********************************************************************************************
     *
     * PROBLEM SET 3 (15 Points)
     *
     * Given the array of integers numbers:
     * check if the numbers of the array are arraned as a min-heap;
	 * if the array is a min-heap then return (1, the minimum element of the array) 
     * Otherwise, return (0, the maximum element of the array)
     *
	 * Example 1:
	 * Input : [1, 8, 11, 12, 14, 15]
	 * Output : 1, 1
	 * Explanation: The array is a min-heap, so we have to return the minimum element of the array
     *
	 * Example 2:
     * Input : [8, 4, 11, 12, 1, 15]
	 * Output : 15
     * Explanation: The array is not a min-heap, so we have to return the maximum element of the array
     * 
     * 10 points for correct code
     ************************************************************************************************
     */
    public int[] check_MinHeap_and_return_min_or_max(int[] nums) {
		int ans[] = new int[2];
        ans[0] = 0;
        ans[1] = 0;
        for(int i = 0; i <= ((nums.length-2)/2); i++){
            if(nums[i*2+1] < nums[i]){
                ans[0] = 0;
                Arrays.sort(nums);
                ans[1] = nums[nums.length-1];
                return ans;
            }
            if(i*2+2 < nums.length && nums[i*2+2] < nums[i]){
                ans[0] = 0;
                Arrays.sort(nums);
                ans[1] = nums[nums.length-1];
                return ans;
            }
        }
        ans[0] = 1;
        ans[1] = nums[0];
        return ans;
    }
	

	 

    public static void main(String args[]){

        PS3 ob = new PS3();
		
		// Problem set 3
        for (int j = 0; j < 4; j++) {
            try {
                String filename = "PS3/input_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                }

                int result[] = ob.check_MinHeap_and_return_min_or_max(array1);
                if (result[0] == 1)
                    System.out.println("[Min Heap] Minimum " + result[1]);
                if (result[0] == 0)
                    System.out.println("[Not a min Heap] Maximum " + result[1]);
				

                array1 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }
}
