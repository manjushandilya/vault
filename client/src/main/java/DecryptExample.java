import java.util.Base64;

public class DecryptExample {

    private static final String PREFIX = "Base64:";

    public static boolean isEncrypted(String text) throws Exception {
        return text != null && text.startsWith(PREFIX);
    }

    public static String encrypt(final String plainText) throws Exception {
        final StringBuilder ret = new StringBuilder(PREFIX);
        ret.append(Base64.getEncoder().encodeToString(plainText.getBytes()));
        return ret.toString();
    }

    public static String decrypt(final String cipherText) throws Exception {
        if (cipherText == null || !isEncrypted(cipherText)) {
            return cipherText;
        }
        final String payload = cipherText.substring(PREFIX.length());
        return new String(Base64.getDecoder().decode(payload.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        String plainText = "the quick brown fox jumps over the lazy dog";
        System.out.println("Plaintext: " + plainText);

        String cipherText = encrypt(plainText);
        System.out.println("Ciphertext: " + cipherText);

        String decryptedText = decrypt(cipherText);
        System.out.println("DecryptedText: " + decryptedText);

        System.out.println("Plaintext.isEncrypted: " + isEncrypted(plainText));
        System.out.println("Ciphertext.isEncrypted: " + isEncrypted(cipherText));

    }

}
