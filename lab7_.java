import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

class Lab7Worker extends Thread {
    HashMap<String, Integer> totalMap;
    String filename;

    Lab7Worker(String file, HashMap<String, Integer> totalMap ) {
        this.totalMap = totalMap;
        this.filename = file;
    }

    @Override
    public void run() {
        System.out.println("Thread parsing " + filename + "...");
        try {
            BufferedReader httpFileScanner = new BufferedReader(new InputStreamReader(new URL("http://homes.ieu.edu.tr/culudagli/files/SE375/datasets/" + filename).openStream()));
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

public class lab7_ {

    private static final HashMap<String, Integer> totalMap = new HashMap<String, Integer>();

    static String[] fileNames = {"01-January.txt", "02-February.txt", "03-March.txt", "04-April.txt", "05-May.txt", "06-June.txt", "07-July.txt", "08-August.txt", "09-September.txt", "10-October.txt", "11-November.txt", "12-December.txt"};

    public static void main(String[] args) throws InterruptedException {

        totalMap.put("In-Store", 0);
        totalMap.put("Online", 0);

        for (String fileName : fileNames) {
            Thread thread = new Lab7Worker(fileName, totalMap);
            thread.start();
            thread.join();
        }

        System.out.println("Threads are complete.");

        System.out.println("\n");

        System.out.println("There are,");
        System.out.println("In-store: ₺" + totalMap.get("In-Store"));
        System.out.println("Online: ₺" + totalMap.get("Online"));
        System.out.println("Total: ₺" + (totalMap.get("In-Store") + totalMap.get("Online")) + " worth of sales for all products.");
    }
}