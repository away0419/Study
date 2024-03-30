package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B1525 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String[][] arr = new String[3][3];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                arr[i][j] = st.nextToken();
                sb.append(arr[i][j]);
            }
        }

        int answer = bfs(sb);

        System.out.println(answer);
    }

    public static int bfs(StringBuilder init) {
        Map<String, Integer> map = new HashMap<>();
        Queue<String> q = new ArrayDeque<>();
        int[] idx = {0, 0, -1, 1};
        int[] idy = {-1, 1, 0, 0};

        q.add(init.toString());
        map.put(init.toString(), 0);

        while (!q.isEmpty()) {
            String cur = q.poll();
            int emptyIndex = cur.indexOf("0");
            int empytX = emptyIndex / 3;
            int empytY = emptyIndex % 3;

            for (int i = 0; i < 4; i++) {
                int sx = idx[i] + empytX;
                int sy = idy[i] + empytY;

                if (sx < 0 || sy < 0 || sx >= 3 || sy >= 3) {
                    continue;
                }

                StringBuilder sb = new StringBuilder(cur);
                int changeIndx = sx * 3 + sy;
                char temp = sb.charAt(changeIndx);

                sb.setCharAt(changeIndx, '0');
                sb.setCharAt(emptyIndex, temp);

                if (map.containsKey(sb.toString())) {
                    continue;
                }

                q.add(sb.toString());
                map.put(sb.toString(), map.get(cur) + 1);
            }
        }

        if (map.containsKey("123456780")) {
            return map.get("123456780");
        }

        return -1;
    }


}
