package datastructures;
/*
문자열 지옥에 빠진 호석
N : 행
M : 열
K : 문자 수
map : 주어지는 2차원 문자 배열

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class B20166 {
    static int N, M, K;
    static char[][] map;
    static Set<String> wordSet;
    static String[] wordArr;
    static Map<String, Integer> wordMap;
    static Map<Character, Integer> firstCharLengMap;
    static int idx[] = {0, 0, -1, 1, 1, -1, 1, -1};
    static int idy[] = {1, -1, 0, 0, 1, 1, -1, -1};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new char[N][];
        wordSet = new HashSet<>();
        wordArr = new String[K];
        wordMap = new HashMap<>();
        firstCharLengMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < K; i++) {
            String str = br.readLine();
            wordArr[i] = str;
            int maxLeng = Math.max(str.length(), firstCharLengMap.getOrDefault(str.charAt(0), 0));
            firstCharLengMap.put(str.charAt(0), maxLeng);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (firstCharLengMap.containsKey(map[i][j])) {
                    dfs(i, j, 1, firstCharLengMap.get(map[i][j]), map[i][j] + "");
                }
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < K; i++) {
            int cnt = wordMap.getOrDefault(wordArr[i], 0);
            sb.append(cnt).append("\n");
        }
        System.out.println(sb);
    }

    public static void dfs(int x, int y, int cnt, int leng, String str) {
        wordMap.put(str, wordMap.getOrDefault(str, 0) + 1);
        if (leng == cnt) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            int sx = x + idx[i];
            int sy = y + idy[i];

            if (sx < 0) {
                sx = N - 1;
            } else if (sx >= N) {
                sx = 0;
            }
            if (sy < 0) {
                sy = M - 1;
            } else if (sy >= M) {
                sy = 0;
            }
            dfs(sx, sy, cnt + 1, leng, str + map[sx][sy]);
        }
    }

}
