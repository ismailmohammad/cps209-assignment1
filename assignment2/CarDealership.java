/**
 * CPS 209 Assignment 2
 * CarDealership.java
 * @author Mohammad Ismail
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Random;

public class CarDealership {
    // Arraylist of Cars
    private ArrayList<Car> cars; // Reference variable to ArrayList of Cars
    // Filter Flags
    private boolean electricFilter = false;
    private boolean AWDFilter = false;
    private boolean priceFilter = false;
    // Price filter min, max
    private double minPrice;
    private double maxPrice;
    // Sales Team
    private SalesTeam team;
    // Accounting System for the dealership
    private AccountingSystem system;
    // Initialize default year for dealership transactions.
    private static final int CURRENT_YEAR = 2019;
    // Number of months in a year
    public static final int NUM_MONTHS_YEAR = 12;
    // Random Generator
    private static Random random = new Random();

    /**
     * Basic CarDealership Constructor which creates an empty ArrayList of Type Car
     * and sets the instance variable cars to it, initializing the dealership's inventory.
     * Also initializes the dealership's sales team.
     */
    public CarDealership() {
        cars = new ArrayList<Car>();
        // Create a sales team object containing sales people of unmatched prowess and assign it to dealership
        team = new SalesTeam();
        // Create an Accounting system object for the dealership to use.
        system = new AccountingSystem();
    }

    /**
     * Initializes a new ArrayList of Type Car and populates the CarDealership's inventory.
     * @param cars ArrayList<Car> of Car objects to initialize the dealership with.
     */
    public CarDealership(ArrayList<Car> cars) {
        cars = new ArrayList<Car>();
        // Create a sales team object containing sales people of unmatched prowess and assign it to dealership
        team = new SalesTeam();
        // Create an Accounting system object for the dealership to use.
        system = new AccountingSystem();
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
     * Attempts to purchase a Car from the dealership using the VIN number provided as displayed in displayInventory().
     * @param VIN - VIN of the car
     * @return Receipt of purchase if successful.
     */
    public String buyCar(int VIN) throws IllegalArgumentException {
        // Checks if number of cars is greater than 0
        if (cars.size() == 0) {
            throw new IllegalArgumentException("There are no cars in this delearship to purchase. Please add cars.\n");
        }
        // Initialize car to return as null;
        Car carToBuy = null;
        // Checks if the VIN is valid
        for (Car car : cars) {
            // If the VIN number matches up to a car within the dealership, set the purchase car as a reference to it.
            if (VIN == car.getVIN()) {
                carToBuy = car;
                // Discontinue loop as the car has been found.
                break;
            }
        }
        // Check Validity of the VIN and throw IllegalArgumentException if it is invalid
        if (carToBuy == null) {
            throw new IllegalArgumentException("Invalid VIN number entered, please enter a valid VIN number from the inventory.\n");
        }
        // Acquire random sales person to whom the sales commission should go towards.
        String randomSalesPerson = team.selectTeamMember();
        // Generate a random month to initialize the date with.
        int randomMonth = (int) (Math.random() * NUM_MONTHS_YEAR);
        // Initialize the date with the current year and random month and the day as 1 as an initial placeholder.
        Calendar date = new GregorianCalendar(CURRENT_YEAR, randomMonth, 1);
        // Then generate a random day using the actual maximum day of the month generated and that as the day.
        int randomDay = random.nextInt(date.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1;
        date.set(Calendar.DAY_OF_MONTH, randomDay);
        // Remove the car from the ArrayList of cars
        cars.remove(carToBuy);
        // Create a Purchase transaction with the indicated date, Car, sales person, and the car's price as the sale price and return the sale receipt.
        return system.add(date, carToBuy, randomSalesPerson, Transaction.Type.BUY, carToBuy.getPrice());
    }

    /**
     * Attempts to return a car to the dealership and throws IllegalArgumentException
     * if the reference to the Car object is null. ie. an invalid reference.
     * @param transactionID ID of a transaction to return.
     * @throws IllegalArgumentException
     */
    public void returnCar(int transactionID) throws IllegalArgumentException {
        // Make sure transaction ID is valid
        Transaction transaction = system.getTransaction(transactionID);
        if (transaction == null) {
            throw new IllegalArgumentException("Invalid Transaction ID Entered. Please enter a valid ID.\n");
        }
        if (!transaction.getTransactionType().equals("BUY")) {
            throw new IllegalArgumentException("Only purchases or BUY transactions can be returned. The transaction id entered is not a purchase.\n");
        }
        // Retrive the Car object tied to the transaction.
        Car carToReturn = transaction.getCar();
        int returnCarVIN = carToReturn.getVIN();
        // Ensure that the Car has not already been returned, or throw exception and prompt user if it already has.
        if (system.carAlreadyReturned(returnCarVIN)) {
            throw new IllegalArgumentException("This Car has already been returned. VIN: " + returnCarVIN + "\n");
        }
        // Retrieve the transaction's original purchase date.
        Calendar date = transaction.getDate();
        // Generate New Date within same month which is later than the purchase date
        int currentDay = date.get(Calendar.DAY_OF_MONTH);
        int maxDayInMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH);
        // Int to hold the generated day.
        int generatedDay;
        // If the purchase date is the last of the month, then there is no more days within the same month which is later
        // As such, the return date is set to the first of the following month. (Current Design Choice). Alternatively, any day of the
        // following month could have been chosen or this edge case could have been made so that the time within the day is later than purchase, etc.
        if (currentDay == maxDayInMonth) {
            // Set the day to the first of the month.
            generatedDay = 1;
            // Set the month to the following month.
            date.set(Calendar.MONTH, (date.get(Calendar.MONTH) + 1));
        } else {
            // Add one to current day as the date is required to be after the current day and then add the (max - min)
            generatedDay = (currentDay + 1) + random.nextInt(date.getActualMaximum(Calendar.DAY_OF_MONTH) - currentDay);
        }
        // Set the day of the month to the generated day.
        date.set(Calendar.DAY_OF_MONTH, generatedDay);
        // Return via the same Sales Person, and initial sale price and display the receipt.
        System.out.println(system.add(date, carToReturn, transaction.getSalesPerson(), Transaction.Type.RET, transaction.getSalePrice()));
        // Add Car back to the ArrayList
        cars.add(carToReturn);
    }

    /**
     * Displays the inventory of the Car Dealership whist applying the filters
     * as specified by the filter flags. Consists of 3 main checks; electric,
     * AWD, and price and checks whether each Car in inventory complies with the
     * requirements prior to displaying.
     */
    public void displayInventory() {
        System.out.println(); // New line before to separate inventory within commandline
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
                System.out.println(selectedCar.display());
            }
        }
        System.out.println(); // New line at end to separate inventory within commandline
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

    /** 
     * Displays the Dealership's sales team.
     */
    public void displayTeam() {
        // Call the Sales Team's display method.
        team.display();
    }

    /**
     * Display the Accounting Stats for Dealership sales for the year.
     */
    public void displaySalesStats() {
        // call the Accounting System's statistics method
        system.displayStatistics();
    }

    /**
     * Display all the Transactions for the year including both purchases and returns.
     */
    public void displayTransactions() {
        // Call the Accounting System's transaction display method.
        system.displayTransactions();
    }

    /**
     * Retreives and display's the dealership's Top Sales Person/People
     */
    public void displayTopSalesPerson() {
        // Print out the Top Sales along with a new line for padding/aesthetics.
        System.out.println(system.getTopSalesPerson() + "\n");
    }

    public void displayMonthlySales(int month) throws IllegalArgumentException {
        // Ensure that the month entered is between 0 and 11 as expected.
        if (month < 0 || month > (NUM_MONTHS_YEAR - 1)) {
            throw new IllegalArgumentException("Month must be between 0 to 11, where 0 is January and 11 is December");
        }
        // Display the monthly sales.
        system.displayMonthlySales(month);
    }
}