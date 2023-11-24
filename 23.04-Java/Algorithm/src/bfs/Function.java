package bfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Function {
	
	//list : 노드 연결 정보
	//visit : 방문 체크
	//start : 시작 지점
	public void bfs(List<Integer>[] list, boolean[] visit,int start) {
		//방문한 노드를 담을 queue
		Queue<Integer> q = new ArrayDeque<Integer>();
		
		//현재 노드 담고 방문 처리
		q.add(start);
		visit[start] = true;
		
		//q가 비었다면 연결된 노드가 없다는 뜻
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			//cur과 연결된 모든 노드 방문. 이미 방문한 노드라면 패스
			for(int next : list[cur]) {
				if(!visit[next]) {
					q.add(next);
					visit[next] = true;
				}
			}
		}
	}
}
