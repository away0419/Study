package twopoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
수들의 합
N : 배열 수
M : 합이 되야하는 수
arr[] : 배열
 */
public class B2003 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int arr[] = new int[N];

        int start = 0;
        int answer = 0;
        int sum = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            sum += arr[i];
            if (sum < M) {
                continue;
            } else {
                while (sum >= M) {
                    if(sum==M){
                        answer++;
                    }
                    sum -= arr[start++];
                }
            }

        }

        System.out.println(answer);
    }

}
