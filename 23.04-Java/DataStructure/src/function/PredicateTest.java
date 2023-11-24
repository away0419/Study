package function;

import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class PredicateTest {
	public static void main(String[] args) {
		Predicate<Integer> fn = t -> {
			if(t>0)
				return true;
			return false;
		};
		
		BiPredicate<Integer, Double> fn2 = (t, u) -> {
			if(t==2 || u==3.0d) {
				return true;
			}
			
			return false;
		};
		
		IntPredicate fn3 = value -> {
			if(value == 4) return true;
			return false;
		};
		
		boolean result = fn.test(-1);
		boolean result2 = fn2.test(3,2.0d);
		boolean result3 = fn3.test(4);
		
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
	}
	
	
	
}
