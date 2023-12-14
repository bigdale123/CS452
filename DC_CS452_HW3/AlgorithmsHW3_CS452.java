import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.lang.Object;
import java.util.Arrays;
import java.io.*;
import java.util.*;
import java.lang.Math;   

/**
 *
 * @author sidharth
 */
public class AlgorithmsHW3_CS452 {

    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 1 (50 points)
     * 
     * 
     * The Hibbonacci sequence Tn is defined as follows: 
       H_0 = 1, H_1 = 2, H_2 = 3, and 
       H_(n+3) = H_n + H_(n+1) + H_(n+2) for n >= 3.

      Given n, your task is to return the nth Hibbonacci number, in the following ways:
     * 
     * [10 points] Hibonacci_exponential: compute nth hibonacci number with an exponential running time
     * [10 points] Hibonacci_linear: compute nth hibonacci number with a linear running time
     * [25 points] Hibonacci_log: compute nth hibonacci number with a logarithmic running time
     * 
     * [5 points] Plot a graph showing the timings to compute the first 35 hibonacci numbers using all three methods. 
     *             X axis should be for n and y axis should be for time taken.
     ************************************************************************************************
     */
    public int hibonacci_exponential(int n) {
        int f = 0;
        if (n == 0 || n == 1){
          f = 1;
        }
        else{
          f = hibonacci_exponential(n-1)+hibonacci_exponential(n-2);
        }
        return f;
    }
    
    public int hibonacci_linear(int n) {
        int[] fib_array = new int[n+1];
        for(int i = 0; i < n+1; i++){
          if(i == 0 || i == 1){
            fib_array[i] = 1;
          }
          else{
            fib_array[i] = fib_array[i-1] + fib_array[i-2];
          }
        }
	      
        return fib_array[n];
    }

    public int[][] matrix_multiply(int[][] x, int[][] y){
      // Used the following site as reference for performing matrix multiplication
      // https://www.baeldung.com/java-matrix-multiplication
      int[][] result = new int[x.length][y[0].length];

      for(int i = 0; i < result.length; i++){
        for(int j = 0; j < result[i].length; j++){
          int cell_result = 0;
          for(int k = 0; k < y.length; k++){
            cell_result += x[i][k] * y[k][j];
          }

          result[i][j] = cell_result;
        }
      }
      return result;
    }
    
    public int[][] matrix_power(int[][] x, int n)
    {
      if( n == 0 || n == 1){
        return x;
      }
      int[][] x_backup = new int[x.length][x[0].length];
      for(int i = 0; i < x.length; i++){
        x_backup[i] = x[i].clone();
      }
      
      x = matrix_multiply(x, x);
      int temp_n = n/2;
      x = matrix_power(x, temp_n);
      // Accounting for any non Power-Of-Two calculations needed to be done
      if (n%2 != 0){
        x = matrix_multiply(x, x_backup);
      }
      return x;
    }
    
    public int hibonacci_log(int n) {
        //System.out.println("Value of n: "+n);
        if(n == 0){
          return 1;
        }
        int[][] matrix = {{0,1},{1,1}};
        int[][] result_matrix = {{0},{1}};
        matrix = matrix_power(matrix,n);
        result_matrix = matrix_multiply(matrix,result_matrix);
        return result_matrix[1][0];
    }
    
    



    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 2 (30 points)
     * 
     * You are climbing a staircase. It takes n steps to reach the top. 
     * Each time you can either hop 1 steps or 2 steps or 3 steps. 
     * In how many distinct ways can you climb to the top?
     * 
     * Implement a naive recursive solution [10 points]
     * Implement a solution with dynamic programming using the bottom-up approach [10 points]
     * 
     * Input : 4 | Output : 7 | Explanation: (1 step + 1 step + 1 step + 1 step), (1 step + 2 step + 1 step), (2 step + 1 step + 1 step),
     * (1 step + 1 step + 2 step), (2 step + 2 step), (3 step + 1 step), (1 step + 3 step)
     * 
     * [10 points] Print out the time take to find solution for n=0 to n=30 (in a graph or in a table)
     ************************************************************************************************
     */
    int climbStairs_naive_recurssion(int n) {
      if(n < 0){
        return 0;
      }
      else if(n == 1){
        return 1;
      }
      return climbStairs_naive_recurssion(n-1)+climbStairs_naive_recurssion(n-2)+climbStairs_naive_recurssion(n-3);
    }

