package SpringProject.WebCommunity.Service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;

@Log
@Service
public class SecurityService {

    public static final int SALT_LENGTH = 24;  // 24 = 4 * ceil(SALT_BYTES / 3)
    public static final int HASH_LENGTH = 24;  // 24 = 4 * ceil(HASH_BYTES / 3)
    private static final int SALT_BYTES = 16;
    private static final int HASH_BYTES = 16;
    private static final int ITERATE_COUNT = 65536;
    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    // https://en.wikipedia.org/wiki/Salt_(cryptography)
    public String getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        return encodeBinary(salt);
    }

    public String encodePassword(String password, String salt) {
        int bitLength = HASH_BYTES * 8;
        char[] _password = password.toCharArray();
        byte[] _salt = decodeBinary(salt);
        String hash = null;

        try {
            KeySpec spec = new PBEKeySpec(_password, _salt, ITERATE_COUNT, bitLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = factory.generateSecret(spec);
            hash = encodeBinary(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.log(Level.SEVERE, "Failed to encode password!", e);
        }

        return hash;
    }

    private String encodeBinary(byte[] bytes) {
        return base64Encoder.encodeToString(bytes);
    }

    private byte[] decodeBinary(String base64) {
        return base64Decoder.decode(base64);
    }

}
