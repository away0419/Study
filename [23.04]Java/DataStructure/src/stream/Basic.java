package stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Basic {
	public static void main(String[] args) {
		// 0 ~ 1000 중 500개 스킵하고 i가 2의 배수인 것 필터링.
		// 이후 5의 배수인 것 필터링.
		// 이후 남은 요소 합
		System.out.println(IntStream.range(0, 1001).skip(500).filter(i -> i % 2 == 0).filter(i -> i % 5 == 0).sum());
		// 38250

		// stream으로 변환 후 해당 조건에 맞는 Stream<Object>로 변환
		// forEach로 해당 스트림 요소 모두 출력
		List<String> list = Arrays.asList("Java8 lamda", "stream mapping");
		list.stream().flatMap(data -> Arrays.stream(data.split(" "))).forEach(System.out::println);
		/*
		 * Java8 lamda stream mapping
		 */

		// int[] -> IntStream -> doubleStream -> 출력
		int[] intArr = { 1, 2, 3, 4, 5 };
		IntStream intStream = Arrays.stream(intArr);
		intStream.asDoubleStream().forEach(System.out::println);
		System.out.println();
		intStream = Arrays.stream(intArr);
		intStream.boxed().forEach(data -> System.out.println(data));
		/*
		 * 1.0 2.0 3.0 4.0 5.0 1 2 3 4 5
		 */
		
		/* 1차원 배열 합 */
		System.out.println(Arrays.stream(intArr).sum());
		System.out.println(Arrays.stream(intArr).reduce(0, (x,y)->x+y));
		
		/* 2차원 배열 합 */
		int[][] arr = { { 1, 2, 3, 4, 5 }, { 1, 2, 3, 4, 5 }, { 1, 2, 3, 4, 5 } };
		System.out.println(Arrays.stream(arr).flatMapToInt(a->Arrays.stream(a)).sum());
		
		/* 3차원 배열 합 */
		int[][][] arr3 = { {{ 1, 2, 3, 4, 5 },{ 1, 2, 3, 4, 5 }}, {{ 1, 2, 3, 4, 5 },{ 1, 2, 3, 4, 5 }}, {{ 1, 2, 3, 4, 5 },{ 1, 2, 3, 4, 5 }} };
		System.out.println(Arrays.stream(arr3).flatMapToInt(t -> Arrays.stream(t).flatMapToInt(Arrays::stream)).sum());
		


	}
}
