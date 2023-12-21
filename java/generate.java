import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+";

    public static String generatePassword(String input, int length) {
        if(length > 64) {
            System.out.println("Length of the password cannot be greater than 64");
            return null;
        }
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            String allCharacters = UPPER + LOWER + DIGITS + SPECIAL;
            Random random = new Random();

            StringBuilder password = new StringBuilder();
            for (int i = 0; i < 12; i++) {
                int randomIndex = random.nextInt(hexString.length());
                char randomChar = hexString.charAt(randomIndex);
                password.append(randomChar);

                if (i == 0) {
                    password.append(UPPER.charAt(random.nextInt(UPPER.length())));
                } else if (i == 4) {
                    password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
                } else if (i == 8) {
                    password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
                }
            }

            return password.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String input;
        int length;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your biometric ID: ");
        input = in.next();
        System.out.println("Enter the length of the password: ");
        length = in.nextInt();
        String generatedPassword = generatePassword(input, length);
        System.out.println("Generated Password: " + generatedPassword);
    }
}
