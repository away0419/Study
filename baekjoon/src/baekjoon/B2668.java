package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class B2668 {
	static boolean visit[];
	static int N, arr[];
	static Set<Integer> tset;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N=Integer.parseInt(br.readLine());
		arr = new int[N+1];
		tset = new TreeSet<>();
		visit = new boolean[N+1];
		for(int i=1; i<=N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		for(int i=1; i<=N; i++) {
			if(visit[i]) {
				continue;
			}
			visit[i] = true;
			Set<Integer> set = new HashSet<>();
			set.add(i);
			fn(i,arr[i],set);
		}
		
		System.out.println(tset.size());
		for(int i : tset) {
			System.out.println(i);
		}
		
	}
	
	public static void fn(int start, int cur, Set<Integer> set) {
		if(start == cur) {
			tset.addAll(set);
			return;
		}
		if(visit[cur]) return;
		visit[cur] = true;
		set.add(cur);
		fn(start, arr[cur], set);
		visit[cur] = false;
	}
}
