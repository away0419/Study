package permutation;

public class Function {
	// arr : 주어진 배열
	// depth : 깊이. 즉 뽑은 갯수
	// n : arr의 크기
	// r : 뽑으려는 갯수
	static void fn(int[] arr, int depth, int n, int r) {
		if (depth == r) {
			print(arr, r);
			return;
		}

		for (int i = depth; i < n; i++) {
			swap(arr, depth, i);
			fn(arr, depth + 1, n, r);
			swap(arr, depth, i);
		}
	}

	//arr 안에서 자리를 바꾸는 것이다.
	static void swap(int[] arr, int depth, int i) {
		int temp = arr[depth];
		arr[depth] = arr[i];
		arr[i] = temp;
	}

	static void print(int[] arr, int r) {
		for (int i = 0; i < r; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	// 백트래킹+순열
	// arr : 뽑으려는 배열
	// cnt : 현재까지 뽑은 갯수
	// k : 뽑아야 하는 갯수
	// visit : arr 인덱스 별 방문 유무. (뽑았는지 아닌지를 나타내는 변수)
	public void fn2(int arr[], boolean visit[], int cnt, int k) {
		if (cnt == k) {
			return;
		}

		// 모든 배열중 방문 안한 인덱스 방문 하기
		for (int i = 0; i < arr.length; i++) {
			if (visit[i])
				continue;
			visit[i] = true;
			// 뽑았으니 다른거 다시 뽑으러 감.
			fn2(arr, visit, cnt + 1, k);
			visit[i] = false;

		}
	}

	// 백트래킹+순열+자기자신
	// arr : 뽑으려는 배열
	// cnt : 현재까지 뽑은 갯수
	// k : 뽑아야 하는 갯수
	public void fn2(int arr[], int cnt, int k) {
		if (cnt == k) {
			return;
		}

		// 모든 배열중 방문 안한 인덱스 방문 하기
		for (int i = 0; i < arr.length; i++) {
			fn2(arr, cnt + 1, k);
		}
	}

}
