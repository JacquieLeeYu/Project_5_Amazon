import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * CS18000 Project 5 - Amazon
 *
 * <h1>Database Manager</h1>
 * <p>
 * Used to locally save and retrieve data.
 *
 * @author Jacquie Yu, Siddarth Pillai
 * @version 2018-12-06
 */
public class DatabaseManager {

    /**
     * Creates an ArrayList of Vehicles from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of vehicles
     */
    public static ArrayList<Vehicle> loadVehicles(File file) {
        try {
            FileReader fr = new FileReader(file);
            Scanner scan = new Scanner(fr);
            String vehicleType = "";
            String vehiclePlate = "";
            double weight = 0.0;
            ArrayList<Vehicle> vehicles = new ArrayList<>(0);
            String details;
            try {
                details = scan.nextLine();
            } catch (NoSuchElementException e) {
                details = null;
            }
            while (details != null) {
                String[] info = details.split(",");
                vehicleType = info[0];
                vehiclePlate = info[1];
                weight = (Double.parseDouble(info[2]));

                if (vehicleType.equalsIgnoreCase("Drone")) {
                    vehicles.add(new Drone(vehiclePlate, weight));
                }
                if (vehicleType.equalsIgnoreCase("Truck")) {
                    vehicles.add(new Truck(vehiclePlate, weight));
                }
                if (vehicleType.equalsIgnoreCase("Cargo Plane")) {
                    vehicles.add(new CargoPlane(vehiclePlate, weight));
                }
                try {
                    details = scan.nextLine();
                } catch (NoSuchElementException e) {
                    details = null;
                }
            }
            fr.close();
            scan.close();
            return vehicles;
        } catch (IOException e) {
            return new ArrayList<>(0);
        }


    }


