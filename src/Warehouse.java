import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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



        DecimalFormat df = new DecimalFormat();
        //TODO

        Scanner s = new Scanner(System.in);

        String menuInactive = "==========Options==========\n" +
                "1) Add Package\n" +
                "2) Add Vehicle\n" +
                "3) Activate Prime Day\n" +
                "4) Send Vehicle\n" +
                "5) Print Statistics\n" +
                "6) Exit\n" +
                "===========================";

        String menuActive = "==========Options==========\n" +
                "1) Add Package\n" +
                "2) Add Vehicle\n" +
                "3) Deactivate Prime Day\n" +
                "4) Send Vehicle\n" +
                "5) Print Statistics\n" +
                "6) Exit\n" +
                "===========================";


        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager

        //Do what?

        ArrayList<Package> packages = DatabaseManager.loadPackages(PACKAGE_FILE);
        ArrayList<Vehicle> vehicles = DatabaseManager.loadVehicles(VEHICLE_FILE);
        double profit = DatabaseManager.loadProfit(PROFIT_FILE);
        boolean primeDay = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);
        int packagesShipped = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);


        //2) Show menu and handle user inputs

        boolean repeatMenu = true;
        while (repeatMenu) {
            if (!primeDay) { //check if it's not prime day
                System.out.println(menuInactive);
            } else {
                System.out.println(menuActive);
            }
            try {
                int input = s.nextInt();

                if (input < 1 || input > 6) {
                    System.out.println("Error: Option not available.");
                } else {
                    if (input == 1) { //Add package
                        boolean packageRepeat = true;
                        while (packageRepeat) {
                            System.out.println("Enter Package ID:");
                            String packageID = s.nextLine();
                            System.out.println("Enter Product Name:");
                            String productName = s.nextLine();
                            System.out.println("Enter Weight:");
                            try {
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
                                ShippingAddress completeAddress = new
                                        ShippingAddress(buyerName, address, city, state, zipCode);
                                Package pack = new Package(packageID, productName, weight, price, completeAddress);
                                packages.add(pack);
                                pack.shippingLabel();

                                packageRepeat = false;
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Option not available.");
                                packageRepeat = false;
                            }
                        }
                    } else if (input == 2) { //Add vehicle
                        boolean vehicleRepeat = true;
                        while (vehicleRepeat) {
                            System.out.println("Vehicle Options:\n" +
                                    "1) Truck\n" +
                                    "2) Drone\n" +
                                    "3) Cargo Plane");
                            try {
                                int vehicleInput = s.nextInt();
                                System.out.println("Enter License Plate No.:");
                                String licensePlate = s.nextLine();
                                System.out.println("Enter Maximum Carry Weight:");
                                double maxCarryWeight = s.nextDouble();
                                vehicles.add(new Vehicle(licensePlate, maxCarryWeight));
                                vehicleRepeat = false;
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Option Not Available.");
                                vehicleRepeat = true;
                            }
                        }
                    } else if (input == 3) { //Activate/Deactivate Prime Day
                        if (primeDay) {

                        }

                    } else if (input == 4) { //Send Vehicle
                        if (vehicles.size() == 0) {
                            System.out.println("Error: No vehicles available.");
                            continue;
                        }
                        if (packages.size() == 0) {
                            System.out.println("Error: No packages available.");
                            continue;
                        }

                        String sendMenu = "Options:\n" +
                                "1) Send Truck\n" +
                                "2) Send Drone\n" +
                                "3) Send Cargo Plane\n" +
                                "4) Send First Available";

                        System.out.println(sendMenu);

                        try {
                            int sendInput = s.nextInt();
                            if (sendInput < 1 || sendInput > 4) {
                                System.out.println("Error: Option Not Available.");
                            } else {
                                if (sendInput == 1) {
                                    boolean found = false;
                                    for (Vehicle v : vehicles) {
                                        if (v instanceof Truck) {
                                            found = true;
                                            System.out.println("ZIP Code Options:\n" +
                                                    "1) Send to first ZIP Code\n" +
                                                    "2) Send to mode of ZIP Codes");

                                            int zipDest = s.nextInt();
                                            if (zipDest == 1) { //First zipcode
                                                int zip = packages.get(0).getDestination().getZipCode();
                                                v.setZipDest(zip);
                                                v.fill(packages);
                                                v.report();
                                                //NEeds to gEtpRofIt
                                                profit += v.getProfit();
                                                packagesShipped += packages.size();
                                            } else if (zipDest == 2) { //Mode zipcode
                                                int[] modes = new int[packages.size()];
                                                for (int i = 0 ; i < packages.size() ; i++) {
                                                    for (int j = 0 ; j < packages.size() ; j++) {
                                                        if (packages.get(i).equals(packages.get(j))) {
                                                            modes[i] += 1;
                                                        }
                                                    }
                                                }
                                                int max = modes[0];
                                                int maxIndex = 0;
                                                for (int e : modes) {
                                                    if (max < modes[e]) {
                                                        max = modes[e];
                                                        maxIndex = e;
                                                    }
                                                }
                                                v.setZipDest(maxIndex);
                                                v.fill(packages);
                                                v.report();
                                                //GEt ProFiT
                                            }
                                        }
                                    }
                                    if (!found) {
                                        System.out.println("Error: No vehicles of selected type are available.");
                                        continue;
                                    }


                                } else if (sendInput == 2) {

                                } else if (sendInput == 3) {

                                } else {

                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Option not available.");
                        }
                    } else if (input == 5) { //Print Stats

                        df.setDecimalSeparatorAlwaysShown(true);

                        String stats = String.format("==========Statistics==========\n" +
                                "Profits: $%.2f\n" +
                                "Packages Shipped: %d\n" +
                                "Packages in Warehouse: %d\n" +
                                "==============================", profit, packagesShipped, packages.size());

                        System.out.println(stats);

                    } else {
                        repeatMenu = false;
                    }
                }
            } catch(NumberFormatException e){
                    System.out.println("Error: Option not available.");
                    repeatMenu = true;
            }
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday)
        // to files (overwriting them) using DatabaseManager
        DatabaseManager.saveVehicles(VEHICLE_FILE,vehicles);
        DatabaseManager.savePackages(PACKAGE_FILE, packages);
        DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, packagesShipped);
        DatabaseManager.saveProfit(PROFIT_FILE,profit);
        DatabaseManager.savePrimeDay(PRIME_DAY_FILE,primeDay);




    }





    	
    
}