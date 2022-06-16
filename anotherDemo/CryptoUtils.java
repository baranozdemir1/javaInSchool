package anotherDemo;

import java.io.*;

import javax.crypto.*;

public class CryptoUtils {

	public void whenEncryptingIntoFile_andDecryptingFileAgain_thenOriginalStringIsReturned() throws Exception {
		String originalContent = "foobar";
		SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
	
		FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
		fileEncrypterDecrypter.encrypt(originalContent, "baz.enc");
	
		String decryptedContent = fileEncrypterDecrypter.decrypt("baz.enc");
	
		new File("baz.enc").delete(); // cleanup
	}

	public static byte[] readFromFile(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);

		byte[] key = new byte[(int) file.length()];
		fis.read(key);
		fis.close();

		return key;
	}
}
