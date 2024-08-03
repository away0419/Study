package random;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class B23059_3 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        Map<String, Integer> indexMap = new HashMap<>();
        Map<Integer, String> stringMap = new HashMap<>();
        int[] levels = new int[N];
        List<Integer>[] edgeList = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            edgeList[i] = new ArrayList<>();
        }


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String A = st.nextToken();
            String B = st.nextToken();

            if(!indexMap.containsKey(A)){
                indexMap.put(A, indexMap.size()+1);
                stringMap.put(indexMap.size(),A);
            }

            if(!indexMap.containsKey(B)){
                indexMap.put(B, indexMap.size()+1);
                stringMap.put(indexMap.size(),B);
            }

            int aIndex = indexMap.get(A);
            int bIndex = indexMap.get(B);

            edgeList[aIndex].add(bIndex);
            levels[bIndex]++;

        }


    }

}
