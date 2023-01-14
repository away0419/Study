package set;

import java.util.*;

public class TreeSetBasic {
	public static void main(String[] args) {
		TreeSet<Integer> ts = new TreeSet<>();
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(3);
		
		/*---- 추가 ----*/
		ts.add(1); // 추가
		ts.addAll(list); // 컬렉션 추가
		
		/*---- 조회 ----*/
		ts.ceiling(1); // 지정된 객체 조회, 없다면 지정된 객체 보다 크고 가까운 객체 조회
		ts.floor(1); // 해당 객체 조회, 없으면 작으며 가까운 값 조회
		ts.contains(1); // 해당 객체 있으면 true
		ts.containsAll(list); // 해당 컬렉션의 객체들이 포함 되어 있으면 true
		ts.first(); // 첫 번째 객체 조회
		ts.last(); // 마지막 객체 조회
		ts.higher(1); // 해당 객체보다 크고 가까운 값 조회. 없으면 null
		ts.lower(1); // 해당 객체보다 작고 가까운 값 조회. 없으면 null
		ts.pollFirst(); // 첫 번째 객체 반환 후 삭제
		ts.pollLast(); // 마지막 객체 반환 후 삭제
		
		/*---- 삭제 ----*/
		ts.clear(); // 모든 객체 삭제
		ts.remove(1); // 해당 객체 삭제
		ts.removeAll(list); // 해당 컬렉션에 있는 모든 값 삭제
		
		/*---- 기타 ----*/
		ts.descendingSet(); // 저장된 요소 역순 정렬 후 반환
		ts.retainAll(list); // 해당 컬렉션과 공통된 요소남 남기고 삭제
		ts.subSet(1, 3); // 사이의 결과 반환. 1, 2
		ts.subSet(1, false, 3, true); // 사이 결과 반환. boolean은 포함 여부. 2, 3
		ts.tailSet(1); // 해당 객체보다 큰 모든 값 반환
		ts.tailSet(1, true); // true면 포함하여 반환
		ts.headSet(1); // 해당 객체보다 작은 모든 값 반환
		ts.headSet(1, true); // true면 포함하여 반환
	
	}
}
