package twopoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
배열 합치기
 */
public class B11728 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int idx1 = 0;
        int idx2 = 0;
        int arr1[] = new int[N];
        int arr2[] = new int[M];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr1[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            arr2[i] = Integer.parseInt(st.nextToken());
        }

        while (idx1 < N && idx2 < M) {
            if (arr1[idx1] > arr2[idx2]) {
                sb.append(arr2[idx2++]).append(" ");
            } else {
                sb.append(arr1[idx1++]).append(" ");
            }
        }

        for (int i = idx2; i < M; i++) {
            sb.append(arr2[i]).append(" ");
        }
        for (int i = idx1; i < N; i++) {
            sb.append(arr1[i]).append(" ");
        }

        System.out.println(sb);
    }

}
