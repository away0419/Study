package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
단어 뒤집기2
S : 주어지는 문자열
arr1[] : 주어지는 문자열의 문자 배열
arr2[] : 정답 배열

 */
public class B17413 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        char arr1[] = s.toCharArray();
        char arr2[] = s.toCharArray();
        int start = 0;
        int end = 0;
        int cnt = 0;

        for (int i = 0; i < arr1.length; i++) {
            char cur = arr1[i];

            if (cur == '<') {
                end = i;
                if (cnt == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(start);

                    for (int j = start; j < end; j++) {
                        if (arr2[j] == ' ') {
                            list.add(j);
                        }
                    }

                    list.add(end);

                    for (int j = 0; j < list.size() - 1; j++) {
                        int ss = list.get(j);
                        if (ss != start) {
                            ss++;
                        }
                        int ee = list.get(j + 1);

                        for (int k = ss; k < ee; k++) {
                            arr2[ee + ss - k - 1] = arr1[k];
                        }
                    }
                }
                cnt++;
            } else if (cur == '>') {
                start = i + 1;
                cnt--;
            } else if (cur == ' ' && cnt == 0 ){
                for (int k = start; k < i; k++) {
                    arr2[start + i - k - 1] = arr1[k];
                }
                start = i + 1;
            }else if(cur != ' ' && i==arr1.length-1){
                for (int k = start; k < arr1.length; k++) {
                    arr2[start + arr1.length - k - 1] = arr1[k];
                }
            }

        }



        System.out.println(String.valueOf(arr2));

    }
}
