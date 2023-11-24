package greedy;

public class Function {
	public int fn(int cost, int arr[]) {
		int i = 0;
		int cnt = 0;
		while (cost > 0) {
			cnt += cost / arr[i];
			cost %= arr[i++];
		}

		return cnt;
	}
}
