import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] numbers = line.split(" ");
            ArrayList<Integer> pancakes = new ArrayList<>();
            for (String number : numbers) {
                pancakes.add(Integer.parseInt(number));
            }
            System.out.println(line);
            System.err.println("Input: " + pancakes);
            ArrayList<Integer> flipIndexes = new ArrayList<>();
            for (int offset = 0; offset < pancakes.size(); offset++) {
                int maxIndex = 0;
                int end = pancakes.size() - offset - 1;
                for (int i = 0; i <= end; i++) {
                    if (pancakes.get(i) > pancakes.get(maxIndex)) {
                        maxIndex = i;
                    }
                }
                if (maxIndex < end) {
                    System.err.println("maxIndex: " + maxIndex);
                    if(maxIndex > 0){
                        flipPancackes(pancakes, maxIndex);
                        flipIndexes.add(pancakes.size() - maxIndex);
                        System.err.println("Post flip: " + pancakes);
                    }
                    flipPancackes(pancakes, end);
                    flipIndexes.add(pancakes.size() - end);
                    System.err.println("Post flip: " + pancakes);
                }
            }
            for (Integer i : flipIndexes) {
                System.out.print(i + " ");
            }
            System.out.println(0);
        }
    }

    private static void flipPancackes(ArrayList<Integer> pancakes, int end) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= end; i++) {
            stack.push(pancakes.get(i));
        }
        for (int i = 0; i <= end; i++) {
            pancakes.set(i, stack.pop());
        }
    }
}