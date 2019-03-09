class Car extends Vehicle implements Comparable {
    // Instance Variables
    private String model;
    private int maxRange;
    private double safetyRating;
    private boolean AWD;
    private double price;

    public Car(String mfr, String color, int power, int numWheels, String model, int maxRange, double safetyRating, boolean AWD, double price){
        super(mfr, color, power, numWheels);
        this.model = model;
        this.maxRange = maxRange;
        this.safetyRating = safetyRating;
        this.AWD = AWD;
        this.price = price;
    }

    /**
     * Returns string representation of Car separated by spaces.
     * That is, return Car's vehicle characteristics along with
     * model, max range, safety rating, all wheel drive capable, and price.
     * @return the string representation
     */
    public String display(){
        return String.format("%s %s %d %f %b %f", super.display(), model, maxRange, safetyRating, AWD, price);
    }
}