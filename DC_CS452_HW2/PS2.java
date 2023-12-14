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
public class PS2 {
    

/**
     * ***********************************************************************************************
     *
     * PROBLEM SET 2 (70 Points)
     * 
     * RSelect : We are going to use random index as pivot to find ith order statistic
     *           The input array is made of distinc (unique) integers
     * [Reference]: I have used the algorithm on page 216 of the third edition of the cormen book (Chapter 9 Medians and Order Statistics)
     * [TASK]: Implement the partition code that uses an extra buffer (instead of swapping), you must mantaint two trackers left and right. 
     * [YOU WILL GET 0 POINTS IF YOU IMPLEMENT THE IN-PLACE PARTITION SUBROUTINE]
     * 20 Points
     *
     * 
     * DSelect : We are going to use the median of median technique to compute the pivot (in a deterministic way) to find ith order statistic
     *           The input array is made of distinc (unique) integers
     * [TASK]: You must implement the function DSelect, that follows the same structure as RSelect. 
     * The only difference will be how you select the pivot.
     * You must use groups of size 7 (instead of 5) for the median of median part. 
     * You must use the DSelect function to compute the median of median.
     * 35 Points
     * 
     * We are using four datasets (look folder PS1)
     * For each dataset, we are randomly picking 100 ith_order statistics
     * Your task is to add code to existing code to collect time taken to compute the ith_order statistic for all 100 cases for both RSelect and DSelect
     * 5 Points
     * 
     * You have been provided with 4 datasets 
     * Plot the time in a graph to show the runtime for both RSelect and DSelect for different input sizes
     * X-axis is size of array
     * Y-axis is time
     * There should be two trendlines (or bars), one for RSelect and one for DSelect
     * 10 Points
     ************************************************************************************************
     */


    /**
     * Implement this function that uses an explicit buffer, instead of the inplace algorithm
     * Partition partitons around pivot
     */
    public int partition(int arr[], int l, int r){
        // Used to switch on print statements, this function is pain :)
        int debug = 0;

        int pivot = arr[l];
        int[] buffer = new int[r-l+1];
        int left_pointer = 0;
        int right_pointer = buffer.length-1;
        int pivot_count = 1;
        if(debug == 1){System.out.println("Pivot: "+pivot);}
        for(int i = l+1; i <= r; i++){
            if(arr[i] < pivot){
                buffer[left_pointer] = arr[i];
                left_pointer++;
            }
            else if(arr[i] > pivot){
                buffer[right_pointer] = arr[i];
                right_pointer--;
            }
            else{
                pivot_count++;
            }
            if(debug == 1){System.out.println(Arrays.toString(buffer));}
        }
        for(int i = 0; i < pivot_count; i++){
            buffer[left_pointer] = pivot;
            left_pointer++;
            if(debug == 1){System.out.println(Arrays.toString(buffer));}
        }
        if(debug == 1){System.out.println("Pivot Index: "+--left_pointer);}
        // We add to make the pivot index match the index in the real array??
        return left_pointer+l-1;
    }

    
    /*
      * arr: array of distinct (unique) integers
      * l: left (starting tracker)
      * r: right (ending tracker)
      * ith_order: ith_order smallest. (1 <= ith_order <= size of array).
                   ith_order of 1 corresponds to the minimum
                   ith_order of arr.length corresponds to maximum
     * return the ith_order smallest element
     */
    public int RSelect(int arr[], int l, int r, int ith_order){

        
        // Base case
        if (l > r)
            return -1;
        if (l == r)
            return arr[l];

        // Pick pivot randomly in the range of (l, r-l+1) 
        Random rand = new Random();
        int pivot_index = rand.nextInt(r - l + 1) + l;
        

        // Swap the pivot with array[l]
        int temp = arr[l];
        arr[l] = arr[pivot_index];
        arr[pivot_index] = temp;


        // partition around arr[l], and return the index where the pivot lands
        int j = partition(arr, l, r);
        

        // recurse
        int k = j - l + 1;
        if (ith_order == k)
            return arr[j];
        else if (k > ith_order)
            return RSelect(arr, l, j-1, ith_order);
        else
            return RSelect(arr, j+1, r, ith_order - k);
    }

