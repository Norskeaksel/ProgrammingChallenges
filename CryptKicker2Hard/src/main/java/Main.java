import java.util.*;

class State {

    Map<Character, Character> key = new HashMap<>();
    Set<String> remainingWords = new HashSet<>();
    Stack<String> remainingCipherWords = new Stack<>();

    State() {
    }

    State(State s) {
        key = new HashMap<>(s.key);
        remainingWords = new HashSet<>(s.remainingWords);
        remainingCipherWords = new Stack<>();
    }
}

public class Main {
    private static final List<Character> alphabet = getAlphabet();
    private static final Set<String> words = new HashSet<>();
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
        //initialize set of strings
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (int i = 0; i < t; i++) {
            if (i > 0)
                System.out.println();
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty())
                    break;
                lines.add(line);
            }

            Map<Character, Character> key = getKey(lines);
            if (key == null) {
                System.out.println("No solution.");
            } else {
                // System.err.println(key);
                List<String> plaintext = decrypt(lines, key);
                for (String line : plaintext) {
                    System.out.println(line);
                }
            }
        }
    }

    private static List<String> decrypt(List<String> cipherLines, Map<Character, Character> key) {
        List<String> text = new ArrayList<>();
        for (String cipherLine : cipherLines) {
            StringBuilder line = new StringBuilder();
            for (char c : cipherLine.toCharArray()) {
                if (c == ' ') {
                    line.append(' ');
                } else {
                    line.append(key.get(c));
                }
            }
            text.add(line.toString());
        }
        return text;
    }

    private static Map<Character, Character> getKey(List<String> lines) {
        String decryptionPhrase = "the quick brown fox jumps over the lazy dog";
        List<String> decryptionPhraseList = Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");
        Set<String> decryptionPhraseSet = new HashSet<>(decryptionPhraseList);
        for (String line : lines) {
            String[] lineWords = line.split(" ");
            if (lineWords.length == 9) {
                words.clear();
                for(String word : lineWords)
                    words.add(word);
                List<Map<Character, Character>> keys = crackKey(decryptionPhraseSet);
                for (Map<Character, Character> key : keys) {
                    Map<Character, Character> reversedKey = new HashMap<>();
                    for (Map.Entry<Character, Character> entry : key.entrySet()) {
                        reversedKey.put(entry.getValue(), entry.getKey());
                    }
                    List<String> decryption = decrypt(Collections.singletonList(line), reversedKey);
                    if (decryption.size()>0 && decryption.get(0).equals(decryptionPhrase))
                        return reversedKey;
                }
            }
        }
        return null;
    }

    private static List<Map<Character, Character>> crackKey(Set<String> cipherWordSet) {
        State root = new State();
        root.remainingWords.addAll(words);
        List<String> sortedCipherWords = new ArrayList<>(cipherWordSet);
        sortedCipherWords.sort(Comparator.comparingInt(String::length));
        root.remainingCipherWords.addAll(sortedCipherWords);

        Queue<State> queue = new LinkedList<>();
        queue.add(root);
        List<Map<Character, Character>> keys = new ArrayList<>();
        while (!queue.isEmpty()) {
            State currentState = queue.remove();
            String nextCipherWord = currentState.remainingCipherWords.pop();
            for (String word : words) {
                if (word.length() != nextCipherWord.length())
                    continue;
                Map<Character, Character> nextKey = nextKey(nextCipherWord, word, currentState.key);
                if (nextKey != null) {
                    State nextState = new State(currentState);
                    nextState.key = nextKey;
                    nextState.remainingWords.addAll(currentState.remainingWords);
                    nextState.remainingWords.remove(word);
                    nextState.remainingCipherWords.addAll(currentState.remainingCipherWords);
                    if (nextState.remainingCipherWords.isEmpty()) {
                        keys.add(nextState.key);
                    } else
                        queue.add(nextState);
                }
            }
        }
        return keys;
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

    private static List<Character> getAlphabet() {
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }
        return alphabet;
    }
}