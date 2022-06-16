import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class MonthScanner {

    private final ArrayList<String> productName = new ArrayList<String>();
    private final ArrayList<Integer> priceTL = new ArrayList<Integer>();
    private final ArrayList<Integer> inStorePurchases = new ArrayList<Integer>();
    private final ArrayList<Integer> onlinePurchases = new ArrayList<Integer>();

    public ArrayList<String> getProductName() {
        return this.productName;
    }

    public ArrayList<Integer> getPrice() {
        return this.priceTL;
    }

    public ArrayList<Integer> getInStorePurchases() {
        return this.inStorePurchases;
    }

    public ArrayList<Integer> getOnlinePurchases() {
        return this.onlinePurchases;
    }

    public void setProductName(String fileName) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                productName.add(data[0]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void setPrice(String fileName) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                priceTL.add(Integer.parseInt(data[1]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void setInStorePurchases(String fileName) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                inStorePurchases.add(Integer.parseInt(data[2]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void setOnlinePurchases(String fileName) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                onlinePurchases.add(Integer.parseInt(data[3]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}


public class lab1 {

    public static void main(String[] args) throws FileNotFoundException {
        MonthScanner monthScanner = new MonthScanner();

        String[] fileNames = {"01-January.csv", "02-February.csv", "03-March.csv", "04-April.csv", "05-May.csv", "06-June.csv", "07-July.csv", "08-August.csv", "09-September.csv", "10-October.csv", "11-November.csv", "12-December.csv"};

        String path = "C:\\Users\\C605\\Downloads\\src\\Datasets\\";

        // The total sale amount that the store has completed for the full year.
        int totalInStore = 0;
        int totalOnline = 0;
        for (String fileName : fileNames) {
            monthScanner.setProductName(path + fileName);
            monthScanner.setPrice(path + fileName);
            monthScanner.setInStorePurchases(path + fileName);
            monthScanner.setOnlinePurchases(path + fileName);
        }
        for (int j = 0; j < 144; j++) {
            totalInStore += (monthScanner.getInStorePurchases().get(j) * monthScanner.getPrice().get(j));
            totalOnline += (monthScanner.getOnlinePurchases().get(j) * monthScanner.getPrice().get(j));
        }
        System.out.println("(1) Total sale amount for the full year: " + (totalInStore + totalOnline) + " TL");

        // The total in-store sales (TL) for a month
        int totalInStoreMonth = 0;
        for (int i = 0; i < 12; i++) {
            totalInStoreMonth += (monthScanner.getInStorePurchases().get(i) * monthScanner.getPrice().get(i));
        }
        System.out.println("(2) Total in-store sales (TL) for a month: " + totalInStoreMonth + " TL");
        
        //The total online sales (TL) for a month
        int totalOnlineMonth = 0;
        for (int i = 0; i < 12; i++) {
            totalOnlineMonth += (monthScanner.getOnlinePurchases().get(i) * monthScanner.getPrice().get(i));
        }
        System.out.println("(3) Total online sales (TL) for a month: " + totalOnlineMonth + " TL");
        System.out.println("(4) Total sale amount (in-store) for the full year: " + totalInStore + " TL");
        System.out.println("(5) Total sale amount (online) for the full year: " + totalOnline + " TL");

        // The total sales for the particular products A, B and C for a year
        int totalProductA = 0;
        int totalProductB = 0;
        int totalProductC = 0;
        for (int i = 0; i < 144; i++) {
            switch (monthScanner.getProductName().get(i)) {
                case "A":
                    totalProductA += (monthScanner.getInStorePurchases().get(i) * monthScanner.getPrice().get(i)) + (monthScanner.getOnlinePurchases().get(i) * monthScanner.getPrice().get(i));
                    break;
                case "B":
                    totalProductB += (monthScanner.getInStorePurchases().get(i) * monthScanner.getPrice().get(i)) + (monthScanner.getOnlinePurchases().get(i) * monthScanner.getPrice().get(i));
                    break;
                case "C":
                    totalProductC += (monthScanner.getInStorePurchases().get(i) * monthScanner.getPrice().get(i)) + (monthScanner.getOnlinePurchases().get(i) * monthScanner.getPrice().get(i));
                    break;
            }
        }

        System.out.println("(6) Total sales (TL) for A: " + totalProductA + " TL");
        System.out.println("(6) Total sales (TL) for B: " + totalProductB + " TL");
        System.out.println("(6) Total sales (TL) for C: " + totalProductC + " TL");
    }

}
