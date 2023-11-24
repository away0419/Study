package datastructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/*
애너그램 그룹
sortWord<String, Integer> : 정렬한 단어가 현재까지 나온 횟수
sortWordIndex<String, Integer> : 정렬한 단어의 인덱스
list[] : 정렬 전 단어들이 들어갈 리스트 (인덱스는 sortWordIndex)

 */
public class B6566 {

    public static class Info implements Comparable<Info> {
        int cnt;
        List<String> list;

        public Info(int cnt, List<String> list) {
            this.cnt = cnt;
            this.list = list;
        }

        @Override
        public int compareTo(Info o) {
            if (this.cnt == o.cnt) {
                return this.list.get(0).compareTo(o.list.get(0));
            }
            return o.cnt - this.cnt;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int curIndex = 0;
        Map<String, Integer> sortWordMap = new HashMap<>();
        Map<String, Integer> sortWordIndexMap = new HashMap<>();
        List<Set<String>> lists = new ArrayList<>();

        String input = br.readLine();
        while (input != null && !"".equals(input)) {
            String sortWord = input.chars().sorted()
                    .collect(StringBuilder::new,
                            StringBuilder::appendCodePoint,
                            StringBuilder::append)
                    .toString();

            if (!sortWordIndexMap.containsKey(sortWord)) {
                lists.add(new HashSet<>());
                sortWordIndexMap.put(sortWord, curIndex++);
                sortWordMap.put(sortWord, 1);
            } else {
                sortWordMap.put(sortWord, sortWordMap.get(sortWord) + 1);
            }

            int index = sortWordIndexMap.get(sortWord);
            lists.get(index).add(input);

            input = br.readLine();
        }


        List<Info> infoList = new ArrayList<>();
        for (String sortWord :
                sortWordMap.keySet()) {
            int index = sortWordIndexMap.get(sortWord);
            List<String> list = lists.get(index).stream().sorted().collect(Collectors.toList());
            infoList.add(new Info(sortWordMap.get(sortWord), list));
        }

        Collections.sort(infoList);
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (int i = 0; i < infoList.size(); i++) {
            Info info = infoList.get(i);
            sb.append("Group of size ").append(info.cnt).append(": ");
            for (int j = 0; j < info.list.size(); j++) {
                sb.append(info.list.get(j)).append(" ");
            }
            sb.append(".\n");
            if (++cnt == 5) {
                break;
            }
        }

        System.out.println(sb);
    }


}


