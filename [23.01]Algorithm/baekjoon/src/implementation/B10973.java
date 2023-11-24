package implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
이전 순열
N : 주어지는 수

 */
public class B10973 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }


        if (fn(arr)) {
            for (int i = 0; i < n; i++) {
                sb.append(arr[i]).append(" ");
            }
        } else {
            sb.append(-1);
        }

        System.out.println(sb);
    }

    public static boolean fn(int arr[]) {
        int left = arr.length - 1;
        while (left > 0 && arr[left - 1] <= arr[left]) {
            left--;
        }

        if (left <= 0) {
            return false;
        }

        int right = arr.length - 1;
        while (arr[right] >= arr[left - 1]) {
            right--;
        }

        swap(arr, left-1, right);

        right = arr.length - 1;
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
        return true;
    }

    public static void swap(int arr[], int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }
}
