package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B14936 {

	static int N, M;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());	
		M = Integer.parseInt(st.nextToken());	
		int ans=1;
		
		int all = N;  //케이스 별 걸리는시간
		int odd = ((N % 2) == 0) ? (N / 2) : (N / 2 + 1);
		int even = N / 2;
		int k3 = (N - 1) / 3 + 1;
		
		
		if (M >= all) { ans++; }
	    if (M >= odd && N > 1) { ans++; }
	    if (M >= even && N > 1) { ans++; }
	    if (M >= k3 && N > 2) { ans++; }
	    if (M >= k3 + odd && N > 2) { ans++; }
	    if (M >= k3 + even && N > 2) { ans++; }
	    if (M >= k3 + all && N > 2) { ans++; }
		
		System.out.println(ans);
	}

}
