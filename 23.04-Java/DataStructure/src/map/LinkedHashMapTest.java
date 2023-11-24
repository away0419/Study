package map;

import java.util.*;
import java.util.Map.Entry;

public class LinkedHashMapTest {
	public static void main(String[] args) throws Exception {
		/*---- 기본 ----*/
		LinkedHashMap<String, String> lhm = new LinkedHashMap<>();

		for (int i = 0; i < 10; i++) {
			lhm.put("foo" + i, "bar" + i);
		}

		lhm.put("foo5", "re-insert bar5");
		lhm.put("foo11", "bar11");

		for (Entry<String, String> string : lhm.entrySet()) {
			System.out.println(string);
		}
		/*
		 * foo0=bar0 
		 * foo1=bar1 
		 * foo2=bar2 
		 * foo4=bar4 
		 * foo6=bar6 
		 * foo7=bar7 
		 * foo8=bar8
		 * foo9=bar9 
		 * foo5=re-insert bar5 
		 * foo11=bar11 
		 * foo3=bar3
		 */

		/*---- 매개 변수 설정한 경우 ----*/
		LinkedHashMap<String, String> lhm2 = new LinkedHashMap<>(1000, 0.75f, true);
		for (int i = 0; i < 10; i++) {
			lhm2.put("foo" + i, "bar" + i);
		}
		// 설정을 했기 때문에 다음 3개는 후미로 이동한다
		lhm2.put("foo5", "re-insert bar5");
		lhm2.put("foo11", "bar11");
		lhm2.get("foo3");

		for (Entry<String, String> string : lhm2.entrySet()) {
			System.out.println(string);
		}
		/*
		 * foo0=bar0 
		 * foo1=bar1 
		 * foo2=bar2 
		 * foo4=bar4 
		 * foo6=bar6 
		 * foo7=bar7 
		 * foo8=bar8
		 * foo9=bar9 
		 * foo5=re-insert bar5 
		 * foo11=bar11 
		 * foo3=bar3
		 */

		/*---- TRUE 설정 + 캐시 크기 설정 ----*/
		LinkedHashMap<String, String> lhm3 = new LinkedHashMap<String, String>(1000, 0.75f, true) {

			private final int MAX = 10;

			protected boolean removeEldestEntry(java.util.Map.Entry<String, String> eldest) {
				return size() >= MAX;
			}

		};

		for (int i = 0; i < 10; i++) {
			lhm3.put("foo" + i, "bar" + i);
		}
		// 총 크기가 10 이상이면 removeEldestEntry가 발동하며 제일 앞의 값 삭제
		lhm3.put("foo5", "re-insert bar5");
		lhm3.put("foo4", "re-insert bar4");
		lhm3.put("foo12", "bar12");
		lhm3.put("foo13", "bar13");
		lhm3.put("foo14", "bar14");
		lhm3.put("foo15", "bar15");
		lhm3.put("foo5", "re-insert bar5");

		for (Entry<String, String> string : lhm3.entrySet()) {
			System.out.println(string);
		}
		/*
		foo7=bar7
		foo8=bar8
		foo9=bar9
		foo4=re-insert bar4
		foo12=bar12
		foo13=bar13
		foo14=bar14
		foo15=bar15
		foo5=re-insert bar5
		*/

	}
}
