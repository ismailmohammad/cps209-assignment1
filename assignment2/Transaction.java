import java.util.Calendar;
import java.text.SimpleDateFormat;
/**
 * CPS 209 Assignment 2
 * Car.java
 * @author Mohammad Ismail
 */
public class Transaction {
    // Instance Variables
    private int id;
    private Calendar date;
    private Car car;
    private String salesPerson;
    private String transactionType;
    private double salePrice;
    // Static Simple Date Format to be used across all Transactions.
    private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");

    /** Enumberated Type to hold BUY and RETURN as the two types */
    public enum Type {
        BUY, RET
    }

    /**
     * Construct a Transaction object with the specified id, date, Car object, the sales person, whether the
     * car was purchased or returned and also the sale price (ie. accounting for additional warranty packages, etc.)
     * @param id Transaction ID
     * @param date Date of Transaction
     * @param car 
     * @param salesPerson For whom the commission tolls
     * @param transType BUY or RET
     * @param salePrice Sale Price of Car
     */
    public Transaction(int id, Calendar date, Car car, String salesPerson, Type transType, double salePrice) {
        // Set instance variables to their appropriate parameters.
        this.id = id;
        this.date = date;
        this.car = car;
        this.salesPerson = salesPerson;
        transactionType = transType.toString();
        this.salePrice = salePrice;
    }

    /**
     * Fetch the sales person who is linked to the transaction.
     * @return The person who facilitated the purchase or return.
     */
    public String getSalesPerson() {
        return salesPerson;
    }

    /**
     * Returns the Date on which the transaction occurred.
     * @return the date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * @return the salePrice
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Fetch the Car associated with the transaction.
     * @return reference to Car object
     */
    public Car getCar() {
        return car;
    }
    
    /**
     * Displays the transaction data in the format as shown below:
     * "ID DATE TYPE SALES_PERSON CAR SALE_PRICE"
     */
    public String display() {
        // The two main external components include the car's display method and also displaying the Date appropriately
        return String.format("ID: %d %s %s Sales Person: %s %s Sale Price: %.1f$", id, sdf.format(date.getTime()), transactionType, salesPerson, car.display(), salePrice);
    }

}