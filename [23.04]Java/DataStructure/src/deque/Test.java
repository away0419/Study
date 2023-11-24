package deque;

import java.util.ArrayDeque;
import java.util.Deque;

public class Test {
	public static void main(String[] args) {
		Deque<Integer> deque = new ArrayDeque<>();
		deque.addFirst(1); // 앞쪽에 삽입
		deque.addLast(2); // 뒤쪽에 삽입

		deque.offerFirst(3); // 앞쪽에 삽입
		deque.offerLast(4); // 뒤쪽에 삽입

		deque.push(5); // 앞쪽에 삽입
		deque.add(6); // 뒤쪽에 삽입
		deque.offer(7); // 뒤쪽에 삽입
		
		while(!deque.isEmpty()) {
			System.out.println(deque.poll());
		}
	}
}
