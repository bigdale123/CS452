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
public class PS1 {

    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 1 (30 Points)
     * 
     * Q1_MinMax_2n_Minus_2: implement the function Q1_MinMax2n2, that count the number of comparisons 
     * and returns the minimum and maximum of the given array using exactly 2n - 2 comparisons
     *
     * Q2_MinMax_3n_By_2: implement the function Q1_MinMax2n2, that count the number of comparisons 
     * and returns the minimum and maximum of the given array using exactly 3n / 2 comparisons
     * 
     * If the length of the array is 1000, Q1_MinMax_2n_Minus_2 must make 1998 comparisions and 
     * Q2_MinMax_3n_By_2 must make 1500 comparisions
     * 
     * 
     * return [min, max, comparisons]
     * 
     * Plot a graph to show the total number of comparisons for both Q1_MinMax_2n_Minus_2 and Q2_MinMax_3n_By_2 for different input sizes
     * 
     * Hint : Carefully choose your if else statement and do not skip any comparisons
     *
     * 10 + 10 Points for the correct Code implementation
     * Graph 5 points
     * 5 points for reporting the correct answer
     ************************************************************************************************
     */
    public int[] Q1_MinMax_2n_Minus_2(int arr[]){
        int result[] = new int[3];
        int min = arr[0];
        int max = arr[0];
        for(int i = 2; i < arr.length; i++){
            if(min > arr[i]){
                min = arr[i];
            }
            else if(max < arr[i]){
                max = arr[i];
            }
        }
        result[0] = min;
        result[1] = max;
        result[2] = arr.length*2 - 2;
        // TODO: Implement this
        return result;
    }

    public int[] Q2_MinMax_3n_By_2(int arr[]){
        int result[] = new int[3];
        int min = arr[0];
        int max = arr[0];
        for(int i = 2; i < arr.length; i = i+2){
            if(arr[i] < arr[i+1]){
                if(arr[i] < min){
                    min = arr[i];
                }
                else if(arr[i+1] > max){
                    max = arr[i+1];
                }
            }
            else{
                if(arr[i+1] < min){
                    min = arr[i+1];
                }
                else if(arr[i] > max){
                    max = arr[i];
                }
            }
        }
        result[0] = min;
        result[1] = max;
        result[2] = arr.length*3/2;
        // TODO: Implement this
        return result;
    }


    public static void main(String args[]){

        PS1 ob = new PS1();
        for (int j = 0; j < 5; j++) {
            Random rand = new Random();
            int num = 0;

            int[] array1 = new int[(int)(Math.pow(10, j) * Math.pow(10, 3))];
            int[] array2 = new int[(int)(Math.pow(10, j) * Math.pow(10, 3))];
            int[] array3 = new int[(int)(Math.pow(10, j) * Math.pow(10, 3))];
            for (int i = 0; i < array1.length; i++) {
                num = rand.nextInt(array1.length) - array1.length /2;
                array1[i] = num;
                array2[i] = num;
                array3[i] = num;
            }
            Arrays.sort(array3);

            int result1[] = ob.Q1_MinMax_2n_Minus_2(array1);
            String space = (j == 4) ? "\t" : "\t\t";
            System.out.println(array1.length + "\tQ1_MinMax_2n_Minus_2\t" + result1[0] + space + result1[1] + "\t\t" + result1[2]);
            
            if(result1[0] != array3[0])
                System.out.println("######## Alert!!! Q1_MinMax_2n_Minus_2 min is wrong!! ########");
            if(result1[1] != array3[array3.length-1])
                System.out.println("######## Alert!!! Q1_MinMax_2n_Minus_2 max is wrong!! ########");
                
            int result2[] = ob.Q2_MinMax_3n_By_2(array2);
            System.out.println(array1.length + "\tQ2_MinMax_3n_By_2\t" + result2[0] + space + result2[1] + "\t\t" + result2[2]);
            if(result2[0] != array3[0])
                System.out.println("######## Alert!!! Q1_MinMax_2n_Minus_2 min is wrong!! ########");
            if(result2[1] != array3[array3.length-1])
                System.out.println("######## Alert!!! Q1_MinMax_2n_Minus_2 max is wrong!! ########");
            
            System.out.println();
            array1 = null;
            array2 = null;
            array3 = null;
        }
    }
}