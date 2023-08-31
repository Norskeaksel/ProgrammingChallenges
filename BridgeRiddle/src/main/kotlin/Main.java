import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Answer {
        int first;
        List<Integer> second;

        Answer(int first, List<Integer> second) {
            this.first = first;
            this.second = second;
        }
    }

    public static Answer bestMoves(int fastest, int fast, int slow, int slowest) {
        Answer answer1 = option1(fastest, slow, slowest);
        Answer answer2 = option2(fastest, fast, slow, slowest);
        if (answer1.first <= answer2.first) {
            return answer1;
        } else {
            return answer2;
        }
    }

    public static Answer option1(int fastest, int slow, int slowest) {
        List<Integer> moves = new ArrayList<>();
        moves.add(fastest);
        moves.add(fastest);
        moves.add(slow);
        moves.add(fastest);
        moves.add(fastest);
        moves.add(slowest);
        int time = fastest + slow + fastest + slowest;
        return new Answer(time, moves);
    }

    public static Answer option2(int fastest, int fast, int slow, int slowest) {
        List<Integer> moves = new ArrayList<>();
        moves.add(fastest);
        moves.add(slow);
        moves.add(slowest);
        moves.add(fast);
        moves.add(fastest);
        moves.add(fast);
        int time = fastest + slowest + fast + fast;
        return new Answer(time, moves);
    }

    public static Answer solve(List<Integer> people) {
        if (people.size() == 1) {
            List<Integer> moves = new ArrayList<>();
            moves.add(people.get(0));
            return new Answer(people.get(0), moves);
        }

        int fastest = people.get(0);
        int fast = people.get(1);
        List<Integer> moves = new ArrayList<>();
        moves.add(fastest);
        moves.add(fast);
        int time = fast;
        List<Integer> remainingPeople = new ArrayList<>(people);
        remainingPeople.remove(Integer.valueOf(fastest));
        remainingPeople.remove(Integer.valueOf(fast));

        while (remainingPeople.size() >= 2) {
            int slow = remainingPeople.get(remainingPeople.size() - 2);
            int slowest = remainingPeople.get(remainingPeople.size() - 1);
            Answer answer = bestMoves(fastest, fast, slow, slowest);
            time += answer.first;
            List<Integer> newMoves = answer.second;
            moves.addAll(newMoves);
            remainingPeople.remove(Integer.valueOf(slow));
            remainingPeople.remove(Integer.valueOf(slowest));
        }

        if (remainingPeople.size() == 1) {
            int remainingDude = remainingPeople.get(0);
            time += fastest + remainingDude;
            moves.addAll(new ArrayList<>(Arrays.asList(fastest, fastest, remainingDude)));
        }

        return new Answer(time, moves);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n-- > 0) {
            int p = sc.nextInt();
            List<Integer> people = new ArrayList<>();
            while (p-- > 0) {
                people.add(sc.nextInt());
            }
            people.sort(Integer::compare);
            Answer answer = solve(people);
            System.out.print(answer.first);
            boolean needExtraNewline = true;
            for (int i = 0; i < answer.second.size(); i++) {
                needExtraNewline = true;
                if (i % 3 == 0 || i % 3 == 2) {
                    System.out.println();
                    needExtraNewline = false;
                } else
                    System.out.print(" ");

                System.out.print(answer.second.get(i));
            }
            if (needExtraNewline) {
                System.out.println("\n");
            } else {
                System.out.println();
            }
        }
    }
}
