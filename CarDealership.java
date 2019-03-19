import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CarDealership {
    // Arraylist of Cars
    private ArrayList<Car> cars; // Reference variable to ArrayList of Cars
    // Filter Flags
    private boolean electricFilter;
    private boolean AWDFilter;
    private boolean priceFiler;
    // Price filter min, max
    private double minPrice;
    private double maxPrice;

    public CarDealership() {
        cars = new ArrayList<Car>();
    }
    
    class SafetyRatingSorter implements Comparable<Car> {
        /**
         * 
         * @param initial Car object
         * @param other Car object to compare against.
         * @return 1 if 
         */
        public int compareTo(Car initial, Car other)  {
            if (initial.getSafetyRating() > other.getSafetyRating()) { return 1; }
            if (initial.getSafetyRating() < other.getSafetyRating()) { return -1; }
            return 0;
        }
    }

    public void addCars(ArrayList<Car> newCars) {
        for (int index = 0; index < newCars.size(); index++) {
            cars.add(newCars.get(index));
        }
    }

    public Car buyCar(int index) {
        return cars.remove(index);
    }

    public void returnCar(Car car) {
        // Make sure reference is not null
        if (car != null) {
            cars.add(car);
        }
        // Exception if null
    }

    public void displayInventory() {
        for (int index = 0; index < cars.size(); index++) {
            System.out.println(index + " " + cars.get(index).display());
        }
    }

    public void filterByElectric() {
        electricFilter = true;
    }

    public void filterByAWD() {
        AWDFilter = true;
    }

    public void filterByPrice(double minPrice, double maxPrice) {
        priceFilyer = true;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public void filtersClear() {
        electricFilter = false;
        AWDFilter = false;
        priceFiler = false;
    }

    public void sortByPrice() {
        Collections.sort(cars);
    }

    public void sortBySafetyRating() {
        Array.sort(cars, new SafetyRatingSorter());
    }
}