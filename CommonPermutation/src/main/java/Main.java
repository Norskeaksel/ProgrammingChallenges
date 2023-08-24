import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String a = scanner.nextLine();
            String b = scanner.nextLine();

            String x = findLongestCommonSubsequence(a, b);
            System.out.println(x);
        }
    }

    public static String findLongestCommonSubsequence(String a, String b) {
        char[] sortedA = sortedCharArray(a.toCharArray());
        char[] sortedB = sortedCharArray(b.toCharArray());

        StringBuilder x = new StringBuilder();

        int i = 0;
        int j = 0;

        while (i < sortedA.length && j < sortedB.length) {
            if (sortedA[i] == sortedB[j]) {
                x.append(sortedA[i]);
                i++;
                j++;
            } else if (sortedA[i] < sortedB[j]) {
                i++;
            } else {
                j++;
            }
        }

        return x.toString();
    }

    public static char[] sortedCharArray(char[] arr) {
        Arrays.sort(arr);
        return arr;
    }
}