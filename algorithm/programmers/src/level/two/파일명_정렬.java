package level.two;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 파일명_정렬 {
    class Solution {
        public String[] solution(String[] files) {
            Pattern p = Pattern.compile("([\\D]+)([\\d]{1,5})");

            Arrays.sort(files, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    Matcher m1 = p.matcher(s1.toLowerCase());
                    Matcher m2 = p.matcher(s2.toLowerCase());
                    m1.find();
                    m2.find();

                    if(!m1.group(1).equals(m2.group(1))) {
                        return m1.group(1).compareTo(m2.group(1));
                    } else {
                        return Integer.parseInt(m1.group(2)) - Integer.parseInt(m2.group(2));
                    }
                }
            });

            return files;
        }
    }
}
