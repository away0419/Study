package mst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 네트워크 연결
 * N : 컴퓨터 수
 * M : 선의 수
 * list[] : 리스트 배열
 * visit[] : 방문
 * answer : 답
 * 
 */
public class B1922 {
	static int N, M;
	static long answer;
	static List<Edge> list[];
	static boolean visit[];

	public static class Edge implements Comparable<Edge> {
		int to;
		long cost;

		public Edge(int to, long cost) {
			this.to = to;
			this.cost = cost;
		}

		public int compareTo(Edge e) {
			return Long.compare(this.cost, e.cost);
		}
	}

	public static void prim() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(0, 0));
		int cnt = 0;
		while (!pq.isEmpty()) {
			Edge e = pq.poll();
			int cur = e.to;
			long cost = e.cost;

			if (visit[cur]) {
				continue;
			}

			answer += cost;
			visit[cur] = true;

			if (++cnt == N) {
				return;
			}

			for (Edge edge : list[cur]) {
				pq.add(edge);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		visit = new boolean[N];
		list = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken());

			list[a].add(new Edge(b, c));
			list[b].add(new Edge(a, c));

		}

		prim();

		System.out.println(answer);

	}

}
