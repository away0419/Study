package map;

import java.util.*;

public class Repetition {
	public static void main(String[] agrs) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("키1", "값1");
		map.put("키2", "값2");
		map.put("키3", "값3");
		map.put("키4", "값4");
		map.put("키5", "값5");
		map.put("키6", "값6");

		// 방법1
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println(String.format("키 : %s, 값 : %s", key, map.get(key)));
		}

		// 방법2
		for (Map.Entry<String, String> elem : map.entrySet()) {
			System.out.println(String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()));
		}

		// 방법3
		for (String key : map.keySet()) {
			System.out.println(String.format("키 : %s, 값 : %s", key, map.get(key)));
		}
	}
}
