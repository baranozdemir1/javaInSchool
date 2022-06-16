import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class lab7 {
    public static void main(String[] args) {
        try {

            BufferedReader file = new BufferedReader(new InputStreamReader(new URL("http://homes.ieu.edu.tr/culudagli/files/SE375/datasets/01-January.txt").openStream()));

            System.out.println(file.readLine());
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