     /*
      * you can use the function Arrays.sort to sort the arrays of length 7
      * arr: array of distinct (unique) integers
      * l: left (starting tracker)
      * r: right (ending tracker)
      * ith_order: ith_order smallest. (1 <= ith_order <= size of array).
                   ith_order of 1 corresponds to the minimum
                   ith_order of arr.length corresponds to maximum
     * return the ith_order smallest element
     */
    public int DSelect(int arr[], int l, int r, int ith_order){
        
        // Base case (similar to RSelect)
        if (l > r)
            return -1;
        if (l == r)
            return arr[l];

        // Pick pivot deterministically (use median of median technique)
        // creating an array C of size n/7
        // Make groups of 7 elements, sort these groups of 7 using Arrays.sort
        // take the median of these groups and put them in C
        // Use DSelect itself to compute the median of C
        // Once you get the median, scan through the array to compute the index of the median (note this will work because array is unique)
        // Use the index as pivot_index
        // TODO
        int[] C = new int[arr.length/7];

        for(int i = 0; i < C.length; i++){
            int[] temp = new int[7];
            for(int j = 0; j < 7; j++){
                temp[j] = arr[i*7+j];
            }
            Arrays.sort(temp);
            C[i] = temp[temp.length/2];
        }

        int median = DSelect(C, 0, C.length-1, ith_order);
        int median_index = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == median){
                median_index = i;
                break;
            }
        }

        // exchnage pivot (which has an index of pivot_index) with arr[l]
        // (similar to RSelect)
        // Swap the pivot with array[l]
        int temp = arr[l];
        arr[l] = arr[median_index];
        arr[median_index] = temp;

        // partition around arr[l]
        // (similar to RSelect)
        int j = partition(arr, l, r);

        // recurse
        // (similar to RSelect)
        int k = j - l + 1;
        if (ith_order == k)
            return arr[j];
        else if (k > ith_order)
            return DSelect(arr, l, j-1, ith_order);
        else
            return DSelect(arr, j+1, r, ith_order - k);
    }
    
    
    public static void main(String args[]){

        PS2 ob = new PS2();

        // There are four data files, so, looping through all of them
        for (int j = 0; j < 4; j++) {
            try {
                String filename = "PS2/data_" + j + ".txt";

                List<String> allLines = Files.readAllLines(Paths.get(filename));
                int[] array1 = new int[allLines.size()];
                int[] array2 = new int[allLines.size()];
                for (int i = 0; i < allLines.size(); i++) {
                    array1[i] = Integer.parseInt(allLines.get(i));
                    array2[i] = Integer.parseInt(allLines.get(i));
                }

                //for each dataset we are computing 100 ith order statistic (the ith_order is picked randomly)
                // TODO: Add timing code here (for each file you must collect two timing numbers, one for RSelect and one for DSelect)
                // These two time should add time for all 100 ith_order statistics
                long total_rselect_time=0;
                long total_dselect_time=0;
                long total_brute_force_time=0;
                for (int p=1; p <= 100; p++)
                {
                    Random rand = new Random();
                    int ith_order = rand.nextInt(array1.length-1);
                    long start_RS = System.nanoTime();
                    int res1 = ob.RSelect(array1, 0, array1.length-1, ith_order);
                    long end_RS = System.nanoTime();

                    long start_DS = System.nanoTime();
                    int res2 = ob.DSelect(array2, 0, array2.length-1, ith_order);
                    long end_DS = System.nanoTime();

                    total_rselect_time = total_rselect_time + (end_RS - start_RS);
                    total_dselect_time = total_dselect_time + (end_DS - start_DS);
                    
                    if (res1 != res2) {
                        System.out.println("Soft fail");
                        break;
                    }

                    // Datsets 1,2,3 are setup in this way
                    if (j != 0)
                    {
                        if (res1 != 7 * ith_order)
                            System.out.println("Hard fail");
                    }

                    
                }
                System.out.println(filename + "\t" + allLines.size() + "\tRSelect time\t" + total_rselect_time);
                System.out.println(filename + "\t" + allLines.size() + "\tDSelect time\t" + total_dselect_time);

                array1 = null;
                array2 = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }   
}
