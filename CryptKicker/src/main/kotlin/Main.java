import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<Character> alphabet = getAlphabet();
    private static List<String> words = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 0; i < n; i++) {
            String word = scanner.nextLine();
            words.add(word);
        }

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            List<String> cipherText = Arrays.asList(input.split(" "));
            if (input.isEmpty()) {
                break;
            }
            Map<Character, Character> key = crackKey(cipherText);
            decrypt(cipherText, key);
            System.out.println();
        }
    }

    private static Map<Character, Character> crackKey(List<String> cipherText) {
        Map<Integer, List<String>> wordGroups = words.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.toList()));
        Map<Integer, List<String>> cipherWordGroups = cipherText.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.groupingBy(String::length, Collectors.toList()));
        Map<Character, Character> key = new HashMap<>();

        if (wordGroups.keySet().containsAll(cipherWordGroups.keySet())) {
            for (Integer groupKey : cipherWordGroups.keySet()) {
                List<String> cipherWords = cipherWordGroups.get(groupKey);
                List<String> wordsInGroup = wordGroups.get(groupKey);
                Map<Character, Character> keyUpdates = mapWordsInGroup(cipherWords, wordsInGroup, key);
                if (keyUpdates == null) {
                    return mapToStar();
                }
                key.putAll(keyUpdates);
            }
        } else {
            return mapToStar();
        }

        return key;
    }

    private static Map<Character, Character> mapWordsInGroup(List<String> cipherWords, List<String> words, Map<Character, Character> key) {
        for (String cipherWord : cipherWords) {
            Map<Character, Character> mapping = null;
            for (String word : words) {
                mapping = mapWords(cipherWord, word);
                if (hasConflicts(mapping, key)) {
                    continue;
                }
                key.putAll(mapping);
                break;
            }
            if (mapping == null) {
                return null;
            }
        }
        return key;
    }

    private static Map<Character, Character> mapWords(String cipherWord, String word) {
        Map<Character, Character> mapping = new HashMap<>();
        for (int i = 0; i < cipherWord.length(); i++) {
            char cipherChar = cipherWord.charAt(i);
            char wordChar = word.charAt(i);
            if (!mapping.containsKey(cipherChar)) {
                mapping.put(cipherChar, wordChar);
            } else if (mapping.get(cipherChar) != wordChar) {
                return null;
            }
        }
        return mapping;
    }

    private static boolean hasConflicts(Map<Character, Character> mapping, Map<Character, Character> key) {
        if (mapping == null) {
            return true;
        }
        for (Map.Entry<Character, Character> entry : mapping.entrySet()) {
            char k = entry.getKey();
            char v = entry.getValue();
            if (key.containsKey(k) && key.get(k) != v) {
                return true;
            }
        }
        return false;
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
        List<String> decrypted = new ArrayList<>();

        boolean hasMissingCharacters = false;
        for (String word : cipherText) {
            StringBuilder decryptedWord = new StringBuilder();
            for (char c : word.toCharArray()) {
                if (key.containsKey(c)) {
                    decryptedWord.append(key.get(c));
                } else {
                    decryptedWord.append("*");
                    hasMissingCharacters = true;
                }
            }
            decrypted.add(decryptedWord.toString());
        }

        if (hasMissingCharacters) {
            for (String word : decrypted) {
                for (char c : word.toCharArray()) {
                    if (c == ' ') {
                        System.out.print(" ");
                    } else {
                        System.out.print("*");
                    }
                }
                System.out.print(" ");
            }
        } else {
            System.out.print(String.join(" ", decrypted));
        }
    }
}