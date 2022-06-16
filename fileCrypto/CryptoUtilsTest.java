package fileCrypto;

import java.io.*;

public class CryptoUtilsTest {
    public static void main(String[] args) {
        String key = "Mary has one cat1";
        File inputFile = new File("/Users/baranozdemir/Desktop/JAVA/src/fileCrypto/test.txt");
        // File outputFile = new File("/Users/baranozdemir/Desktop/JAVA/src/fileCrypto/test.txt.enc");
        File encryptedFile = new File("/Users/baranozdemir/Desktop/JAVA/src/fileCrypto/document.encrypted");
        File decryptedFile = new File("/Users/baranozdemir/Desktop/JAVA/src/fileCrypto/document.decrypted");
        try {
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
            CryptoUtils.decrypt(key, inputFile, decryptedFile);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }
}
