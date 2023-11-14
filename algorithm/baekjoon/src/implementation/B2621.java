package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
캬드게임

 */
public class B2621 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        Map<Integer, Integer> map = new TreeMap<>();
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());
            Character ch = st.nextToken().charAt(0);
            int k = Integer.parseInt(st.nextToken());
            set.add(ch);
            map.put(k, map.getOrDefault(k, 0) + 1);
        }

        int res = 0;
        res = check1(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check2(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check3(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check4(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check5(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check6(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check7(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check8(map, set);
        if (res != 0) {
            System.out.println(res);
            return;
        }

        res = check9(map, set);
        if (res != 0) {
            System.out.println(res);
        }

    }


    public static int check1(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        if (set.size() == 1 && map.size() == 5) {
            List<Integer> list = new ArrayList<>(map.keySet());
            int k = list.get(0);
            for (int i = 1; i < 5; i++) {
                int temp = list.get(i);
                if (temp != k + 1) {
                    return res;
                }
                k = temp;
            }
            res = 900 + k;
        }
        return res;
    }


    public static int check2(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        if (map.size() == 2) {
            for (int key :
                    map.keySet()) {
                if (map.get(key) == 4) {
                    res = 800 + key;
                }
            }
        }
        return res;
    }

    public static int check3(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        if (map.size() == 2) {
            boolean check = false;
            int k = 0;
            for (int key :
                    map.keySet()) {
                int cur = map.get(key);
                if (cur == 3 || cur == 2) {
                    if (check) {
                        if (cur == 3) {
                            k += key * 10;
                        } else {
                            k += key;
                        }
                        res = k;
                    } else {
                        check = true;
                        k += 700;
                        if (cur == 3) {
                            k += key * 10;
                        } else {
                            k += key;
                        }
                    }
                } else {
                    return res;
                }
            }
        }
        return res;
    }

    public static int check4(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        if (set.size() == 1) {
            int k = 0;
            for (int key :
                    map.keySet()) {
                k = key;
            }
            res = 600 + k;
        }
        return res;
    }

    public static int check5(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        if(map.size()==5){
            List<Integer> list = new ArrayList<>(map.keySet());
            int k = list.get(0);
            for (int i = 1; i < 5; i++) {
                int temp = list.get(i);
                if (temp != k + 1) {
                    return res;
                }
                k = temp;
            }
            res = 500 + k;
        }
        return res;
    }

    public static int check6(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        for (int key :
                map.keySet()) {
            if (map.get(key) == 3) {
                res = 400 + key;
            }
        }
        return res;
    }

    public static int check7(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        if (map.size() == 3) {
            boolean check = false;
            int k = 0;
            for (int key :
                    map.keySet()) {
                int cur = map.get(key);
                if (cur == 2) {
                    if (check) {
                        k += key * 10;
                        res = k;
                    } else {
                        check = true;
                        k += 300 + key;
                    }
                }
            }
        }
        return res;
    }

    public static int check8(Map<Integer, Integer> map, Set<Character> set) {
        int res = 0;
        for (int key :
                map.keySet()) {
            int cur = map.get(key);
            if (cur == 2) {
                res += 200 + key;
            }
        }
        return res;
    }

    public static int check9(Map<Integer, Integer> map, Set<Character> set) {
        int k = 0;
        for (int key :
                map.keySet()) {
            k = key;
        }
        return 100 + k;
    }


}