    int climbStairs_dynamic_programming(int n) {
      if(n == 0){
        return 0;
      }
      int[] memo = new int[n];
      for(int i = 0; i < n; i++){
        if(i == 0 || i == 1){
          memo[i] = 1;
        }
        else{
          for(int j = 1; j <= 3; j++){
            if (j <= i){
              memo[i] += memo[i-j];
            }
          }
        }
      }
      return memo[n-1];
    }


    /**
     * ***********************************************************************************************
     *
     * PROBLEM SET 3 (40 points)
     *
     * [30 points] Given a triangle array, return the maximum path sum from top to bottom.
     * For each step, you may move to an adjacent number of the row below 
     * (if you are on index i on the current row, you may move to either index i or index i + 1 on the next row).
     * Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
       Output: 11
       Explanation: The triangle looks like:
		   2
		  3 4
		 6 5 7
		4 1 8 3
     The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11.
     
     [10 points] 
     Print out the correct answer for all triangles (pre-baked data and random data) (in a table)
     ************************************************************************************************
     */
    public int minimumTotal(List<List<Integer>> triangle)
    {
    	// for (int i=0; i < triangle.size(); i++)
    	// {
    	//   List<Integer> tlist = triangle.get(i);
    	//   for (int j=0; j < tlist.size(); j++)
    	//   {
    	//     System.out.print(tlist.get(j)+ " ");
    	//   }
    	//   System.out.println();
    	// }
    	// System.out.println();
      int[] route = new int[triangle.size()];

      route[0] = triangle.get(0).get(0);
      int previous_index = 0;

      for(int i = 1; i < triangle.size(); i++){
        if(triangle.get(i).get(previous_index) > triangle.get(i).get(previous_index+1)){
          route[i] = triangle.get(i).get(previous_index+1);
          previous_index += 1;
        }
        else{
          route[i] = triangle.get(i).get(previous_index);
        }
      }

      //System.out.println("Route taken: "+Arrays.toString(route));

      int sum = 0;
      for(int i = 0; i < route.length; i++){
        sum += route[i];
      }
    	
    	return sum;
    }
    
    /**
     * ***********************************************************************************************
     *
     * 
     * PROBLEM SET 4 (30 points)
     *
     * Implement the function to compute the nth power of 3. Using the following two formulae:
     * Formula_1: (15 points)
     * n % 4 = 0: 3^n = 3^(n/4) x 3^(n/4) x 3^(n/4) x 3^(n/4)
     * n % 4 = 1: 3^n = 3^(n/4) x 3^(n/4) x 3^(n/4) x 3^(n/4) x 3
     * n % 4 = 2: 3^n = 3^(n/4) x 3^(n/4) x 3^(n/4) x 3^(n/4) x 9
     * n % 4 = 3: 3^n = 3^(n/4) x 3^(n/4) x 3^(n/4) x 3^(n/4) x 27
     * 
     * Formula_2: (10 points)
     * 3^n = 3^(n-2) x 9
     * 
     * Both implementations should be implemented using recurssion 
     * Formula_1 should be implemented using concepts of dyamic programming
     * 
     * Report the results till n = 30 (5 points)
     ************************************************************************************************
     */

    long formula_1(int n) {
      if(n == 0){
        return 1;
      }
      else if(n == 1){
        return 3;
      }
      //System.out.println(n);
      long result = formula_1(n/4)*formula_1(n/4)*formula_1(n/4)*formula_1(n/4);
      switch (n%4){
        case 1:
          result *= 3;
          break;
        case 2:
          result *= 9;
          break;
        case 3:
          result *= 27;
          break;
      }

      return result;
    }

