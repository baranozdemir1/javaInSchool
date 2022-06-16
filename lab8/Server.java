package lab8;

import java.net.*;
import java.util.*;

public class Server {

    public static DatagramSocket socket;
    private static HashMap<Integer, String> urlMap = new HashMap<Integer, String>();

    static String[] fileNames = {"01-January.txt", "02-February.txt", "03-March.txt", "04-April.txt", "05-May.txt", "06-June.txt", "07-July.txt", "08-August.txt", "09-September.txt", "10-October.txt", "11-November.txt", "12-December.txt"};

    public static void main(String[] args) throws Exception {
        
        int port = 8888;

        System.out.println("Server is running on port " + port + "..." + "\n");

        for (int i = 0; i < fileNames.length; i++) {
            urlMap.put(i, ("http://homes.ieu.edu.tr/culudagli/files/SE375/datasets/" + fileNames[i]));
        }

        Random random = new Random();
        socket = new DatagramSocket(port);
        byte[] data = new byte[512];

        while(true) {
            DatagramPacket request = new DatagramPacket(data, data.length);
            socket.receive(request);

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            int mapIndex = random.nextInt(12);
            String randomUrl = urlMap.get(mapIndex);
            byte[] randomUrlBytes = randomUrl.getBytes();

            DatagramPacket response = new DatagramPacket(randomUrlBytes, randomUrlBytes.length, clientAddress, clientPort);
            System.out.println(new Date() + ": " + "Sending \"" + randomUrl + "\" to " + clientAddress + ":" + clientPort);
            socket.send(response);
        }
    }
}
