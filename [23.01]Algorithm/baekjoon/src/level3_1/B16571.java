package level3_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B16571 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] map = new int[3][3];

        int winner = 0;
        for (int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {
                    winner--;
                } else if (map[i][j] == 1) {
                    winner++;
                }
            }
        }

        int res = permutation(winner, map);
        char answer = 'D';
        if (res == 1) {
            answer = 'W';
        } else if (res == -1) {
            answer = 'L';
        }

        System.out.println(answer);
    }

    public static int permutation(int player, int[][] map) {
        int nextRes = 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (map[i][j] != 0) {
                    continue;
                }
                map[i][j] = player + 1;
                nextRes = (check(player+1, map)) ? -1 : nextRes;
                nextRes = Math.min(nextRes, permutation(player ^ 1, map));
                map[i][j] = 0;
            }
        }

        if (nextRes == 1) return -1;
        else if (nextRes == -1) return 1;
        else return 0;
    }

    public static boolean check(int player, int[][] map) {
        int diagonal = 0;
        int diagonal2 = 0;
        for (int i = 0; i < 3; i++) {
            int w = 0;
            int h = 0;
            if (map[i][i] == player) {
                diagonal++;
            }

            if (map[2 - i][i] == player) {
                diagonal2++;
            }

            for (int j = 0; j < 3; j++) {
                if (map[i][j] == player) {
                    w++;
                }
                if (map[j][i] == player) {
                    h++;
                }
            }
            if (w == 3 || h == 3) {
                return true;
            }
        }

        if (diagonal == 3 || diagonal2 == 3) {
            return true;
        }

        return false;
    }

}
