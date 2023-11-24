package regex;

import java.util.regex.Pattern;

public class PatternTest {
	public static void main(String[] args) {
		String pattern = "^[0-9]*$"; //숫자만
        String val = "123456789"; //대상문자열
    
        boolean regex = Pattern.matches(pattern, val);
        System.out.println(regex);
        
        String pattern2 = "(\\w)(\\s+)([\\w])";
        System.out.println("Hello     World".replaceAll(pattern2, "-"));

        pattern = "(\\w)(\\s+)([\\w])";
        System.out.println("Hello     World".replaceAll(pattern2, "$1-$3"));
	}
}
