package bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
시간표 짜기
N : 총 과목 수
K : 과목 별 수업 시간
M : 학생 수
P : 비어 있는 시간
 */
public class B14569 {
    static int N, M;
    static long lessons[];

    public static void main(String[] args) throws IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        lessons = new long[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            long schedule = 0;
            for (int j = 0; j < K; j++) {
                int time = Integer.parseInt(st.nextToken()) - 1;
                schedule |= (1L << time);
            }
            lessons[i] = schedule;
        }

        M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            long schedule = 0;
            for (int j = 0; j < K; j++) {
                int time = Integer.parseInt(st.nextToken()) - 1;

                schedule |= 1L << time;
            }

            int answer = 0;
            for (int j = 0; j < N; j++) {
                if ((lessons[j] | schedule) == schedule) {
                    answer++;
                }
            }

            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }
}
