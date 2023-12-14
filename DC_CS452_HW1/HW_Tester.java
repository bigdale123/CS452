import java.util.Arrays;
class HW_Tester{
    
    public static void main(String[] args){
        System.out.println("--- BEGIN DEBUG ---");
        int arr[] = {8,6,4,9,7,3,5,2,1,10};
        AlgorithmsHW1 ob = new AlgorithmsHW1();
        boolean result = ob.duplicateFinder_quadratic(arr);
        System.out.println(result);
        System.out.println("--- END DEBUG ---");
    }
}