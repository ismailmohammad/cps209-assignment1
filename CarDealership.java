/**
 * CarDealership.java
 * Mohammad Ismail
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

    public CarDealership() {
        cars = new ArrayList<Car>();
    }

    public CarDealership(ArrayList<Car> cars) {
        this.cars = cars;
    }
    
    static class SafetyRatingSorter implements Comparator<Car> {
        /**
         * @param initial Car object to compare against.
         * @param other Car object to compare against.
         * @return 1 if other Car has a greater safety rating, it will come after this one.
         * -1 if the other car has a lower safety rating, the other car should come before the initial.
         * 0 if both cars have the same safety rating.
         */
        public int compare(Car initial, Car other)  {
            if (initial.getSafetyRating() > other.getSafetyRating()) { return 1; }
            if (initial.getSafetyRating() < other.getSafetyRating()) { return -1; }
            return 0;
        }
    }

    static class MaxRangeSorter implements Comparator<Car> {
        /**
         * @param initial Car object to compare against.
         * @param other Car object to compare against.
         * @return 1 if other Car has a greater maximum range, it will come after this one.
         * -1 if the other car has a lower maximum range, the other car should come before the initial.
         * 0 if both cars have the same maximum range.
         */
        public int compare(Car initial, Car other) {
            if (initial.getMaxRange() > other.getMaxRange()) { return 1; }
            if (initial.getMaxRange() < other.getMaxRange()) { return -1; }
            return 0;
        }
    }

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

    public void displayInventory() {
        for (int index = 0; index < cars.size(); index++) {
            boolean display = true;
            if (electricFilter && !(cars.get(index).getPower() == Car.ELECTRIC_MOTOR)) {
                display = false;
            }
            if (AWDFilter && !(cars.get(index).getAWD())){
                display = false;
            }
            if (priceFilter) {
                double price = cars.get(index).getPrice();
                if (price < minPrice || price > maxPrice) {
                    display = false;
                }
            }
            if (display) {
                System.out.println(index + " " + cars.get(index).display());
            }
        }
    }

    public void filterByElectric() {
        electricFilter = true;
    }

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
        // If
        if () {

        }
        priceFilter = true;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public void filtersClear() {
        electricFilter = false;
        AWDFilter = false;
        priceFilter = false;
    }

    public void sortByPrice() {
        Collections.sort(cars);
    }

    // Safety/MaxRange static instances.
    public static SafetyRatingSorter safetyComparator = new SafetyRatingSorter();
    public static MaxRangeSorter rangeComparator = new MaxRangeSorter();
    
    public void sortBySafetyRating() {
        Collections.sort(cars, safetyComparator);
    }

    public void sortByMaxRange() {
        Collections.sort(cars, rangeComparator);
    }
}