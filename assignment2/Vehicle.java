import java.util.Set;
import java.util.TreeSet;

/**
 * CPS 209 Assignment 2
 * Vehicle.java
 * @author Mohammad Ismail
 */
public class Vehicle {
	// Instance variables
	private String mfr;
	private String color;
	private int power;
	private int numWheels;
	/** VIN number of vehicle 100-499 */
	private int VIN;
	/** Contains a set of used valid VIN numbers to ensure that each car has an unique VIN */
	public static Set<Integer> usedVINs = new TreeSet<Integer>();
	// Initialize public constants
	public static final int ELECTRIC_MOTOR = 0;
	public static final int GAS_ENGINE = 1;
	// Minimum and Maximum Range of VINs (Min to Max inclusive) Min must be <= max
	public static final int VIN_MAX = 499;
	public static final int VIN_MIN = 100;
	
	/**
	 * Constructor Method to create a vehicle object with the parameters
	 * @param mfr - Vehicle Manufacturer
	 * @param color - Color of vehicle ie. "red", "black", etc.
	 * @param power - Power of vehicle
	 * @param numWheels - Number of wheels the vehicle has
	 */
	public Vehicle(String mfr, String color, int power, int numWheels) throws InstantiationException {
		this.mfr = mfr;
		this.color = color;
		this.power = power;
		this.numWheels = numWheels;
		int maxNumVIN = VIN_MAX - VIN_MIN + 1; // Add 1 since the initial value is inclusive
		// Generate Random VIN between 100 and 499
		int generatedVIN = (int) (Math.random() * maxNumVIN + VIN_MIN);
		// If the VIN number is already used, attempt to generate a new one.
		while (usedVINs.contains(generatedVIN)) {
			// If the number of available VIN has already been used, alert dealership
			if (usedVINs.size() >= maxNumVIN) {
				throw new InstantiationException("All " + maxNumVIN + " valid VIN numbers have been used. Cannot register this additional vehicle.");
			}
			generatedVIN = (int) (Math.random() * maxNumVIN + VIN_MIN);
		}
		// Sets the vehicle's VIN Number and add it to set of used VIN numbers.
		this.VIN = generatedVIN;
		usedVINs.add(generatedVIN);
	}

	/**
	 * Get the Vehicle's manufacturer
	 * @return the vehicle manufacturer
	 */
	public String getMfr() {
		return mfr;
	}

	/**
	 * Set the manufacturer of the vehicle.
	 * @param mfr the manufacturer to set for vehicle
	 */
	public void setMfr(String mfr) {
		this.mfr = mfr;
	}

	/**
	 * Get the colour of the vehicle.
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Set the vehicle's colour.
	 * @param color colour to which the vehicle should be set to
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Get the power level of the vehicle.
	 * @return the power of the vehicle
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Set the power level of the vehicle.
	 * @param power the power to set vehicle to
	 */
	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * Get the number of wheels the vehicle has.
	 * @return the Number of wheels that the vehicle has
	 */
	public int getNumWheels() {
		return numWheels;
	}

	/**
	 * Set the number of wheels pertaining to the vehicle
	 * @param numWheels the number of wheels to set vehicle to
	 */
	public void setNumWheels(int numWheels) {
		this.numWheels = numWheels;
	}

	/**
	 * Fetches the VIN number of the vehicle.
	 * @return the vIN
	 */
	public int getVIN() {
		return VIN;
	}

	/**
	 * Checks whether the vehicles have the same manufacturer, power, and number of wheels.
	 * @param other - Other Vehicle Object to compare against
	 * @return whether the two Vehicles are equal
	 */
	public boolean equals(Object other){
		Vehicle otherVehicle = (Vehicle) other;
		if (!mfr.equals(otherVehicle.getMfr())) {
			return false;
		} else if (power != otherVehicle.getPower()) {
			return false;
		} if (numWheels != otherVehicle.getNumWheels()) {
			return false;
		}
		return true;
	}

	/**
	 * Display the Vehicle's manufacturer name and colour.
	 * @return string representation of vehicle
	 */
	public String display() {
		return "VIN: " + getVIN() + " " + getMfr() + " " + getColor();
	}
}