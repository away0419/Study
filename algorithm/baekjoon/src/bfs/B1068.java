package bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class B1068 {
	static List<Integer>[] lists;
	static int N;
	static boolean[] visit;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int root = 0;
		N = Integer.parseInt(st.nextToken());
		lists = new ArrayList[N];
		visit = new boolean[N];
		
		for(int i=0; i<N; i++) {
			lists[i] = new ArrayList<>(); 
		}
		
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			int p = Integer.parseInt(st.nextToken());
			if(p == -1) {
				root = i;
				continue;
			}
			lists[p].add(i);
			
		}
		
		int start = Integer.parseInt(br.readLine());
		if(start == root) {
			System.out.println(0);
		}else {
			delete(start);
			int result = find(root);
			
			System.out.println(result);
			
		}
		
		
	}
	
	// 시작 노드의 트리 제거
	public static void delete(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		visit[start] = true;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			for(int child : lists[cur]) {
				visit[child] = true;
				q.add(child);
			}
		}
	}
	
	// 남은 트리의 리프 노드 찾기
	public static int find(int root) {
		
		Queue<Integer> q = new LinkedList<>();
		int reafCnt =0;
		q.add(root);
		visit[root] = true;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			int childCnt =0;
			
			for(int child : lists[cur]) {
				if(visit[child]) {
					continue;
				}
				visit[child] = true;
				q.add(child);
				childCnt++;
			}
			
			if(childCnt==0) {
				reafCnt++;
			}
		}
		
		return reafCnt;
		
	}
	
	
	
}
