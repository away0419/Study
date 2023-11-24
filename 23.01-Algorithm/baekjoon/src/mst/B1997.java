package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 최소 신장 트리
 * V : 정점 개수
 * E : 간선 개수
 * list[] : 간선 리스트 배열 
 * edge : 간선
 * visit[] : 노드 방문 체크 배열
 * parent[] : 부모 노드 배열
 * 
 * 
 */
public class B1997 {
	static int V, E, parent[];
	static long answer;
	static List<Edge> list[];
	static boolean visit[];

	public static class Edge implements Comparable<Edge> {
		int to;
		long cost;

		public Edge(int to, long cost) {
			super();
			this.to = to;
			this.cost = cost;
		}

		public int compareTo(Edge e) {
			return Long.compare(this.cost, e.cost);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		visit = new boolean[V];
		list = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());

			list[from].add(new Edge(to, cost));
			list[to].add(new Edge(from, cost));
		}

		prim();

		System.out.println(answer);

	}

	public static void prim() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(0, 0));
		int cnt = 0;
		while (!pq.isEmpty()) {
			Edge edge = pq.poll();
			int cur = edge.to;
			long cost = edge.cost;

			if (visit[cur]) {
				continue;
			}

			visit[cur] = true;
			answer += cost;

			if (++cnt == V)
				return;

			for (Edge next : list[cur]) {
				pq.add(next);
			}
		}

	}
	

}
