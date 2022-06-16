import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Lab2Worker extends Thread {

    String fileName = "";

    static String path = "C:\\Users\\C605\\Downloads\\src\\Datasets\\";

    private final ArrayList<String> productName = new ArrayList<String>();
    private final ArrayList<Integer> priceTL = new ArrayList<Integer>();
    private final ArrayList<Integer> inStorePurchases = new ArrayList<Integer>();
    private final ArrayList<Integer> onlinePurchases = new ArrayList<Integer>();
    private int totalInStore;
    private int totalOnline;

    static int generalTotalInStore = 0;
    static int generalTotalOnline = 0;

    Lab2Worker(String fileName) {
        this.fileName = fileName;
    }

    Lab2Worker() {}

    int getTotalInStore() {
        return generalTotalInStore;
    }

    int getTotalOnline() {
        return generalTotalOnline;
    }

    @Override
    public void run() {
        System.out.println("Thread parsing " + this.fileName + "...");
        try (Scanner scanner = new Scanner(new File(path + this.fileName))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                productName.add(data[0]);
                priceTL.add(Integer.parseInt(data[1]));
                inStorePurchases.add(Integer.parseInt(data[2]));
                onlinePurchases.add(Integer.parseInt(data[3]));
            }
            scanner.close();
            
            for (int i = 0; i < productName.size(); i++) {
                totalInStore += (inStorePurchases.get(i) * priceTL.get(i));
                totalOnline += (onlinePurchases.get(i) * priceTL.get(i));
            }

            System.out.println("Thread finished: " + this.fileName + " - " + "In-store: ₺" + totalInStore + ", " + "Online: ₺" + totalOnline);
            generalTotalInStore += totalInStore;
            generalTotalOnline += totalOnline;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class lab2 extends Thread {

    public static void main(String[] args) throws InterruptedException {
        Thread january = new Lab2Worker("01-January.csv");
        Thread fabruary = new Lab2Worker("02-February.csv");
        Thread march = new Lab2Worker("03-March.csv");
        january.start();
        fabruary.start();
        march.start();
        
        january.join();
        fabruary.join();
        march.join();
        System.out.println("Threads are complete. Aggregating results.");
        System.out.println("\n");
        System.out.println("There are,");
        System.out.println("In-store: ₺" + new Lab2Worker().getTotalInStore() + " TL");
        System.out.println("Online: ₺" + new Lab2Worker().getTotalOnline() + " TL");
        System.out.println("Total: ₺" + (new Lab2Worker().getTotalInStore() + new Lab2Worker().getTotalOnline()) + " worth of sales for all products.");
    }
}