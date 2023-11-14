package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 욱제는 도박쟁이야
 * min : 최소 값
 * max : 최대 값
 * player : max-min
 * N : 동전 갯수
 */
public class B14655 {
	static int min, max, N;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			max+= Math.abs(Integer.parseInt(st.nextToken()));
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			min+= Math.abs(Integer.parseInt(st.nextToken()));
		}
		
		System.out.println(max+min);
	}

}
