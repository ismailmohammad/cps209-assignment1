/**
 * CPS 209 Assignment 1
 * CarDealership.java
 * @author Mohammad Ismail
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CarDealership {
    // Arraylist of Cars
    private ArrayList<Car> cars; // Reference variable to ArrayList of Cars
    // Filter Flags
    private boolean electricFilter;
    private boolean AWDFilter;
    private boolean priceFilter;
    // Price filter min, max
    private double minPrice;
    private double maxPrice;

    /**
     * Basic CarDealership Constructor which creates an empty ArrayList of Type Car
     * and sets the instance variable cars to it, initializing the dealership's inventory.
     */
    public CarDealership() {
        cars = new ArrayList<Car>();
    }

    /**
     * Initializes a new ArrayList of Type Car and populates the CarDealership's inventory.
     * @param cars ArrayList<Car> of Car objects to initialize the dealership with.
     */
    public CarDealership(ArrayList<Car> cars) {
        cars = new ArrayList<Car>();
        addCars(cars);
    }
    
    static class SafetyRatingSorter implements Comparator<Car> {
        /**
         * Compares the Safety Rating of Car objects in descending order.
         * @param initial Car object to compare against.
         * @param other Car object to compare against.
         * @return 1 if other Car has a greater safety rating, it will come after this one.
         * -1 if the other car has a lower safety rating, the other car should come before the initial.
         * 0 if both cars have the same safety rating.
         */
        public int compare(Car initial, Car other)  {
            if (initial.getSafetyRating() < other.getSafetyRating()) { return 1; }
            if (initial.getSafetyRating() > other.getSafetyRating()) { return -1; }
            return 0;
        }
    }

    static class MaxRangeSorter implements Comparator<Car> {
        /**
         * Compares the Maximum Range of Car Objects in Descending Order (500, 400, 300, etc).
         * @param initial Car object to compare against.
         * @param other Car object to compare against.
         * @return 1 if other Car has a greater maximum range, it will be after this one.
         * -1 if the other car has a lower maximum range, the other car should come before the initial.
         * 0 if both cars have the same maximum range.
         */
        public int compare(Car initial, Car other) {
            if (initial.getMaxRange() < other.getMaxRange()) { return 1; }
            if (initial.getMaxRange() > other.getMaxRange()) { return -1; }
            return 0;
        }
    }

    /**
     * Adds cars from provided ArrayList of Car objects into the CarDealership's
     * inventory.
     * @param newCars a reference to an ArrayList of cars to be added
     */
    public void addCars(ArrayList<Car> newCars) {
        for (int index = 0; index < newCars.size(); index++) {
            cars.add(newCars.get(index));
        }
    }

    /**
     * Attempts to purchase Car from dealership via index of Car as displayed by displayInventory()
     * @param index
     * @return returns a reference to the Car object being purchased.
     * @throws IndexOutOfBoundsException
     */
    public Car buyCar(int index) throws IndexOutOfBoundsException {
        // If index is greater than size of ArrayList of Cars less 1 or if negative, throw IOOBE
        if (index > (cars.size() -1) || (index < 0)) {
            throw new IndexOutOfBoundsException("Index of Car must be within list of cars displayed and cannot be negative.");
        }
        return cars.remove(index);
    }

    /**
     * Attempts to return a car to the dealership and throws IllegalArgumentException
     * if the reference to the Car object is null. ie. an invalid reference.
     * @param car Car object which is to be returned to the dealership.
     * @throws IllegalArgumentException
     */
    public void returnCar(Car car) throws IllegalArgumentException {
        // Make sure Car reference is not null
        if (car == null) {
            throw new IllegalArgumentException("The last Car bought has already been returned. Return will not be processed.");
        }
        // Add Car to the ArrayList
        cars.add(car);
    }

    /**
     * Displays the inventory of the Car Dealership whist applying the filters
     * as specified by the filter flags. Consists of 3 main checks; electric,
     * AWD, and price and checks whether each Car in inventory complies with the
     * requirements prior to displaying.
     */
    public void displayInventory() {
        for (int index = 0; index < cars.size(); index++) {
            // Retrieve the Car object and set selectedCar as its reference for 
            Car selectedCar = cars.get(index);
            // Check whether the filter for electric cars is set and whether the car fails its check ie. not electric
            boolean failElectricFilter = electricFilter && !(selectedCar.getPower() == Car.ELECTRIC_MOTOR);
            // Checks whether the price filter is set and if so check if price is outside of minimum/maximum price bounds.
            boolean failPriceFilter = priceFilter && (selectedCar.getPrice() < minPrice || selectedCar.getPrice() > maxPrice);
            // Fails if AWD Filter flag is set to true however car is not AWD
            boolean failAWDFilter = AWDFilter && !(cars.get(index).getAWD());
            if (!failAWDFilter && !failPriceFilter && !failElectricFilter) {
                System.out.println(index + " " + selectedCar.display());
            }
        }
    }

    /** 
     * Sets the filter for electric so that displayInventory() is able to display
     * only electric/battery powered vehicles.
     */
    public void filterByElectric() {
        electricFilter = true;
    }

    /**
     * Sets the filter for AWD so that displayInventory() is able to display
     * only AWD capable vehicles.
     */
    public void filterByAWD() {
        AWDFilter = true;
    }

    /**
     * Sets price filter on the CarDealership using the minimum and maximum price as provided.
     * @param minPrice Minimum price of Car to filter
     * @param maxPrice Maximum price of Car to filter
     * @throws IllegalArgumentException
     */
    public void filterByPrice(double minPrice, double maxPrice) throws IllegalArgumentException {
        // If any of the prices are negative, if minPrice is greater than maxPrice, or if
        // maxPrice is lower than minPrice, throw IAE. Did not account for maxPrice being zero in
        // case the dealership has some wayward price promotion where it's $0 for some reason. Otherwise
        // another condition such that `maxPrice != 0` would be added.
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice || maxPrice < minPrice) {
            throw new IllegalArgumentException("Please ensure both prices are not negative numbers, that the minimum price is 0 or greater, and that the minimum price is less than or equal to maximum price.");
        }
        priceFilter = true;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Clear all filters for the inventory of Cars.
     */
    public void filtersClear() {
        electricFilter = false;
        AWDFilter = false;
        priceFilter = false;
    }

    /**
     * Sorts the Car Dealership inventory based on price in Ascending order.
     */
    public void sortByPrice() {
        Collections.sort(cars);
    }

    // Safety/MaxRange static instances, so that one instance is created despite multiple
    // CarDealership objects being created.
    public static SafetyRatingSorter safetyComparator = new SafetyRatingSorter();
    public static MaxRangeSorter rangeComparator = new MaxRangeSorter();
    
    /**
     * Sorts the Cars in the inventory in Descending order of Safety Rating.
     */
    public void sortBySafetyRating() {
        Collections.sort(cars, safetyComparator);
    }

    /** 
     * Sorts the Cars in the inventiory in Descending order of maximum range.
     */
    public void sortByMaxRange() {
        Collections.sort(cars, rangeComparator);
    }
}