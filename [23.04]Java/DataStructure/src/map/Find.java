package map;

import java.util.*;
import java.util.stream.Collectors;

public class Find {
	public static void main(String[] args) {

		Map<String, Integer> map = new HashMap<>();
		map.put("apple", 1);
		map.put("melon", 2);
		map.put("kiwi", 3);
		map.put("banana", 2);

		for (String key : map.keySet()) {
			Integer value = map.get(key);
			System.out.println("Iterating, key: " + key);

			if (value == 2) {
				System.out.println("key of the value 2: " + key);
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println("Iterating, key: " + key + ", value: " + value);

			if (value == 2) {
				System.out.println("key of the value 2: " + key);
			}
		}

		Set<String> foundKeys = map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 2))
				.map(Map.Entry::getKey).collect(Collectors.toSet());

		System.out.println("Result: " + foundKeys);
	}
}
