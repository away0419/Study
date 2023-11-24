package binarySearch;

public class BinarySearch {
	
	/* arr : 탐색하려는 배열
	   key : 찾으려는 값
	   left: 탐색 범위의 시작 인덱스
	   right : 탐색 범위의 끝 인덱스
	   while를 이용한 탐색
	 */
	int fn1(int arr[], int key, int left, int right){
		
		// 탐색 범위의 시작이 끝과 동일하거나 작을 때만 반복한다.
		while(left<=right){ 
			
			// 탐색 범위의 중간 인덱스
			int mid = (left+right)/2;  
			
			// 찾은 경우 mid 반환
			if(arr[mid] == key) { 
				return mid; 
			}
			
			/*
			 * 중간 값이 큰 경우, K가 left ~ mid-1 사이에 있을 수 있다는 뜻.
			 * 따라서, right=mid-1 하고 다시 탐색.
			 */
			else if(arr[mid]>key) { 
				right = mid-1;
				continue;
			}
			
			/*
			 * 중간 값이 작은 경우, K가 mid+1 ~ right 사이에 있을 수 있다는 뜻. 
			 * 따라서, left=mid-1 하고 다시 탐색.
			 */
			else{
				left = mid+1;
				continue;
			}
		}
		
		// 찾지 못한 경우 -1 리턴
		return -1;
	}
	
	
	/* arr : 탐색하려는 배열
	   key : 찾으려는 값
	   left: 탐색 범위의 시작 인덱스
	   right : 탐색 범위의 끝 인덱스
	   while를 이용한 탐색
	 */
	int fn2(int arr[], int key, int left, int right){
		
		//재귀 함수 탈출 조건. 탐색 범위가 어긋나면 못찾은 것이니 -1 반환
		if(left>right) {
			return -1;
		}
		
		// 탐색 범위의 중간 인덱스
		int mid = (left+right)/2;  
		
		// 찾은 경우 mid 반환
		if(arr[mid] == key) { 
			return mid; 
		}
		
		/*
		 * 중간 값이 큰 경우, K가 left ~ mid-1 사이에 있을 수 있다는 뜻.
		 * 따라서, right=mid-1 하고 재귀 탐색.
		 */
		else if(arr[mid]>key) { 
			return fn2(arr, key, left, mid-1);
		}
		
		/*
		 * 중간 값이 작은 경우, K가 mid+1 ~ right 사이에 있을 수 있다는 뜻. 
		 * 따라서, left=mid-1 하고 재귀 탐색
		 */
		else{
			return fn2(arr, key, left, mid+1);
		}
	
	}

}
