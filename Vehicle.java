public class Vehicle {
	// Instance variables
	private String mfr;
	private String color;
	private int power;
	private int numWheels;
	// Initialize public constants
	public int ELECTRIC_MOTOR;
	public int GAS_ENGINE;
	
	/**
	 * Constructor Method to create a vehicle object with the parameters
	 * @param mfr - Vehicle Manufacturer
	 * @param color - Color of vehicle
	 * @param power - Power of vehicle
	 * @param numWheels - Number of wheels the vehicle has
	 */
	public Vehicle(String mfr, String color, int power, int numWheels) {
		this.mfr = mfr;
		this.color = color;
		this.power = power;
		this.numWheels = numWheels;
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
		return getMfr() + " " + getColor();
	}
}