import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

class Lab3_2Worker extends Thread {
    HashMap<String, Integer> totalMap;
    String filename;

    static String path = "C:\\Users\\C605\\Downloads\\src\\Datasets\\";

    Lab3_2Worker(String file, HashMap<String, Integer> totalMap ) {
        this.totalMap = totalMap;
        this.filename = file;
    }

    @Override
    public void run() {
        System.out.println("Thread parsing " + filename + "...");
        try (Scanner scanner = new Scanner(new File(path + filename))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                int priceTL = Integer.parseInt(data[1]);
                int inStorePurchases = Integer.parseInt(data[2]);
                int onlinePurchases = Integer.parseInt(data[3]);

                int inStoreCalc = totalMap.get("In-Store") + ( inStorePurchases * priceTL );
                totalMap.put("In-Store", inStoreCalc);

                int onlineCalc = totalMap.get("Online") + ( onlinePurchases * priceTL );
                totalMap.put("Online", onlineCalc);
            }

            // Proceed by adding the value to inStoreMap and onlineMap in each for loop

            /*
            for (int i = 0; i < productName.size(); i++) {
                String name = productName.get(i);
                int price = priceTL.get(i);

                int inStoreAmount = inStorePurchases.get(i);
                int inStoreSum = inStoreAmount * price ;
                int inStoreOldValue = inStoreMap.get(name);
                
                int onlineAmount = onlinePurchases.get(i);
                int onlineSum = onlineAmount * price ;
                int onlineOldValue = onlineMap.get(name);

                inStoreSum = inStoreSum + inStoreOldValue;
                inStoreMap.put(name, inStoreSum);

                onlineSum = onlineSum + onlineOldValue;
                onlineMap.put(name, onlineSum);
            }
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class lab3_2 {

    private static final HashMap<String, Integer> totalMap = new HashMap<String, Integer>();

    static String[] fileNames = {"01-January.csv", "02-February.csv", "03-March.csv", "04-April.csv", "05-May.csv", "06-June.csv", "07-July.csv", "08-August.csv", "09-September.csv", "10-October.csv", "11-November.csv", "12-December.csv"};

    public static void main(String[] args) throws InterruptedException {

        totalMap.put("In-Store", 0);
        totalMap.put("Online", 0);

        for (String fileName : fileNames) {
            Thread thread = new Lab3_2Worker(fileName, totalMap);
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