package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		String str = "456789";
		int[] arr = Stream.of(str.split("")).mapToInt(Integer::parseInt).toArray();
		System.out.println(Arrays.toString(arr));
		
		int[] A = { 1, 2, 3, 4, 5 };
        int sum = Arrays.stream(A).sum();
        int sum2 = IntStream.of(A).sum();
        int sum3 = Arrays.stream(A).reduce((x, y) -> x + y).getAsInt();
        long sum4 = Arrays.stream(A).summaryStatistics().getSum();
        
        System.out.println(sum);
        System.out.println(sum2);
        System.out.println(sum3);
        System.out.println(sum4);
        
        
	
	}
}
