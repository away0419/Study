package function;

import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

public class OperatorTest {
	public static void main(String[] args) {
		BinaryOperator<Integer> fn = (a,b)->{
			return a+b;
		};
		
		UnaryOperator<Integer> fn2 = a -> a*4;
		IntBinaryOperator fn3 = (a,b) -> a*b;
		IntUnaryOperator fn4 = a-> a/4;
		
		int sum = fn.apply(3, 5);
		int sum2 = fn2.apply(3);
		int sum3 = fn3.applyAsInt(3,4);
		int sum4 = fn4.applyAsInt(3);
		
		System.out.println(sum);
		System.out.println(sum2);
		System.out.println(sum3);
		System.out.println(sum4);
	}
}
