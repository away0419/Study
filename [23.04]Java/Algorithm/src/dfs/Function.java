package dfs;

import java.util.List;

public class Function {
	// 재귀(인접리스트)
	// list : List배열로 index는 현재 노드이며 현재 노드와 연결된 다른 노드의 정보가 list로 담겨있음.
	// cur : 현재 노드
	public void dfs(List<Integer>[] list, boolean[] visit, int cur) {
		
		//방문 처리
		visit[cur] = true;
		
		//현재 노드와 연결된 모든 노드 만큼 반복
		for(int next : list[cur]) {
			
			//재귀 탐색
			dfs(list,visit,next);
		}
	}
}
