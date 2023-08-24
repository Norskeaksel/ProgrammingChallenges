import java.util.*;

class State {

    Map<Character, Character> key = new HashMap<>();
    Set<String> remainingWords = new HashSet<>();
    Stack<String> remainingCipherWords = new Stack<>();

    State() {}
    State(State s) {
        key = new HashMap<>(s.key);
        remainingWords = new HashSet<>(s.remainingWords);
        remainingCipherWords = new Stack<>();
    }
}

public class Main {
    private static final List<Character> alphabet = getAlphabet();
    private static final Set<String> words = new HashSet<>();
    private static Map<Integer, Set<String>> wordGroups;

    private static Map<Integer, Set<String>> getWordGroups() {
        Map<Integer, Set<String>> wordGroups = new HashMap<>();
        for (String word : words) {
            int len = word.length();
            if (!wordGroups.containsKey(len)) {
                wordGroups.put(len, new HashSet<>());
            }
            wordGroups.get(len).add(word);
        }
        return wordGroups;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String word = scanner.nextLine();
            words.add(word);
        }
        wordGroups = getWordGroups();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            List<String> cipherText = Arrays.asList(input.split(" "));
            Set<String> cipherWordSet = new HashSet<>(cipherText);
            Map<Character, Character> key = crackKey(cipherWordSet);
            decrypt(cipherText, key);
        }
    }

    private static Map<Character, Character> crackKey(Set<String> cipherWordSet) {
        State root = new State();
        root.remainingWords.addAll(words);
        List<String> sortedCipherWords = new ArrayList<>(cipherWordSet);
        sortedCipherWords.sort(Comparator.comparingInt(String::length));
        root.remainingCipherWords.addAll(sortedCipherWords);

        Queue<State> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            String nextCipherWord = currentState.remainingCipherWords.pop();
            Set<String> possibleMappings = new HashSet<>(currentState.remainingWords);
            possibleMappings.retainAll(wordGroups.get(nextCipherWord.length()));
            for (String word : possibleMappings) {
                Map<Character, Character> nextKey = nextKey(nextCipherWord, word, currentState.key);
                if (nextKey != null) {
                    State nextState = new State(currentState);
                    nextState.key = nextKey;
                    nextState.remainingWords.addAll(currentState.remainingWords);
                    nextState.remainingWords.remove(word);
                    nextState.remainingCipherWords.addAll(currentState.remainingCipherWords);
                    if (nextState.remainingCipherWords.isEmpty()) {
                        return nextState.key;
                    }
                    queue.add(nextState);
                }
            }
        }
        return mapToStar();
    }

    private static Map<Character, Character> nextKey(String cipherWord, String word, Map<Character, Character> key) {
        Map<Character, Character> nextKey = new HashMap<>(key);

        for (int i = 0; i < cipherWord.length(); i++) {
            char cipherChar = cipherWord.charAt(i);
            char wordChar = word.charAt(i);
            if (!nextKey.containsKey(cipherChar)) {
                if (nextKey.containsValue(wordChar))
                    return null;
                nextKey.put(cipherChar, wordChar);
            } else if (nextKey.get(cipherChar) != wordChar) {
                return null;
            }
        }
        return nextKey;
    }

    private static Map<Character, Character> mapToStar() {
        Map<Character, Character> mapping = new HashMap<>();
        for (char c : alphabet) {
            mapping.put(c, '*');
        }
        return mapping;
    }

    private static List<Character> getAlphabet() {
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }
        return alphabet;
    }

    private static void decrypt(List<String> cipherText, Map<Character, Character> key) {
        boolean notFirst = false;
        for (String word : cipherText) {
            if (notFirst) {
                System.out.print(" ");
            }
            notFirst = true;
            for (char c : word.toCharArray()) {
                System.out.print(key.get(c));
            }
            // System.out.print(" ");
        }
        System.out.println();
    }
}