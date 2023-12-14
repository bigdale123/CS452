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
public class PS5 {
	
/**
     * ***********************************************************************************************
     *
     * PROBLEM SET 5  (15 Points)
     * Q1_median_of_sorted_Array: given two sorted arrays arr1 and arr2, return the median of the two sorted arrays combined
     * 
     * 10 points for correct code
     ************************************************************************************************
     */
    public int Q1_median_of_sorted_Array(int arr1[], int arr2[]) {
        int[] total_array = new int[arr1.length+arr2.length];
        int k = 0;
        for(int i = 0; i < arr1.length; i++){
            total_array[k] = arr1[i];
            k++;
        }
        for(int j = 0; j < arr2.length; j++){
            total_array[k] = arr2[j];
            k++;
        }
        Arrays.sort(total_array);
        return total_array[(total_array.length-1)/2];
    }
	 

    public static void main(String args[]){

        PS5 ob = new PS5();

        for (int j = 0; j < 4; j++) {
            try {
                String filename1 = "PS5/input_" + j + "_0.txt";
                String filename2 = "PS5/input_" + j + "_1.txt";

                List<String> allLines1 = Files.readAllLines(Paths.get(filename1));
                List<String> allLines2 = Files.readAllLines(Paths.get(filename2));
                int[] array1 = new int[allLines1.size()];
                int[] array2 = new int[allLines2.size()];
                for (int i = 0; i < allLines1.size(); i++) {
                    array1[i] = Integer.parseInt(allLines1.get(i));
                }
                for (int i = 0; i < allLines2.size(); i++) {
                    array2[i] = Integer.parseInt(allLines2.get(i));
                }
                Arrays.sort(array1);
                Arrays.sort(array2);
		        
                int combinedMedian = ob.Q1_median_of_sorted_Array(array1, array2);
                System.out.println("Median of the combined array is " + combinedMedian);
		
		
                array1 = null;
                array2 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
    }
}
