import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>Database Manager</h1>
 * 
 * Used to locally save and retrieve data.
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
       //TODO


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
     * 
     * If filePath does not exist, a blank ArrayList will be returned.
     * 
     * @param file CSV File
     * @return ArrayList of packages
     */
    public static ArrayList<Package> loadPackages(File file) {
    	//TODO
    }
    
    
    
    
    

    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     * 
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
    	//TODO
    }

    
    
    
    
    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     * 
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
    	//TODO
    }

    
    
    
    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     * 
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
    	//TODO
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

            for (int i = 0; i < vehicles.size() ; i++) {
                if (vehicles.get(i) instanceof Drone) {
                    vehicleType = "Drone";
                } else
                if (vehicles.get(i) instanceof Truck) {
                    vehicleType = "Truck";
                } else
                if (vehicles.get(i) instanceof CargoPlane) {
                    vehicleType = "Cargo Plane";
                }
                vehicleFormat = "Vehicle Type (Truck/Done/Cargo Plane) " + vehicleType
                        + ", Vehicle License Plate " + vehicles.get(i).getLicensePlate()
                        + ", Maximum Carry Weight " + vehicles.get(i).getMaxWeight();

                vehicleFile.write(vehicleFormat);
            }
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
            for (int i = 0; i < packages.size() ; i++) {
            packageFormat = "ID " + packages.get(i).getID()
                    + ", Product Name " + packages.get(i).getProduct()
                    + ", Weight " + packages.get(i).getWeight()
                    + ", Price " + packages.get(i).getPrice()
                    + ", Address Name " + packages.get(i).getDestination().getName()
                    + ", Address " + packages.get(i).getDestination().getAddress()
                    + ", City " + packages.get(i).getDestination().getCity()
                    + ", State " + packages.get(i).getDestination().getState()
                    + ", ZIP Code " + packages.get(i).getDestination().getZipCode() + "\n";
            packageFile.write(packageFormat);

            }
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
            shippedFile.write(nPackages);
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
            if(primeDay) {
                primeFile.write(1);
            } else {
                primeFile.write(0);
            }

        } catch (IOException e) {
            System.out.println("File could not be saved.");
        }
    }
}