package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B15684 {
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        int[] position = new int[N];
        int[][] map = new int[H][N];
        answer = 4;
        for (int i = 0; i < N; i++) {
            position[i] = i;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int length = Integer.parseInt(st.nextToken()) - 1;
            int width = Integer.parseInt(st.nextToken()) - 1;
            map[length][width] = 1;
            map[length][width + 1] = 2;
        }

        for(int i=0; i<4; i++){
            if(answer != 4){
                break;
            }
            dfs(0, 0, i, N, H, position, map);
        }

        answer = (answer==4)?-1:answer;
        System.out.println(answer);
    }


    public static void dfs(int height, int cnt, int max, int N, int H, int[] position, int[][] map) {
        if (cnt == max) {
            if (check(N, H, position, map)) {
                answer = cnt;
            }
            return;
        }

        for (int i = height; i < H; i++) {
            for (int j = 0; j < N-1; j++) {
                if (map[i][j] != 0 || map[i][j + 1] != 0) {
                    continue;
                }

                map[i][j] = 1;
                map[i][j + 1] = 2;

                dfs(height, cnt + 1, max, N, H, position, map);

                map[i][j] = 0;
                map[i][j + 1] = 0;
            }
        }

    }

    public static boolean check(int N, int H, int[] position, int[][] map) {
        int[] copy = new int[N];
        System.arraycopy(position,0,copy,0,N);

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < N-1; j++) {
                if (map[i][j] == 1) {
                    int temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                    j++;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (copy[i] != i) {
                return false;
            }
        }

        return true;
    }

}
