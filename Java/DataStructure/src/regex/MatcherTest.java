package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherTest {
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$"); // 영문자만
		String val = "abcdef"; // 대상문자열

		Matcher matcher = pattern.matcher(val);
		System.out.println(matcher.find());
		
		Pattern pattern2 = Pattern.compile("\\bcat\\b");
	    Matcher matcher2 = pattern2.matcher("cat cat cat cattie cat");
	    int count = 0;
	    while(matcher2.find()) {
	    	System.out.println("group(): " + matcher2.group());
	        System.out.println("Match number " + ++count);
	        System.out.println("start(): " + matcher2.start());
	        System.out.println("end(): " + matcher2.end());
	    }
	}
}
