package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// N : 학생 수 (1부터 N까지 정수)
// A, B 비교 후 더 높은 학생 알려줌
// M : 질문 수
// X : 학생으로 해당 학생의 등수를 알고 싶음
// X의 등수 범위를 찾아 출력
// 출력 : x의 가장 높은 등수 U, 가낭 낮은 등수 V
public class B17616 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        List<Integer> list[][] = new ArrayList[n + 1][2];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 2; j++) {
                list[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list[a][1].add(b);
            list[b][0].add(a);
        }

        int worst = n - fn(x, n, 1, list);
        int best = fn(x, n, 0, list) + 1;

        System.out.println(best + " " + worst);
    }

    public static int fn(int x, int n, int flag, List<Integer>[][] list) {
        int cnt = 0;
        boolean[] visits = new boolean[n + 1];
        Queue<Integer> q = new ArrayDeque<>();

        q.add(x);
        visits[x] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : list[cur][flag]) {
                if (!visits[next]) {
                    visits[next] = true;
                    cnt++;
                    q.add(next);
                }
            }
        }

        return cnt;
    }

}
