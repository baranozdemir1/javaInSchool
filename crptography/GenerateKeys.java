package crptography;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

class GenerateKeys {

    public static void writeToFile(String path, byte[] key) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdir();

        FileOutputStream fos = new FileOutputStream(file);

        fos.write(key);
        fos.flush();
        fos.close();
    }

    public static void main(String[] args) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            System.out.println("Public Key: " + publicKey.getFormat());
            System.out.println("Private Key: " + privateKey.getFormat());

            writeToFile("KeyStore/publicKey", publicKey.getEncoded());
            writeToFile("KeyStore/privateKey", privateKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}