import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CS18000 Project 5 - Amazon
 *
 * <h1>Warehouse</h1>
 *
 * @author Jacquie Yu, Siddarth Pillai
 * @version 2018-12-06
 */

public class Warehouse {
    final static String FOLDER_PATH = "files/";
    final static File VEHICLE_FILE = new File(FOLDER_PATH + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(FOLDER_PATH + "PackageList.csv");
    final static File PROFIT_FILE = new File(FOLDER_PATH + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(FOLDER_PATH + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(FOLDER_PATH + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .15;


    /**
     * Main Method
     *
     * @param args list of command line arguments
     */
    public static void main(String[] args) {

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
                s.nextLine();

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
                                if (primeDay) {
                                    price *= 1 - PRIME_DAY_DISCOUNT;
                                }
                                s.nextLine();
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
                                System.out.println(pack.shippingLabel());

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
                                s.nextLine();
                                if (vehicleInput < 1 || vehicleInput > 3) {
                                    System.out.println("Error: Option Not Available.");
                                    continue;
                                }
                                System.out.println("Enter License Plate No.:");
                                String licensePlate = s.nextLine();
                                System.out.println("Enter Maximum Carry Weight:");
                                double maxCarryWeight;
                                try {
                                    maxCarryWeight = s.nextDouble();
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Input Not A Number.");
                                    continue;
                                }
                                s.nextLine();
                                if (vehicleInput == 1) {
                                    vehicles.add(new Truck(licensePlate, maxCarryWeight));
                                    vehicleRepeat = false;
                                } else if (vehicleInput == 2) {
                                    vehicles.add(new Drone(licensePlate, maxCarryWeight));
                                    vehicleRepeat = false;
                                } else {
                                    vehicles.add(new CargoPlane(licensePlate, maxCarryWeight));
                                    vehicleRepeat = false;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Option Not Available.");
                                vehicleRepeat = true;
                            }
                        }
                    } else if (input == 3) { //Activate/Deactivate Prime Day
                        if (primeDay) {
                            for (Package p : packages) {
                                p.setPrice(p.getPrice() / (1 - PRIME_DAY_DISCOUNT));
                            }
                            primeDay = false;
                        } else {
                            for (Package p : packages) {
                                p.setPrice(p.getPrice() * (1 - PRIME_DAY_DISCOUNT));
                            }
                            primeDay = true;
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


                        boolean sendRepeat = true;
                        while (sendRepeat) {
                            System.out.println(sendMenu);
                            try {
                                int sendInput = s.nextInt();
                                s.nextLine();
                                if (sendInput < 1 || sendInput > 4) {
                                    System.out.println("Error: Option Not Available.");
                                } else {
                                    boolean found = false;
                                    Vehicle ve = null;
                                    if (sendInput == 1) {
                                        for (Vehicle v : vehicles) {
                                            if (v instanceof Truck) {
                                                found = true;
                                                ve = new Truck(v.getLicensePlate(), v.getMaxWeight());
                                                break;
                                            }
                                        }
                                    } else if (sendInput == 2) {
                                        for (Vehicle v : vehicles) {
                                            if (v instanceof Drone) {
                                                found = true;
                                                ve = new Drone(v.getLicensePlate(), v.getMaxWeight());
                                                break;
                                            }
                                        }
                                    } else if (sendInput == 3) {
                                        for (Vehicle v : vehicles) {
                                            if (v instanceof CargoPlane) {
                                                found = true;
                                                ve = new CargoPlane(v.getLicensePlate(), v.getMaxWeight());
                                                break;
                                            }
                                        }
                                    } else {
                                        for (Vehicle v : vehicles) {
                                            if (!v.isFull()) {
                                                found = true;
                                                ve = v;
                                                break;
                                            }
                                        }
                                    }
                                    if (found) {
                                        System.out.println("ZIP Code Options:\n" +
                                                "1) Send to first ZIP Code\n" +
                                                "2) Send to mode of ZIP Codes");

                                        int zipDest = s.nextInt();
                                        s.nextLine();
                                        int sizeBefore;
                                        int sizeAfter;
                                        if (zipDest == 1) { //First zipcode
                                            int zip = packages.get(0).getDestination().getZipCode();
                                            ve.setZipDest(zip);
                                            sizeBefore = packages.size();
                                            ve.fill(packages);
                                            sizeAfter = packages.size();
                                            System.out.println(ve.report());
                                            profit += ve.getProfit();
                                            packagesShipped += sizeBefore - sizeAfter;
                                            sendRepeat = false;
                                        } else if (zipDest == 2) { //Mode zipcode
                                            int[] modes = new int[packages.size()];
                                            for (int i = 0; i < packages.size(); i++) {
                                                for (int j = 0; j < packages.size(); j++) {
                                                    if (packages.get(i).equals(packages.get(j))) {
                                                        modes[i] += 1;
                                                    }
                                                }
                                            }
                                            int max = modes[0];
                                            int maxIndex = 0;
                                            for (int i = 0; i < modes.length; i++) {
                                                if (max < modes[i]) {
                                                    max = modes[i];
                                                    maxIndex = i;
                                                }
                                            }
                                            ve.setZipDest(maxIndex);
                                            sizeBefore = packages.size();
                                            ve.fill(packages);
                                            sizeAfter = packages.size();
                                            System.out.println(ve.report());
                                            profit += ve.getProfit();
                                            packagesShipped += sizeBefore - sizeAfter;
                                            sendRepeat = false;
                                        }

                                    } else {
                                        System.out.println("Error: No vehicles of selected type are available.");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Option not available.");
                            }
                        }
                    } else if (input == 5) { //Print Stats

                        printStatisticsReport(profit, packagesShipped, packages.size());

                    } else {
                        System.out.println("Exiting...");
                        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);
                        DatabaseManager.savePackages(PACKAGE_FILE, packages);
                        DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, packagesShipped);
                        DatabaseManager.saveProfit(PROFIT_FILE, profit);
                        DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeDay);
                        repeatMenu = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Option not available.");
                repeatMenu = true;
            }
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday)
        // to files (overwriting them) using DatabaseManager


    }


    public static void printStatisticsReport(double profits, int packagesShipped, int numberOfPackages) {

        DecimalFormat df = new DecimalFormat();
        df.setDecimalSeparatorAlwaysShown(true);

        String stats = String.format("==========Statistics==========\n" +
                "Profits: $%.2f\n" +
                "Packages Shipped: %d\n" +
                "Packages in Warehouse: %d\n" +
                "==============================", profits, packagesShipped, numberOfPackages);

        System.out.println(stats);
    }


}

