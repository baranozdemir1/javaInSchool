import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestExample {

    public static void main(String[] args) throws NoSuchAlgorithmException {

//        String password = "123456";
    	String password = "The Quick Brown Fox Jumps Over The Lazy Dog";

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashInBytes = md.digest(password.getBytes());

		// bytes to hex
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        System.out.println("Size in bytes:" + hashInBytes.length + "\n" + sb.toString());
        
        String name = "John Doe";
        byte[] data1 = password.getBytes();
        byte[] data2 = name.getBytes();

 //       MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(data1);
        messageDigest.update(data2);

        hashInBytes = messageDigest.digest();
        
        sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        System.out.println("Uses update()." + "Size in bytes:" + hashInBytes.length + "\n" + sb.toString());
    }

}