    /**
     * Creates an ArrayList of Packages from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * <p>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of packages
     */
    public static ArrayList<Package> loadPackages(File file) {
        try {
            FileReader fr = new FileReader(file);
            Scanner scan = new Scanner(fr);
            String id = "";
            String productName = "";
            double weight = 0.0;
            double price = 0.0;
            String addressName = "";
            String address = "";
            String city = "";
            String state = "";
            int zip = 0;
            ShippingAddress ship = new ShippingAddress();
            ArrayList<Package> packages = new ArrayList<>(0);
            String details;
            try {
                details = scan.nextLine();
            } catch (NoSuchElementException e) {
                details = null;
            }
            while (details != null) {
                String[] info = details.split(",");
                id = info[0];
                productName = info[1];
                weight = Double.parseDouble(info[2]);
                price = Double.parseDouble(info[3]);
                addressName = info[4];
                address = info[5];
                city = info[6];
                state = info[7];
                zip = Integer.parseInt(info[8]);
                ship = new ShippingAddress(addressName, address, city, state, zip);
                packages.add(new Package(id, productName, weight, price, ship));

                try {
                    details = scan.nextLine();
                } catch (NoSuchElementException e) {
                    details = null;
                }

            }
            fr.close();
            scan.close();
            return packages;
        } catch (IOException e) {
            return new ArrayList<>(0);
        }
    }


    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     *
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
        try {
            FileReader fr = new FileReader(file);
            Scanner scan = new Scanner(fr);
            double profit = scan.nextDouble();
            fr.close();
            scan.close();
            return profit;
        } catch (IOException | NoSuchElementException e) {
            return 0;
        }
    }


    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     *
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
        try {
            FileReader fr = new FileReader(file);
            Scanner scan = new Scanner(fr);
            int shipped = scan.nextInt();
            fr.close();
            scan.close();
            return shipped;
        } catch (IOException | NoSuchElementException e) {
            return 0;
        }
    }


    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     *
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
        try {
            FileReader fr = new FileReader(file);
            Scanner scan = new Scanner(fr);
            int prime = Integer.parseInt(scan.nextLine());
            fr.close();
            scan.close();
            return (prime == 1);

        } catch (IOException | NoSuchElementException e) {
            return false;
        }

    }


    /**
     * Saves (writes) vehicles from ArrayList of vehicles to file in CSV format one vehicle per line.
     * Each line (vehicle) has following fields separated by comma in the same order.
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     *
     * @param file     File to write vehicles to
     * @param vehicles ArrayList of vehicles to save to file
     */
    public static void saveVehicles(File file, ArrayList<Vehicle> vehicles) {
        try {
            FileWriter vehicleFile = new FileWriter(file);
            String vehicleType = "";
            String vehicleFormat = "";
            String vehicleInfo = "";

            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i) instanceof Drone) {
                    vehicleType = "Drone";
                } else if (vehicles.get(i) instanceof Truck) {
                    vehicleType = "Truck";
                } else if (vehicles.get(i) instanceof CargoPlane) {
                    vehicleType = "Cargo Plane";
                }
                vehicleFormat = vehicleType
                        + "," + vehicles.get(i).getLicensePlate()
                        + "," + vehicles.get(i).getMaxWeight()
                        + "\n";

                vehicleInfo += vehicleFormat;
            }

            vehicleFile.write(vehicleInfo);
            vehicleFile.close();
        } catch (IOException e) {
            System.out.println("File could not be saved");
        }
    }


    /**
     * Saves (writes) packages from ArrayList of package to file in CSV format one package per line.
     * Each line (package) has following fields separated by comma in the same order.
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     *
     * @param file     File to write packages to
     * @param packages ArrayList of packages to save to file
     */
    public static void savePackages(File file, ArrayList<Package> packages) {
        try {
            FileWriter packageFile = new FileWriter(file);
            String packageFormat = "";
            String packageInfo = "";
            for (int i = 0; i < packages.size(); i++) {
                packageFormat = packages.get(i).getID()
                        + "," + packages.get(i).getProduct()
                        + "," + packages.get(i).getWeight()
                        + "," + packages.get(i).getPrice()
                        + "," + packages.get(i).getDestination().getName()
                        + "," + packages.get(i).getDestination().getAddress()
                        + "," + packages.get(i).getDestination().getCity()
                        + "," + packages.get(i).getDestination().getState()
                        + "," + packages.get(i).getDestination().getZipCode()
                        + "\n";
                packageInfo += packageFormat;
            }
            packageFile.write(packageInfo);
            packageFile.close();
        } catch (IOException e) {
            System.out.println("File could not be saved");
        }

    }


    /**
     * Saves profit to text file.
     *
     * @param file   File to write profits to
     * @param profit Total profits
     */

    public static void saveProfit(File file, double profit) {
        try {
            FileWriter profitFile = new FileWriter(file);
            //save profit
            profitFile.write(Double.toString(profit));
            profitFile.close();
        } catch (IOException e) {
            System.out.println("File could not be saved");
        }
    }


    /**
     * Saves number of packages shipped to text file.
     *
     * @param file      File to write profits to
     * @param nPackages Number of packages shipped
     */

    public static void savePackagesShipped(File file, int nPackages) {
        try {
            FileWriter shippedFile = new FileWriter(file);
            //save shipped packages
            System.out.println(Integer.toString(nPackages));
            shippedFile.write(Integer.toString(nPackages));
            shippedFile.close();
        } catch (IOException e) {
            System.out.println("File could not be saved.");
        }
    }

    /**
     * Saves status of prime day to text file. If it is primeDay "1" will be
     * writtern, otherwise "0" will be written.
     *
     * @param file     File to write profits to
     * @param primeDay Whether or not it is Prime Day
     */

    public static void savePrimeDay(File file, boolean primeDay) {
        try {
            FileWriter primeFile = new FileWriter(file);

            //save primeday
            if (primeDay) {
                primeFile.write("1");
            } else {
                primeFile.write("0");
            }

            primeFile.close();

        } catch (IOException e) {
            System.out.println("File could not be saved.");
        }
    }
}