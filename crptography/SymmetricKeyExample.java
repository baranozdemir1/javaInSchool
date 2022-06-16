package crptography;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SymmetricKeyExample {
    public static String printBytes(byte[] data){
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : data) {
            stringBuilder.append(String.format("%02X ", b));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String message = "Merhaba.";
        byte[] plain_text;
        byte[] encrypted_tex;
        byte[] decrypted_text;
        Cipher cipher;

        try {
            plain_text = message.getBytes("UTF-8");
            System.out.println("Original Data: " + message);
            System.out.println(printBytes(plain_text));

            // STEP 1. Generate the Keys.
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(256, secureRandom);
            SecretKey secretKey = keyGen.generateKey();

            // length of the key must be 128 , 192 or 256 bits (16, 24, 32 byte) 
            // byte[] key = "!'^+Pass@4meword".getBytes("UTF-8");
            // SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            // STEP 2. Get a Cipher for the desired transformation
            cipher = Cipher.getInstance("AES");

            // STEP 3. Choose a method
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // STEP 4. Perform the operation
            encrypted_tex = cipher.doFinal(plain_text);
            System.out.println("Encrypted Data: " + printBytes(encrypted_tex));

            // STEP 5. Undo the operation
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decrypted_text = cipher.doFinal(encrypted_tex);
            System.out.println("Decrypted Data: " + printBytes(decrypted_text));

            if (java.util.Arrays.equals(decrypted_text, plain_text)){
                System.out.println("Obtained the original text: " + new String(decrypted_text));
            } else {
                System.out.println("Failed to obtain the original text.");
            }

        } catch (UnsupportedEncodingException ex) {
			System.err.println("Couldn't create key: " + ex.getMessage()); 
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.err.println(e.getMessage());
		}  catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println(e.getMessage());
		}

    }
}
