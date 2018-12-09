import java.util.ArrayList;

/**
 * CS18000 Project 5 - Amazon
 *
 * <h1>Vehicle</h1> Represents a vehicle
 *
 * @author Jacquie Yu, Siddarth Pillai
 * @version 2018-12-06
 */

public class Vehicle implements Profitable {
    private String licensePlate;
    private double maxWeight;
    private double currentWeight;
    private int zipDest;
    private ArrayList<Package> packages;


    /**
     * Default Constructor
     */
    //============================================================================
    public Vehicle() {
        this("", 0);
        this.currentWeight = 0;
        this.zipDest = 0;
        this.packages = new ArrayList<>();
    }

    //============================================================================


    /**
     * Constructor
     *
     * @param licensePlate license plate of vehicle
     * @param maxWeight    maximum weight of vehicle
     */
    //============================================================================
    public Vehicle(String licensePlate, double maxWeight) {
        this.licensePlate = licensePlate;
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
        this.zipDest = 0;
        this.packages = new ArrayList<>();
    }

    //============================================================================


    /**
     * Returns the license plate of this vehicle
     *
     * @return license plate of this vehicle
     */
    public String getLicensePlate() {
        return licensePlate;
    }


    /**
     * Updates the license plate of vehicle
     *
     * @param licensePlate license plate to be updated to
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    /**
     * Returns the maximum weight this vehicle can carry
     *
     * @return the maximum weight that this vehicle can carry
     */
    public double getMaxWeight() {
        return maxWeight;
    }


    /**
     * Updates the maximum weight of this vehicle
     *
     * @param maxWeight max weight to be updated to
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }


    /**
     * Returns the current weight of all packages inside vehicle
     *
     * @return current weight of all packages inside vehicle
     */
    public double getCurrentWeight() {
        return currentWeight;
    }


    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    /**
     * Returns the current ZIP code desitnation of the vehicle
     *
     * @return current ZIP code destination of vehicle
     */
    public int getZipDest() {
        return zipDest;
    }


    /**
     * Updates the ZIP code destination of vehicle
     *
     * @param zipDest ZIP code destination to be updated to
     */
    public void setZipDest(int zipDest) {
        this.zipDest = zipDest;
    }


    /**
     * Returns ArrayList of packages currently in Vehicle
     *
     * @return ArrayList of packages in vehicle
     */
    public ArrayList<Package> getPackages() {
        return packages;
    }


    /**
     * Adds Package to the vehicle only if has room to carry it (adding it would not
     * cause it to go over its maximum carry weight).
     *
     * @param pkg Package to add
     * @return whether or not it was successful in adding the package
     */
    public boolean addPackage(Package pkg) {
        if ((currentWeight + pkg.getWeight()) <= maxWeight) {
            packages.add(pkg);
            return true;
        } else return false;
    }


    /**
     * Clears vehicle of packages and resets its weight to zero
     */
    public void empty() {
        this.packages = new ArrayList<>();
        this.currentWeight = 0;
    }


    /**
     * Returns true if the Vehicle has reached its maximum weight load, false
     * otherwise.
     *
     * @return whether or not Vehicle is full
     */
    public boolean isFull() {
        return (currentWeight >= maxWeight);
    }

    @Override
    public double getProfit() {
        return 0;
    }

    @Override
    public String report() {
        return null;
    }

    /**
     * Fills vehicle with packages with preference of date added and range of its
     * destination zip code. It will iterate over the packages intially at a range
     * of zero and fill it with as many as it can within its range without going
     * over its maximum weight. The amount the range increases is dependent on the
     * vehicle that is using this. This range it increases by after each iteration
     * is by default one.
     *
     * @param warehousePackages List of packages to add from
     */
    public void fill(ArrayList<Package> warehousePackages) {
        int diffCounter = 0;
        int checkOnce = -1;
        boolean recheck = false;
        boolean sameI = false;
        int overWeight = 0;
//        setZipDest(warehousePackages.get(0).getDestination().getZipCode());
//        System.out.println("While");
        while (!isFull() && (warehousePackages.size() - overWeight) > 0 ) {
//            System.out.println("For");
            for (int i = 0; i < warehousePackages.size(); i++) { //go through list
                sameI = false;
                int destination = warehousePackages.get(i).getDestination().getZipCode(); //get next location
                int difference = Math.abs(destination - this.zipDest); //Distance b/w current location and next
                if (difference == diffCounter) {
                    if (((warehousePackages.get(i).getWeight() + this.currentWeight) < this.maxWeight)) { //if not fat
                        addPackage(warehousePackages.get(i));
                        currentWeight += warehousePackages.get(i).getWeight();
                        warehousePackages.remove(i);
                        sameI = true;
                        break;
                    } else { //if exceeds, needs to check for when the same one comes back around
                        overWeight += 1;
                    }
                }
            }
//            System.out.println("Absolute Value: " + diffCounter);
            if (!sameI) {
                diffCounter++;
            }
        }

    }


}