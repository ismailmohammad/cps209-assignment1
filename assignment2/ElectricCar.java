/**
 * CPS 209 Assignment 2
 * ElectricCar.java
 * @author Mohammad Ismail
 */
class ElectricCar extends Car {
    // Electric Car Instance Variables
    private int rechargeTime; // Recharge time in minutes
    private String batteryType; // Type of battery of Car

    /**
     * Constructor Method for Electric car
     * @param mfr String of Manufacturer of Car ie. "Honda", "Toyota", etc.
     * @param color String of colour of Car i.e. "red", "black", etc.
     * @param power Int of power type of Car 0 for electric, 1 for gas
     * @param numWheels Int of number of wheels of Car i.e. 4. It is assumed that
     * all cars have 4 wheels.
     * @param model Model of the car
     * @param maxRange Maximum range of the Car assuming full fuel/energy capacity
     * @param safetyRating Safety Standard rating for the Car in question.
     * @param AWD Whether the Car is AWD capable or 2WD
     * @param price Price 
     * @param rechargeTime Int of number of minutes it takes to recharge car
     * @param batteryType String of the type of battery ie. "Lithium Ion", etc.
     */
    public ElectricCar(String mfr, String color, int power, Model model, int maxRange, double safetyRating, boolean AWD, double price, int rechargeTime, String batteryType) throws InstantiationException{
        // Call superclass's constructor method.
        super(mfr, color, power, model, maxRange, safetyRating, AWD, price);
        // Initialize electric car instance variables with respective values.
        this.rechargeTime = rechargeTime;
        // If battery type is not specified. i.e an empty string, it will default to Lithium
        if (batteryType.equals("")) {
            this.batteryType = "Lithium";
        } else {
            this.batteryType = batteryType;
        }
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
     * battery type, and battery recharge time.
     * @return the string representation as follows
     * "MANUFACTURER COLOUR MODEL PRICE SAFETY_RATING MAX_RANGE BAT_TYPE RECHARGE_TIME"
     */
    public String display(){
        return String.format("%s EL, BAT: %s RCH: %d", super.display(), batteryType, rechargeTime);
    }
}