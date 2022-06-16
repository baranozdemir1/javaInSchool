package lab9_;

import java.io.*;
import java.net.*;
import java.util.*;

public class Lab9Worker extends Thread {
    HashMap<String, Integer> totalMap;
    String url;

    Lab9Worker(String url, HashMap<String, Integer> totalMap ) {
        this.totalMap = totalMap;
        this.url = url;
    }

    @Override
    public void run() {
        System.out.println("Thread parsing " + url + "...");
        try {
            BufferedReader httpFileScanner = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            httpFileScanner.readLine();
            while (httpFileScanner.ready()) {
                String line = httpFileScanner.readLine();
                String[] data = line.split(",");

                int priceTL = Integer.parseInt(data[1]);
                int inStorePurchases = Integer.parseInt(data[2]);
                int onlinePurchases = Integer.parseInt(data[3]);

                int inStoreCalc = totalMap.get("In-Store") + ( inStorePurchases * priceTL );
                totalMap.put("In-Store", inStoreCalc);

                int onlineCalc = totalMap.get("Online") + ( onlinePurchases * priceTL );
                totalMap.put("Online", onlineCalc);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
