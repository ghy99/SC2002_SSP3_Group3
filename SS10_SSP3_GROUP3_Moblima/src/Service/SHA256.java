package Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author CHEW ZHI QI
 * SHA256 class for hashing admin password
 */
public class SHA256 {
    /**
     * Takes in string data and hash is with sha256
     * @param data Data
     * @return Hashed string
     */
    public static String toString(String data) throws NoSuchAlgorithmException {
        //Hashing Password
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hash);
    }
}