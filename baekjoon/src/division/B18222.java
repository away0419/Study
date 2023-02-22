package division;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/*
 * 투에-모스 문자열
 * N : 주어진 수
 */
public class B18222 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		BigInteger bi = new BigInteger(br.readLine());
		BigInteger b1 = new BigInteger("1");
		BigInteger b2 = new BigInteger("2");
		BigInteger b0 = new BigInteger("0");
		bi=bi.subtract(b1);

		BigInteger answer = new BigInteger("0");
		while (bi.compareTo(b0) > 0) {
			if (bi.remainder(b2).compareTo(b0) == 0) {
				bi = bi.divide(b2).subtract(b1);
			} else {
				bi = bi.divide(b2);
			}
			answer=answer.add(b1);
		}
		
		if (answer.remainder(b2).compareTo(b0) == 0) {
			System.out.println(0);
		} else {
			System.out.println(1);
		}
	}

}
