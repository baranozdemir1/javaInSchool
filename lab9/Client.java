package lab9;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Client implements Runnable {
    public static final HashMap<String, Integer> totalMap = new HashMap<String, Integer>();

    @Override
    public void run() {
        String url;

        try {

            Socket clientSocket = new Socket("localhost", 6789);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            url = inFromServer.readLine();

            System.out.println("FROM SERVER: " + url);
            BufferedReader httpFileScanner = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            httpFileScanner.readLine();

            while (httpFileScanner.ready()) {
                String line = httpFileScanner.readLine();
                String[] fileData = line.split(",");

                int priceTL = Integer.parseInt(fileData[1]);
                int inStorePurchases = Integer.parseInt(fileData[2]);
                int onlinePurchases = Integer.parseInt(fileData[3]);

                totalMap.put("Online", (totalMap.get("Online") + (onlinePurchases * priceTL)) );
                totalMap.put("In-Store", (totalMap.get("In-Store") + (inStorePurchases * priceTL)) );
            }

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            outToServer.writeInt(totalMap.get("Online"));
            outToServer.writeInt(totalMap.get("In-Store"));

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 12; i++) {
            Thread tr = new Thread(new Client());
            tr.start();
        }

        System.out.println("There are,");
        System.out.println("In-store: ₺" + totalMap.get("In-Store"));
        System.out.println("Online: ₺" + totalMap.get("Online"));
        System.out.println("Total: ₺" + (totalMap.get("Online") + totalMap.get("In-Store")) + " worth of sales for all products.");

    }
}
