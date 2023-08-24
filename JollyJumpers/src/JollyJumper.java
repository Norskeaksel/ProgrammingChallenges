import java.util.Arrays;
import java.util.Scanner;

class JollyJumper {
    // This program will read an array of integers and make an array based on the absolute difference between each element and its left neighbour.
    // It will then check if is all the numbers from 1 to n-1 are in the array
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n < 1) {
                System.out.println("Not jolly");
                return;
            }
            int[] input = new int[n];
            for (int i = 0; i < n; i++) {
                int a = sc.nextInt();
                input[i] = a;
            }
            System.err.println("input: " + Arrays.toString(input));
            int[] absoluteDifferences = new int[n - 1];
            for (int i = 0; i < n - 1; i++) {
                absoluteDifferences[i] = Math.abs(input[i] - input[i + 1]);
            }
            System.err.println("absoluteDifferences: " + Arrays.toString(absoluteDifferences));
            int[] result = Arrays.stream(absoluteDifferences).sorted().toArray();
            System.err.println("sorted result: " + Arrays.toString(result));
            boolean jolly = true;
            for (int i = 0; i < n - 1; i++) {
                if (result[i] != i + 1) {
                    jolly = false;
                    break;
                }
            }
            if (jolly) {
                System.out.println("Jolly");
            } else
                System.out.println("Not jolly");
        }
    }
}