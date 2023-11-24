package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
꿀벌

 */
public class B9733 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cnt = 0;
        Map<String, Integer> map = new HashMap<>();
        String str = br.readLine();
        String[] works = {"Re", "Pt", "Cc", "Ea", "Tb", "Cm", "Ex"};
        while (str != null && !"".equals(str) && cnt < 24) {
            StringTokenizer st = new StringTokenizer(str);
            while (st.hasMoreTokens()) {
                String work = st.nextToken();
                cnt++;
                map.put(work, map.getOrDefault(work, 0) + 1);
                if (cnt == 24) break;
            }
            str = br.readLine();
        }
        StringBuilder sb = new StringBuilder();
        for (String work :
                works) {
            int workCnt = map.getOrDefault(work, 0);
            float f = 0f;
            if (cnt > 0) {
                f = (float) workCnt / cnt;
            }
            sb.append(work).append(" ").append(workCnt).append(String.format(" %.2f", f)).append("\n");
        }

        sb.append("Total ").append(cnt).append(" 1.00");

        System.out.println(sb);
    }


}
