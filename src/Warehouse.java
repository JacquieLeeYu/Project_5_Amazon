import java.io.File;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 */

public class Warehouse {
	final static String folderPath = "files/";
    final static File VEHICLE_FILE = new File(folderPath + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(folderPath + "PackageList.csv");
    final static File PROFIT_FILE = new File(folderPath + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(folderPath + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(folderPath + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .15;

    /**
     * Main Method
     * 
     * @param args list of command line arguments
     */
    public static void main(String[] args) {
    	//TODO

        Scanner s = new Scanner(System.in);

        String menu = "==========Options==========\n" +
                "1) Add Package\n" +
                "2) Add Vehicle\n" +
                "3) Activate Prime Day\n" +
                "4) Send Vehicle\n" +
                "5) Print Statistics\n" +
                "6) Exit\n" +
                "===========================";


        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager



        //2) Show menu and handle user inputs

        System.out.println(menu);
        try {
            int input = s.nextInt();

            if (input < 1 || input > 6) {
                System.out.println("Error: Option not available.");
            } else {
                if (input == 1) {
                    System.out.println("Enter Package ID:");
                    String packageID = s.nextLine();
                    System.out.println("Enter Product Name:");
                    String productName = s.nextLine();
                    System.out.println("Enter Weight:");
                    double weight = s.nextDouble();
                    System.out.println("Enter Price:");
                    double price = s.nextDouble();
                    System.out.println("Enter Buyer Name:");
                    String buyerName = s.nextLine();
                    System.out.println("Enter Address:");
                    String address = s.nextLine();
                    System.out.println("Enter City:");
                    String city = s.nextLine();
                    System.out.println("Enter State:");
                    String state = s.nextLine();
                    System.out.println("Enter ZIP Code:");
                    int zipCode = s.nextInt();
                    String totalInfo = String.format("====================\n" +
                            "TO: %s\n" +
                            "%s\n" +
                            "%s, %s, %d\n" +
                            "Weight: %.2f\n" +
                            "Price: $%.2f\n" +
                            "Product: %s\n" +
                            "====================", buyerName, address, city,
                            state, zipCode, weight, price, productName);
                } else if (input == 2) {

                } else if (input == 3) {

                } else if (input == 4) {

                } else if (input == 5) {

                } else {

                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Option not available.");
        }


    	//3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
    	
    
    }


}