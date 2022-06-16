package lab8;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getByName("localhost");
        DatagramSocket socket = new DatagramSocket();
        
        try {
            byte[] data = new byte[512];
            DatagramPacket request = new DatagramPacket(data, data.length, address, 8888);
            socket.send(request);

            byte[] newData = new byte[512];
            DatagramPacket response = new DatagramPacket(newData, newData.length);
            socket.receive(response);

            String receivedUrl = new String(newData, 0, response.getLength());
            System.out.println(receivedUrl);
            System.out.println();

            // Calc the results

            BufferedReader httpFileScanner = new BufferedReader(new InputStreamReader(new URL(receivedUrl).openStream()));
            httpFileScanner.readLine();

            int inStore = 0;
            int online = 0;

            while (httpFileScanner.ready()) {
                String line = httpFileScanner.readLine();
                String[] fileData = line.split(",");

                int priceTL = Integer.parseInt(fileData[1]);
                int inStorePurchases = Integer.parseInt(fileData[2]);
                int onlinePurchases = Integer.parseInt(fileData[3]);

                inStore += (inStorePurchases * priceTL);
                online += (onlinePurchases * priceTL);
            }

            System.out.println("There are,");
            System.out.println("In-store: ₺" + inStore);
            System.out.println("Online: ₺" + online);
            System.out.println("Total: ₺" + (online + inStore) + " worth of sales for all products.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
