package lab9;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    private static final HashMap<Integer, String> urlMap = new HashMap<Integer, String>();

    static String[] fileNames = {"01-January.txt", "02-February.txt", "03-March.txt", "04-April.txt", "05-May.txt", "06-June.txt", "07-July.txt", "08-August.txt", "09-September.txt", "10-October.txt", "11-November.txt", "12-December.txt"};

    public static void main(String[] args) throws Exception {
        int totals;
        int total2;
        int port = 6789;

        System.out.println("Server is running on port " + port + "..." + "\n");

        for (int i = 0; i < fileNames.length; i++) {
            urlMap.put(i, ("http://homes.ieu.edu.tr/culudagli/files/SE375/datasets/" + fileNames[i]));
        }

        ServerSocket welcomeSocket = new ServerSocket(port);
        
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            totals = inFromClient.read();
            total2 = inFromClient.read();

            outToClient.writeInt(totals);
            outToClient.writeInt(total2);

        }
    }
}
