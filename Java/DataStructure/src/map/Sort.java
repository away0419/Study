package map;

import java.util.*;

public class Sort {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key01", "value01");
		map.put("key02", "value02");
		map.put("key03", "value03");
		map.put("key04", "value04");
		map.put("key05", "value05");

		// Stream 사용 - 내림차순
		System.out.println("------key 내림차순------");
		map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
			System.out.println("[key]:" + entry.getKey() + ", [value]:" + entry.getValue());
		});
		System.out.println();

		// Stream 사용 - 오름차순
		System.out.println("------key 오름차순------");
		map.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).forEach(entry -> {
			System.out.println("[key]:" + entry.getKey() + ", [value]:" + entry.getValue());
		});
		System.out.println();
		
		// value로 정렬
		Map<Integer, Double> map2 = new HashMap<Integer, Double>();

		map2.put(1, 0.8);
		map2.put(2, 0.3);
		map2.put(3, 0.6);
		map2.put(4, 0.9);
		map2.put(5, 0.2);

		List<Integer> keySetList = new ArrayList<>(map2.keySet());

		// 오름차순
		System.out.println("------value 오름차순------");
		Collections.sort(keySetList, (o1, o2) -> (map2.get(o1).compareTo(map2.get(o2))));

		for (Integer key : keySetList) {
			System.out.println("key : " + key + " / " + "value : " + map2.get(key));
		}

		System.out.println();

		// 내림차순
		System.out.println("------value 내림차순------");
		Collections.sort(keySetList, (o1, o2) -> (map2.get(o2).compareTo(map2.get(o1))));
		for (Integer key : keySetList) {
			System.out.println("key : " + key + " / " + "value : " + map2.get(key));
		}

	}
}
