package prefixSum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* 소를 찾아라
 * 
 * N : 소괄호 갯수
 * s1[] : 뒷다리 누적합
 * set : 앞다리 가능한 인덱스
 */
public class B5874 {
	static int N, s1[];
	static long answer;
	static Set<Integer> set;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int l = str.length();
		set = new HashSet<>();
		s1 = new int[l+1];
		
		for (int i = l-1; i > 0; i--) {
			if(str.charAt(i)==str.charAt(i-1)) {
				if(str.charAt(i-1)=='(') {
					set.add(i);
					s1[i] =s1[i+1];
				}else if(str.charAt(i)==')') {
					s1[i]= s1[i+1]+1;
				}
			}else {
				s1[i] =s1[i+1];
			}
		}
		
		for(int i : set) {
			answer+= s1[i];
		}
		
		System.out.println(answer);
	}

}
