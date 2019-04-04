/**
 * CPS 209 Assignment 2
 * Car.java
 * @author Mohammad Ismail
 */
public class Car extends Vehicle implements Comparable<Car> {
    // Instance Variables
    private String model;
    private int maxRange;
    private double safetyRating;
    private boolean AWD;
    private double price;

    /**
     * Constant for number of car wheels. All cars have 4 wheels
     * unless speaking of the 2016 Pembleton Supersports or of that sort.
     */
    public static final int NUM_CAR_WHEELS = 4;

    /**
     * Enumerate model into the 4 possible types of Cars. Sedan,
     * SUV, Sports, and Minivan.
     */
    public enum Model {
        SEDAN, SUV, SPORTS, MINIVAN
    }

    /**
     * Constructs a Car object with the necessary parameters as follows
     * @param mfr String of Manufacturer of Car ie. "Honda", "Toyota", etc.
     * @param color String of colour of Car i.e. "red", "black", etc.
     * @param power Int of power type of Car 0 for electric, 1 for gas
     * @param model Model of the car
     * @param maxRange Maximum range of the Car assuming full fuel/energy capacity
     * @param safetyRating Safety Standard rating for the Car in question.
     * @param AWD Whether the Car is AWD capable or 2WD
     * @param price Price of the car in dollars and cents.
     */
    public Car(String mfr, String color, int power, Model model, int maxRange, double safetyRating, boolean AWD, double price) throws InstantiationException {
        // The superclass constructor call implements the NUM_CAR_WHEELS instead of an extra parameter as
        // all cars have 4 wheels with the exception of some very uncommon 3 wheeled 'Cars'.
        super(mfr, color, power, NUM_CAR_WHEELS);
        this.model = model.toString();
        this.maxRange = maxRange;
        this.safetyRating = safetyRating;
        this.AWD = AWD;
        this.price = price;
    }

    /**
     * Fetches the safetyRating of the Car object in question.
     * @return the safety rating
     */
    public double getSafetyRating() {
        return safetyRating;
    }

    /**
     * Fetches the Maximum range of the car
     * @return the max range of the Car object.
     */
    public int getMaxRange() {
        return maxRange;
    }

    /**
     * Fetches whether the car is AWD capable
     * @return the AWD status of vehicle.
     */
    public boolean getAWD() {
        return AWD;
    }

    /**
     * Fetches the price of the Car object
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns string representation of Car separated by spaces.
     * That is, return Car's vehicle characteristics along with
     * model, price, safety rating, and maximum range.
     * @return the string representation "MANUFACTURER COLOUR MODEL PRICE SAFEY_RATING MAX_RANGE"
     */
    public String display(){
        return super.display() + " " + model + String.format(" %.1f$ ", price) + "SF: " + safetyRating + " RNG: " + maxRange + (getAWD() ? " AWD" : " 2WD");
    }   

    /**
     * Compares the Car object with the object in the parameter for equality. Checks manufacturer,
     * power, and number of wheels, along with model and AWD.
     * @param other The car object to compare against
     * @return whether the Car objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        Car otherCar = (Car) other;
        // Checks power, mfr, and numWheels
        if (!(super.equals(other))) {
            return false;
        }
        if (!(model.equals(otherCar.model))) {
            return false;
        }
        if (AWD != otherCar.AWD) {
            return false;
        }
        return true;
    }

    /**
     * Compares the Car object with the 
     * @param other the Car object to compare with.
     * @return 1 if other Car has lesser price current car will come after
     * -1 if other Car has greater price, current car will come before it.
     * lower price, and 0 if both of the prices are equal.
     */
    public int compareTo(Car other) {
        if (this.price > other.price) {
            return 1;
        }
        if (this.price < other.price) {
            return -1;
        }
        return 0;
    }
}