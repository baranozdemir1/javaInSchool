package lab9_;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final List<String> urls = new ArrayList<String>();

    static String[] fileNames = {"01-January.txt", "02-February.txt", "03-March.txt", "04-April.txt", "05-May.txt", "06-June.txt", "07-July.txt", "08-August.txt", "09-September.txt", "10-October.txt", "11-November.txt", "12-December.txt"};
    
    public static void main(String[] args) throws Exception {
        int port = 1235;

        System.out.println("Server is running on port " + port + "..." + "\n");

        for (int i = 0; i < fileNames.length; i++) {
            urls.add("http://homes.ieu.edu.tr/culudagli/files/SE375/datasets/" + fileNames[i]);
        }
        ServerSocket welcomeSocket = new ServerSocket(port);

        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            DataOutputStream outTheClient = new DataOutputStream(connectionSocket.getOutputStream());
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            // send the urlMap to the client
            outTheClient.writeBytes(urls.toString() + '\n');
            outTheClient.writeBytes(inFromClient.readLine());
            outTheClient.writeBytes(inFromClient.readLine().toString());
        }
    }
}
