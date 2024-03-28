import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*_=+-/";
    private static final String AMBIGUOUS = "l1Io0";

    private static final String[] WORD_LIST = {"apple", "banana", "orange", "grape", "pineapple", "mango", "strawberry", "blueberry", "watermelon", "kiwi"};

    private static SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        int length = 8;
        int numPasswords = 5;
        boolean includeUppercase = true;
        boolean includeLowercase = true;
        boolean includeDigits = true;
        boolean includeSpecial = true;
        int minUppercase = 1;
        int minLowercase = 1;
        int minDigits = 1;
        int minSpecial = 1;
        boolean excludeAmbiguous = true;

        String[] passwords = generatePasswords(length, numPasswords, includeUppercase, includeLowercase, includeDigits, includeSpecial,
                minUppercase, minLowercase, minDigits, minSpecial, excludeAmbiguous);

        for (String password : passwords) {
            System.out.println("Generated Password: " + password);
        }

        // Generate passphrase-style password
        String passphrase = generatePassphrase(4);
        System.out.println("Generated Passphrase: " + passphrase);
    }

    public static String[] generatePasswords(int length, int numPasswords, boolean includeUppercase, boolean includeLowercase,
                                             boolean includeDigits, boolean includeSpecial, int minUppercase, int minLowercase,
                                             int minDigits, int minSpecial, boolean excludeAmbiguous) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < numPasswords; i++) {
            StringBuilder password = new StringBuilder();
            if (includeUppercase) appendRandomCharacters(password, UPPER, minUppercase);
            if (includeLowercase) appendRandomCharacters(password, LOWER, minLowercase);
            if (includeDigits) appendRandomCharacters(password, DIGITS, minDigits);
            if (includeSpecial) appendRandomCharacters(password, SPECIAL, minSpecial);
            if (excludeAmbiguous) excludeAmbiguousCharacters(password);
            appendRandomCharacters(password, UPPER + LOWER + DIGITS + SPECIAL, length - password.length());
            shuffleString(password);
            passwords.add(password.toString());
        }
        return passwords.toArray(new String[0]);
    }

    private static void appendRandomCharacters(StringBuilder password, String characters, int count) {
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
    }

   private static void excludeAmbiguousCharacters(StringBuilder password) {
    for (char c : AMBIGUOUS.toCharArray()) {
        int index = password.indexOf(String.valueOf(c));
        if (index != -1) {
            password.deleteCharAt(index);
        }
    }
   }


    private static void shuffleString(StringBuilder password) {
        List<Character> characters = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters, random);
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : characters) {
            shuffledPassword.append(c);
        }
        password.setLength(0);
        password.append(shuffledPassword);
    }

    public static String generatePassphrase(int numWords) {
        StringBuilder passphrase = new StringBuilder();
        for (int i = 0; i < numWords; i++) {
            int randomIndex = random.nextInt(WORD_LIST.length);
            passphrase.append(WORD_LIST[randomIndex]);
            if (i < numWords - 1) passphrase.append(" ");
        }
        return passphrase.toString();
    }
}
