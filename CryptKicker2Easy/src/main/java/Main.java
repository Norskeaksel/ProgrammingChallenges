import java.util.*;

public class Main {
    static String decryptionPhrase = "the quick brown fox jumps over the lazy dog";

    public static void main(String[] args) {
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
                for (String line : lines) {
                    String plaintext = decrypt(line, key);
                    System.out.println(plaintext);
                }
            }
        }
    }

    static Map<Character, Character> getKey(List<String> lines) {
        for (String line : lines) {
            Map<Character, Character> key = new HashMap<>();
            if (line.length() == decryptionPhrase.length()) {
                for (int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == ' ')
                        continue;
                    key.put(line.charAt(i), decryptionPhrase.charAt(i));
                }
                String plaintext = decrypt(line, key);
                if (plaintext != null && plaintext.equals(decryptionPhrase)) {
                    return key;
                }
            }

        }
        return null;
    }

    private static String decrypt(String cipherLine, Map<Character, Character> key) {
        StringBuilder line = new StringBuilder();
        for (char c : cipherLine.toCharArray()) {
            if (c == ' ') {
                line.append(' ');
            } else {
                if(!key.containsKey(c))
                    return null;
                line.append(key.get(c));
            }
        }
        return line.toString();
    }
}