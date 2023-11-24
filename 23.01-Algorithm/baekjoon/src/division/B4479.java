package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 *  칸토어 집합
 *	arr[] : N 집합
 */
public class B4479 {
	static List<Integer> list;
	static StringBuilder arr[];

	public static void main(String[] args) throws IOException {
		list = new ArrayList<Integer>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int max = 0;
		while (!"".equals(str) && str != null) {
			int n = Integer.parseInt(str);
			max = Math.max(max, n);
			list.add(n);
			str = br.readLine();
		}
		arr = new StringBuilder[max + 1];

		StringBuilder sb = new StringBuilder("-");
		arr[0] = sb;
		for (int i = 1; i <= max; i++) {
			StringBuilder sbt = new StringBuilder();
			sbt.append(sb);
			for (int j = 0; j < Math.pow(3, i - 1); j++) {
				sbt.append(" ");
			}
			sbt.append(sb);
			arr[i] = sbt;
			sb = sbt;
		}

		for (int i = 0; i < list.size(); i++) {
			System.out.println(arr[list.get(i)]);
		}

	}

}
