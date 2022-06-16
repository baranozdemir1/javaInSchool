import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class Lab3Worker extends Thread {
    HashMap<String, Integer> inStoreMap;
    HashMap<String, Integer> onlineMap;
    String filename;

    static String path = "C:\\Users\\C605\\Downloads\\src\\Datasets\\";

    Lab3Worker(String file, HashMap<String, Integer> storeMap, HashMap<String, Integer> onlineMap ) {
        this.inStoreMap = storeMap;
        this.onlineMap = onlineMap;
        this.filename = file;
    }

    @Override
    public void run() {
        ArrayList<Integer> priceTL = new ArrayList<Integer>();
        ArrayList<String> productName = new ArrayList<String>();
        ArrayList<Integer> inStorePurchases = new ArrayList<Integer>();
        ArrayList<Integer> onlinePurchases = new ArrayList<Integer>();

        System.out.println("Thread parsing " + filename + "...");
        try {
            BufferedReader httpFileScanner = new BufferedReader(new InputStreamReader(new URL("http://homes.ieu.edu.tr/culudagli/files/SE375/datasets/" + filename).openStream()));
            httpFileScanner.readLine();
            while (httpFileScanner.ready()) {
                String line = httpFileScanner.readLine();
                String[] data = line.split(",");
                productName.add(data[0]);
                priceTL.add(Integer.parseInt(data[1]));
                inStorePurchases.add(Integer.parseInt(data[2]));
                onlinePurchases.add(Integer.parseInt(data[3]));
            }
            httpFileScanner.close();
            
            // Proceed by adding the value to inStoreMap and onlineMap in each for loop
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

public class lab3 {

    private static final HashMap<String, Integer> inStoreMap = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> onlineMap = new HashMap<String, Integer>();

    static String[] fileNames = {"01-January.txt", "02-February.txt", "03-March.txt", "04-April.txt", "05-May.txt", "06-June.txt", "07-July.txt", "08-August.txt", "09-September.txt", "10-October.txt", "11-November.txt", "12-December.txt"};

    static String[] products = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M"};

    public static void main(String[] args) throws InterruptedException {

        for (String product : products) {
            inStoreMap.put(product, 0);
            onlineMap.put(product, 0);
        }

        for (String fileName : fileNames) {
            Thread thread = new Lab3Worker(fileName, inStoreMap, onlineMap);
            thread.start();
            thread.join();
        }

        System.out.println("Threads are complete. Aggregating results.");

        System.out.println("\n");
        
        Scanner search = new Scanner(System.in);
        System.out.println("Which product do you want to search?");
        String selectedProduct = search.nextLine();
        search.close();

        System.out.println("\n");

        System.out.println("For the product " + selectedProduct + ",");
        System.out.println("In-store sales: ₺" + inStoreMap.get(selectedProduct));
        System.out.println("Online sales: ₺" + onlineMap.get(selectedProduct));
        System.out.println("Total sales: ₺" + (inStoreMap.get(selectedProduct) + onlineMap.get(selectedProduct)));
    }
}