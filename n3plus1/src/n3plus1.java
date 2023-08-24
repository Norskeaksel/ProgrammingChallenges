import java.util.Scanner;

import static java.lang.Math.max;


class Main {
    // This program will loop through the numbers i to j and find the number which generates the most iterations when using the Collatz conjecture.
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            int i = input.nextInt(); // lower bound
            int j = input.nextInt(); // upper bound
            int a = i, b = j;

            if (i > j) {
                int temp = i;
                i = j;
                j = temp;
            }

            int c = 0;
            for (int k = i; k <= j; k++) {
                c = max(c, collatzConjecture(k));
            }
            System.out.println(a + " " + b + " " + c);
        }
    }
        public static int collatzConjecture ( int n){
            int c = 1;
            while (n != 1) {
                c++;
                if (n % 2 == 1)
                    n = n * 3 + 1;
                else
                    n /= 2;
            }
            return c;
        }
}