package anotherDemo;

import java.io.*;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class FileEncrypterDecrypter {

    private SecretKey secretKey;
    private Cipher cipher;

    FileEncrypterDecrypter(SecretKey secretKey, String transformation) throws Exception {
		this.secretKey = secretKey;
		this.cipher = Cipher.getInstance(transformation);
	}

    void encrypt(String content, String fileName) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();
    
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
          CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);
            cipherOut.write(content.getBytes());
        } catch (Exception e) {
            System.err.println("Error encrypting file: " + e.getMessage());
        }
    }

    String decrypt(String fileName) throws Exception {
        String content;
    
        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));
    
            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    InputStreamReader inputReader = new InputStreamReader(cipherIn);
                    BufferedReader reader = new BufferedReader(inputReader)
                ) {
    
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
            }
    
        }
        return content;
    }
}
