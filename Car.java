class Car extends Vehicle implements Comparable<Car> {
    // Instance Variables
    private String model;
    private int maxRange;
    private double safetyRating;
    private boolean AWD;
    private double price;

    public enum Model {
        SEDAN, SUV, SPORTS, MINIVAN
    }

    /**
     * Constructs a Car object with the necessary parameters as follows
     * @param mfr String of Manufacturer of Car ie. "Honda", "Toyota", etc.
     * @param color String of colour of Car i.e. "red", "black", etc.
     * @param power Int of power type of Car 0 for electric, 1 for gas
     * @param numWheels Int of number of wheels of Car i.e. 4
     * @param model
     * @param maxRange
     * @param safetyRating
     * @param AWD
     * @param price
     */
    public Car(String mfr, String color, int power, int numWheels, Model model, int maxRange, double safetyRating, boolean AWD, double price){
        super(mfr, color, power, numWheels);
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
     * Returns string representation of Car separated by spaces.
     * That is, return Car's vehicle characteristics along with
     * model, max range, safety rating, all wheel drive capable, and price.
     * @return the string representation "MANUFACTURER COLOUR MODEL MAX_RANGE SAFETY_RATING AWD PRICE"
     */
    public String display(){
        return String.format("%s %s %.1f$ %d %.1f %b", super.display(), model, price, maxRange, safetyRating, AWD);
    }

    /**
     * Compares the Car object with the object in the parameter for equality. Checks manufacturer,
     * power, and number of wheels, along with model and AWD.
     * @param other The car object to compare against
     * @return whether the Car objects are equal.
     */
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

class ElectricCar extends Car {
    // Electric Car Instance Variables
    private int rechargeTime; // Recharge time in minutes
    private String batteryType;

    /**
     * Constructor Method for Electric car
     * @param mfr String of Manufacturer of Car ie. "Honda", "Toyota", etc.
     * @param color String of colour of Car i.e. "red", "black", etc.
     * @param power Int of power type of Car 0 for electric, 1 for gas
     * @param numWheels Int of number of wheels of Car i.e. 4
     * @param model
     * @param maxRange
     * @param safetyRating
     * @param AWD
     * @param price 
     * @param rechargeTime Int of number of minutes it takes to recharge car
     * @param batteryType String of the type of battery ie. "Lithium Ion", etc.
     */
    public ElectricCar(String mfr, String color, int power, int numWheels, Model model, int maxRange, double safetyRating, boolean AWD, double price, int rechargeTime, String batteryType) {
        // Call superclass's constructor method.
        super(mfr, color, power, numWheels, model, maxRange, safetyRating, AWD, price);
        // Initialize electric car instance variables with values.
        this.rechargeTime = rechargeTime;
        this.batteryType = batteryType;
    }

    /**
     * Fetches the recharge time of the Electric Car
     * @return the recharge time of the car in whole minutes
     */
    public int getRechargeTime() {
        return rechargeTime;
    }

    /**
     * Sets the recharge time of the Electric Car
     * @param rechargeTime number of minutes the recharge time should be set to
     */
    public void setRechargeTime(int rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    /**
     * Fetches the battery type of the Electric Car
     * @return the battery type
     */
    public String getBatteryType() {
        return batteryType;
    }
    
    /**
     * Sets the battery type of the Electric Car
     * @param batteryType the batteryType to set
     */
    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    /**
     * Returns string representation of Car separated by spaces.
     * That is, return Car's vehicle characteristics along with
     * model, max range, safety rating, all wheel drive capablibility, price,
     * battery recharge time, and
     * @return the string representation as follows
     * "MANUFACTURER COLOUR MODEL MAX_RANGE SAFETY_RATING AWD PRICE RECHARGE_TIME BAT_TYPE"
     */
    public String display(){
        return String.format("%s %d %s", super.display(), rechargeTime, batteryType);
    }
}