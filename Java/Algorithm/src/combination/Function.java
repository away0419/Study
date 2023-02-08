package combination;

public class Function {
	// 조합
	// arr : 뽑으려는 배열
	// cnt : 현재까지 뽑은 갯수
	// k : 뽑아야 하는 갯수
	// cur : 현재 인덱스
	public void fn(int arr[], int cnt, int k, int cur) {
		// k개 뽑은 경우
		if (cnt == k) {
			return;
		}

		// 배열을 모두 돌았으나 k개 못 뽑은 경우
		else if (arr.length == cur) {
			return;
		}

		// 현재 인덱스 선택 안한 경우
		fn(arr, cnt, k, cur + 1);

		// 현재 인덱스 선택 한 경우
		fn(arr, cnt + 1, k, cur + 1);

	}
	
	
	// arr : 뽑으려는 배열
	// k : 뽑아야 하는 갯수
	// cnt : 현재까지 뽑은 갯수
	// cur : 현재 인덱스
	public void fn2(int arr[], int k, int cnt, int cur) {
		if(cnt==k) {
			return;
		}
		
		//배열 만큼 돌기
		for(int i=cur; i<arr.length; i++) {
			
			//현재 i값을 뽑았으니, 나머지에서 한개 뽑기위해 재귀
			fn2(arr, k, cnt+1,  i+1);
		}
	}

	// arr : 뽑으려는 배열
	// k : 뽑아야 하는 갯수
	// cnt : 현재까지 뽑은 갯수
	// cur : 현재 인덱스
	public void fn3(int arr[], int k, int cnt, int cur) {
		if(cnt==k) {
			return;
		}
		
		//배열 만큼 돌기
		for(int i=cur; i<arr.length; i++) {
			
			//현재 i값을 뽑았으나, 다음에도 나를 뽑을 수 있기 때문에 i
			fn2(arr, k, cnt+1,  i);
		}
	}
	
	
	
}
