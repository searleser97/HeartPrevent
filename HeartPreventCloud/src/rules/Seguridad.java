package rules;

/**
 *
 * @author Silvester
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Seguridad {

    static String key = "123456789abcdefg"; // 128 bit key
    static String initVector = "RandomInitVector"; // 16 bytes IV
//Funcion Hash para crear la contarseña

    public String encriptaSHA1(String clave) {
        String sen = "";
        @SuppressWarnings("UnusedAssignment")
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            BigInteger hash = new BigInteger(1, md.digest(clave.getBytes()));
            sen = hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.print("no existe el algoritmo en messagedigest");
        }
        return sen;
    }

    public String encriptaAES(String clave) {

        byte[] encrypted = null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            encrypted = cipher.doFinal(clave.getBytes());

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decriptaAES(String clave) {
        byte[] original = null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            original = cipher.doFinal(Base64.getDecoder().decode(clave.getBytes()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
        }
        return new String(original);
    }
}
