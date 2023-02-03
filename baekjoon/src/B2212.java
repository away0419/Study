import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*
 * 센서
 * N : 센서 갯수
 * K : 최대 집중국 수
 * arr[] : 각 센서의 좌표
 * 
 * 1. N개가 적어도 하나의 K와 연결되어야 한다
 * 2. 각 집중국의 수신 사능 영역의 길이 합 최소 값
 * 3. N개를 K 만큼 그룹화하고 그안의 거리를 더한 값 최소로 만들기
 * 
 */
public class B2212 {
	static int N, K, arr[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		Set<Integer> set = new TreeSet<>();

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			set.add(Integer.parseInt(st.nextToken()));
		}
		arr = new int[set.size() - 1];
		int i = 0;
		Iterator<Integer> iter = set.iterator();
		int start = iter.next();
		while (iter.hasNext()) {
			int next = iter.next();
			arr[i++] = next - start;
			start = next;
		}

		Arrays.sort(arr);
		int answer = 0;
		for (int j = 0; j < set.size() - K; j++) {
			answer += arr[j];
		}

		System.out.println(answer);

	}

}
