package rules;

/**
 *
 * @author Silvester
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
////////////////////////////////////EncryptionUtil.java imports
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
//import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.internet.InternetAddress;

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

////////////////////////////////////EncriptionUtil.java methods
    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    /**
     * String to hold the name of the private key file.
     */
    public static final String PRIVATE_KEY_FILE = "E:/1AARedMedicaWakeUp/keys/private.key";
    //}
    public static final String PUBLIC_KEY_FILE = "E:/1AARedMedicaWakeUp/keys/public.key";
    /**
     * Generate key which contains a pair of private and public key using 1024
     * bytes. Store the set of keys in Prvate.key and Public.key files.
     *
     */
    public static String chiperTexto = "";
    public static String chiperTexto2 = "";

    public static void generateKey() {

        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(512);
            final KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);

            // Create files to store public and private key
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();

            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            // Saving the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();

//      Key pubKey = key.getPublic();
//       Key privKey = key.getPrivate();
//byte[] privateKeyBytes = privKey.getEncoded();
//      byte[] publicKeyBytes = pubKey.getEncoded();
//chiperTexto = new String(Base64.getEncoder().encode(publicKeyBytes));
//chiperTexto2 = new String(Base64.getEncoder().encode(privateKeyBytes));
//        System.out.println("pubKey1: "+chiperTexto);
//        System.out.println("privKey1: "+chiperTexto2);
        } catch (NoSuchAlgorithmException | IOException e) {
        }

    }

    /**
     * The method checks if the pair of public and private key has been
     * generated.
     *
     * @return flag indicating if the pair of keys were generated.
     */
    public static boolean areKeysPresent() {

        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Encrypt the plain text using public key.
     *
     * @param text : original plain text
     * @param key :The public key
     * @return Encrypted text
     */
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        }
        return cipherText;
    }

    /**
     * Decrypt text using private key.
     *
     * @param text :encrypted text
     * @param key :The private key
     * @return plain text
     */
    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
        }

        return new String(dectyptedText);
    }

    /**
     * Test the EncryptionUtil
     *
     * @param base64string
     * @return
     */
    public String decripta(String base64string) {
        String plainText = "";
        try {
            // Check if the pair of keys are present else generate those.
            if (!areKeysPresent()) {
                // Method generates a pair of keys using the RSA algorithm and stores it
                // in their respective files
                generateKey();
            }

//      final String originalText = "texto prueba no cuenta";
            ObjectInputStream inputStream = null;

            // Encrypt the string using the public key
//Key privKey = key.getPrivate();
//      inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
//      final PublicKey publicKey = (PublicKey) inputStream.readObject();
//      final byte[] cipherText = encrypt(originalText, publicKey);
//      String chiperText = new String(Base64.getEncoder().encode(cipherText));
            // Decrypt the cipher text using the private key.
            Config.Defaults conf = new Config.Defaults();
            inputStream = new ObjectInputStream(new FileInputStream(conf.privateKeyPath));
            final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
            final byte[] decoded = Base64.getDecoder().decode(base64string.getBytes());
            plainText = decrypt(decoded, privateKey);

//      byte[] privateKeyBytes = privateKey.getEncoded();
//      byte[] publicKeyBytes = publicKey.getEncoded();
//chiperTexto = new String(Base64.getEncoder().encode(publicKeyBytes));
//chiperTexto2 = new String(Base64.getEncoder().encode(privateKeyBytes));
//        System.out.println("pubKey2: "+chiperTexto);
//        System.out.println("privKey2: "+chiperTexto2);
//       Printing the Original, Encrypted and Decrypted Text
//      System.out.println("Original: " + originalText);
//      System.out.println("Encrypted: " + chiperText);
//      System.out.println("Decrypted: " + plainText);
//System.out.println("project location: " + Public_File_key);
        } catch (IOException | ClassNotFoundException e) {
        }
        return plainText;
    }

    public boolean validaOPC(String opc, String str) {
        switch (opc) {
            case "1":
                boolean validaMail = true;
                try {
                    InternetAddress internetAddress = new InternetAddress(str);
                    internetAddress.validate();
                    validaMail = false;
                } catch (javax.mail.internet.AddressException e) {
                    System.out.println(e.getMessage());
                }
                return validaMail;
            case "2":
                if (str.length() < 11 && str.length() > 7) {
                    return validaNum(str);
                } else {
                    return true;
                }
            case "3":

                if (str.length() > 3 || str.length() < 2) {
                    return true;
                } else {
                    return validaNum(str);
                }
        }
        return true;
    }

//  public boolean validaTel(String tel)
//  {
//      if(tel.length()<11 && tel.length()>7)
//      {
//        return validaNum(tel);
//      }
//      else
//      {
//        return true;  
//      }
//  }
    public boolean validaNum(String str) {
        int x;
        for (int j = 0; j < str.length(); j++) {
            x = (int) str.charAt(j);
            if (x < 48 || x > 57) {
                return true;
            }
        }
        return false;
    }

    public boolean validaLe(String str) {

        int x = 0;
        String restr = "";
        boolean checa;
        if (str.contains("&apos;")) {
            String[] strs = str.split("&apos;");

            for (int i = 0; i < strs.length; i++) {
                restr = strs[i];
                for (int j = 0; j < restr.length(); j++) {
                    x = (int) restr.charAt(j);
                    checa = ((x > 64 && x < 91) || (x > 96 && x < 123) || (x == 195) || (x == 145) || (x == 177) || (x == 32));
                    if (!checa) {
                        return true;
                    }
                }
            }
        } else {
            for (int j = 0; j < str.length(); j++) {
                x = (int) str.charAt(j);
                checa = ((x > 64 && x < 91) || (x > 96 && x < 123) || (x == 195) || (x == 145) || (x == 177) || (x == 32));
                if (checa) {
                    System.out.println("Ok.");
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validaImg(String img) {
        int indexImg = img.toLowerCase().indexOf(".") + 1;
        String extension = img.substring(indexImg);
        if (img.equals("")) {
            return false;
        }
        return !((indexImg > 1) && (extension.equals("jpg") || extension.equals("png") || extension.equals("gif") || extension.equals("jpeg") || extension.equals("ico")));

    }

    public String stremplaza(String str) {
        if (str.contains("&") || str.contains("<") || str.contains(">") || str.contains("\\") || str.contains("'") || str.contains("\"")) {
            return str.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\\", "&bsol;")
                    .replace("'", "&apos;")
                    .replace("\"", "&quot;");
        } else {
            return str;
        }

    }

    public String stdesremplaza(String str) {
        if (str.contains("&amp;") || str.contains("&lt;") || str.contains("&gt;") || str.contains("&bsol;") || str.contains("&apos;") || str.contains("&quot;")) {
            return str.replace("&amp;", "&")
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                    .replace("&bsol;", "\\")
                    .replace("&apos;", "'")
                    .replace("&quot;", "\"");
        } else {
            return str;
        }

    }

    public String urlToHtmlTag(String text) {
        if (text.contains(".")) {
            Pattern pattern = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");
            Matcher matcher = pattern.matcher(text);
            Set<String> urlsFound = new HashSet<>();
            while (matcher.find()) {
                urlsFound.add(matcher.group());
            }
            for (String url : urlsFound) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    text = text.replace(url, "<a target='blank' href='" + url + "'>" + url + "</a>");
                } else {
                    text = text.replace(url, "<a target='blank' href='http://" + url + "'>" + url + "</a>");
                }
            }

        }

        return text;
    }
    
    public String randomtoken(){
        SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[32];
    random.nextBytes(bytes);
    return new String(Base64.getEncoder().encode(bytes));
    }
}