    long formula_2(int n) {
      if(n == 0){
        return 1;
      }
      else if(n == 1){
        return 3;
      }
      return formula_2(n-2)*9;
    }


    public static void main(String[] args) {

        AlgorithmsHW3_CS452 ob = new AlgorithmsHW3_CS452();

        // Problem set 1
        // TODO add timing code
        System.out.println("----- Problem Set 1 -----");
	      for (int i=0; i < 35; i++)
	      {
          long start_exp = System.nanoTime();
	        int hib_exp = ob.hibonacci_exponential(i);
          long end_exp = System.nanoTime();
          long start_linear = System.nanoTime();
	        int hib_linear = ob.hibonacci_linear(i);
          long end_linear = System.nanoTime();
          long start_log = System.nanoTime();
	        int hib_log = ob.hibonacci_log(i);
          long end_log = System.nanoTime();
          System.out.println(i+",\t"+(end_exp-start_exp)+",\t  "+(end_linear-start_linear)+",\t  "+(end_log-start_log));

          if (hib_exp != hib_linear || hib_linear != hib_log){
            System.out.println("Error");
          }
	      }
        
        // Problem set 2
        // Add timing code
        System.out.println("----- Problem Set 2 -----");
        // Changed to 30 because naive was taking too long
	      for (int i=0; i < 31; i++)
	      {
          long start_naive = System.nanoTime();
	        int climb_naive = ob.climbStairs_naive_recurssion(i);
          long end_naive = System.nanoTime();
          long start_dp = System.nanoTime();
	        int climb_dynamic_programming = ob.climbStairs_dynamic_programming(i);
          long end_dp = System.nanoTime();
          if (climb_naive != climb_dynamic_programming)
            System.out.println("Error");
          System.out.println(i+",\t"+(end_naive-start_naive)+",\t"+(end_dp-start_dp));
	      }        


	      // Problem set 3
        // Add code to print out the correct answer 
        
        // generating 40 use cases with pre-baked data
        // i = 1, triangle with one row
        //   5

        // i = 2, triangle with two rows
        //     10
        //  15  20

        // i = 3, triangle with two rows
        //      25
        //    30  35
        //   40  45  50

        // so on and so forth
        System.out.println("----- Problem Set 3 -----");
        System.out.println("-- Constant Triangle --");
        int counter = 5;
        for (int i = 1; i < 40; i++)
	      {
	          List<List<Integer>> list = new ArrayList<List<Integer>>(i);
	          for (int j=1;j <= i;j++)
	          {
	            List<Integer> tlist = new ArrayList<Integer>(j);
	            for (int k=0; k<j; k++)
	            {
	              tlist.add(counter);
                counter = counter + 5;
  	          }
  	          list.add(tlist);
	          }
	          int result = ob.minimumTotal(list);
            System.out.println(i+"\t"+result);
          
	      }
        System.out.println("-- Random Triangle --");
        // generating 40 use cases with random data
        // the structure is going to be same as before, however the data is all random
        for (int i = 1; i < 40; i++)
	      {
          List<List<Integer>> list = new ArrayList<List<Integer>>(i);
	        for (int j=1;j <= i;j++)
	        {
	          List<Integer> tlist = new ArrayList<Integer>(j);
	          for (int k=0; k<j; k++)
	          {
	            tlist.add((int)(Math.random()*1000) % 1000);
  	        }
  	        list.add(tlist);
	        }
	        int result = ob.minimumTotal(list);
          System.out.println(i+"\t"+result);
	      }

        // Problem set 4
        // Add outputting code
        System.out.println("----- Problem Set 4 -----");
        for (int i=0; i < 35; i++)
	      {
	        long f1 = ob.formula_1(i);
	        long f2 = ob.formula_2(i);

          if (f1 != f2){
            System.out.println("Error");
            System.out.println("F1: "+f1);
            System.out.println("F2: "+f2);
          }
          else{
            System.out.println("3^"+i+"\t"+f1);
          }
	      }
    }
}
