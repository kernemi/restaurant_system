package RS.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256"; // Simple and effective hashing algorithm

    // Method to generate a simple salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes salt
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt); // Return Base64 encoded salt
    }

    // Method to hash the password using SHA-256 and salt
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(Base64.getDecoder().decode(salt)); // Add salt to the hash
            byte[] hashedBytes = digest.digest(password.getBytes()); // Hash the password with salt
            return Base64.getEncoder().encodeToString(hashedBytes); // Return Base64 encoded hash
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    // Method to validate the password by comparing hashes
    public static boolean validatePassword(String inputPassword, String storedHash, String storedSalt) {
        String inputHash = hashPassword(inputPassword, storedSalt);
        return inputHash.equals(storedHash); // Check if the hashed input matches the stored hash
    }
}
