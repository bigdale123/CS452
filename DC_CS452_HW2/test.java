import java.util.Arrays;
public class test{
    public static void main(String args[]){
        PS2 ps2 = new PS2();
        int[] numbers = {1,4,5,7,2,8,6,9,0};
        // System.out.println(ps2.RSelect(numbers, 0, numbers.length-1, 1));
        System.out.println(ps2.DSelect(numbers, 0, numbers.length-1, 1));
        Arrays.sort(numbers);
        System.out.println(numbers[numbers.length/2]);
    }
}