package lab9_;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static final List<String> urls = new ArrayList<String>();

    private static final HashMap<String, Integer> totalMap = new HashMap<String, Integer>();

    public static void main(String[] args) throws Exception {
        totalMap.put("In-Store", 0);
        totalMap.put("Online", 0);

        String urlsFromServer;

        Socket clientSocket = new Socket("localhost", 1235);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        outToServer.writeBytes("Give me something..." + '\n');

        urlsFromServer = inFromServer.readLine();

        String[] urlsFromServerArray = urlsFromServer.substring(1, urlsFromServer.length() - 1).split(", ");
        for (int i = 0; i < urlsFromServerArray.length; i++) {
            urls.add(urlsFromServerArray[i]);
        }

        for (int i = 0; i < urls.size(); i++) {
            System.out.println(urls.get(i));
        }

        // for (int i = 0; i < urls.size(); i++) {
        //     // Thread thread = new Lab9Worker(urls.get(i), totalMap);
        //     // thread.start();
        //     // thread.join();
        //     outToServer.writeBytes("Thread " + i + " is done." + '\n');
        // }
        outToServer.writeBytes("Thread is done." + '\n');
        String fromServer1 = inFromServer.readLine();
        System.out.println(fromServer1);
        clientSocket.close();
    }
}
