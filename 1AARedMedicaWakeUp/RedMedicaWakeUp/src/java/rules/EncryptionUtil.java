/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;
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
import java.util.Base64;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author JavaDigest
 * 
 */
public class EncryptionUtil {

  /**
   * String to hold name of the encryption algorithm.
   */
  public static final String ALGORITHM = "RSA";

  /**
   * String to hold the name of the private key file.
   */
  public static final String PRIVATE_KEY_FILE = "E:/1AARedMedicaWakeUp/keys/private.key";
public static final String f =Seguridad.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      public static final String ruta=f.replace("Seguridad.class","private.key");
      public static final String rutaprivate=ruta.replaceFirst("/","");
    
  /**
   * String to hold name of the public key file.
   */
  public static final String PUBLIC_KEY_FILE = "E:/1AARedMedicaWakeUp/keys/public.key";

  /**
   * Generate key which contains a pair of private and public key using 1024
   * bytes. Store the set of keys in Prvate.key and Public.key files.
   * 
   */
   public static String chiperTexto="";
   public static String chiperTexto2="";
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
   * The method checks if the pair of public and private key has been generated.
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
   * @param text
   *          : original plain text
   * @param key
   *          :The public key
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
   * @param text
   *          :encrypted text
   * @param key
   *          :The private key
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
     * @param base64string
     * @return 
   */
  public String decripta(String base64string) {
String plainText="";
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
      inputStream = new ObjectInputStream(new FileInputStream(rutaprivate));
      final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
      final byte[] decoded =Base64.getDecoder().decode(base64string.getBytes());
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
}