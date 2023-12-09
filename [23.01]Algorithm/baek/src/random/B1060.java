package random;

import java.util.*;

public class B1060 {

    static class Info {
        long num, goodNum, cnt, limi, interv;
        boolean chk;

        Info(long num, boolean chk, long goodNum, long cnt, long limi, long interv) {
            this.num = num;
            this.chk = chk;
            this.goodNum = goodNum;
            this.cnt = cnt;
            this.limi = limi;
            this.interv = interv;
        }
    }

    static class InfoComparator implements Comparator<Info> {
        public int compare(Info a, Info b) {
            if (a.goodNum == b.goodNum) {
                return Long.compare(a.num, b.num);
            }
            return Long.compare(b.goodNum, a.goodNum);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int L = scanner.nextInt();
        List<Long> V = new ArrayList<>();
        PriorityQueue<Info> PQ = new PriorityQueue<>(new InfoComparator());

        for (int i = 0; i < L; i++) {
            V.add(scanner.nextLong());
        }

        long N = scanner.nextLong();

        Collections.sort(V);

        for (long e : V) {
            PQ.add(new Info(e, false, 0, 0, 0, 0));
        }

        V.add(0, 0L);

        for (int i = 0; i < V.size() - 1; i++) {
            long left = V.get(i) + 1;
            long right = V.get(i + 1) - 1;

            if (left > right) {
                continue;
            }

            long interval = right - left + 1;

            if (interval % 2 == 0) {
                PQ.add(new Info(left, true, interval - 1, 1, interval / 2, interval));
                PQ.add(new Info(right, false, interval - 1, 1, interval / 2, interval));
            } else {
                PQ.add(new Info(left, true, interval - 1, 1, interval / 2 + 1, interval));

                if (interval > 1) {
                    PQ.add(new Info(right, false, interval - 1, 1, interval / 2, interval));
                }
            }
        }

        while (!PQ.isEmpty() && N > 0) {
            Info tmp = PQ.poll();
            System.out.print(tmp.num + " ");

            if (tmp.cnt < tmp.limi) {
                long newNum = tmp.num + (tmp.chk ? 1 : -1);
                long newCnt = tmp.cnt + 1;
                long newGoodNum = tmp.goodNum + tmp.interv - 2 * tmp.cnt;

                PQ.add(new Info(newNum, tmp.chk, newGoodNum, newCnt, tmp.limi, tmp.interv));
            }

            N--;
        }

        long idx = V.get(V.size() - 1) + 1;
        while (N > 0) {
            System.out.print(idx++ + " ");
            N--;
        }
    }
}
