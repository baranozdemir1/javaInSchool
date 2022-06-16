package crptography;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AsymmetricCryptography {
    public static String printBytes(byte[] data){
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : data) {
            stringBuilder.append(String.format("%02X ", b));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String message = "This is SE375 SYSTEM PROGRAMMING.";
        byte[] plain_text;
        byte[] encrypted_tex;
        byte[] decrypted_text;
        Cipher cipher;

        try {
            plain_text = message.getBytes("UTF-8");
            System.out.println("Original Data: " + message);
            System.out.println(printBytes(plain_text));

            // STEP 1. Generate the Keys. Read them from a file. 
            byte[] keyBytes = Files.readAllBytes(new File("KeyStore/privateKey").toPath());
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            keyBytes = Files.readAllBytes(new File("KeyStore/publicKey").toPath());
            X509EncodedKeySpec spec2 = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = keyFactory.generatePublic(spec2);

            // STEP 2. Get a Cipher for the desired transformation
            cipher = Cipher.getInstance("RSA");

            // STEP 3. Choose a method
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // STEP 4. Perform the operation
            encrypted_tex = cipher.doFinal(plain_text);
            decrypted_text = cipher.doFinal(encrypted_tex);
            System.out.println("Decrypted Data: " + printBytes(decrypted_text));
            String msgBase64 = Base64.getEncoder().encodeToString(encrypted_tex);
            System.out.println("Base64 Encoded String (Basic): " + msgBase64);

            // STEP 5. Undo the operation
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decrypted_text = cipher.doFinal(encrypted_tex);
            System.out.println("Decrypted Data: " + printBytes(decrypted_text));

            System.out.println("Original Message: " + message + "\nEncrypted Message: " + msgBase64
			+ "\nDecrypted Message: " + new String(decrypted_text));

        } catch (UnsupportedEncodingException ex) {
			System.err.println("Couldn't create key: " + ex.getMessage()); 
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.err.println(e.getMessage());
		}  catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
        }

    }
}
