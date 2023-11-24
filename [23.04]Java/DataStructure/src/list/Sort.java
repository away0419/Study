package list;

import java.util.*;

public class Sort {
	public static void main(String[] args) {
		// ArrayList 준비
		ArrayList<String> list = new ArrayList<>(Arrays.asList("C", "A", "B", "a"));
		System.out.println("원본 : " + list); // [C, A, B, a]

		// 오름차순으로 정렬
		list.sort(Comparator.naturalOrder());
		System.out.println("오름차순 : " + list); // [A, B, C, a]

		// 내림차순으로 정렬
		list.sort(Comparator.reverseOrder());
		System.out.println("내림차순 : " + list); // [a, C, B, A]

		// 대소문자 구분없이 오름차순 정렬
		list.sort(String.CASE_INSENSITIVE_ORDER);
		System.out.println("대소문자 구분없이 오름차순 : " + list); // [a, A, B, C]

		// 대소문자 구분없이 내림차순 정렬
		list.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
		System.out.println("대소문자 구분없이 내림차순 : " + list); // [C, B, a, A]
	}
}
