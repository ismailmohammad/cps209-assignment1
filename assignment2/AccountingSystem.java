import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * CPS 209 Assignment 2
 * Car.java
 * @author Mohammad Ismail
 */
public class AccountingSystem {
    /** TreeMap Containing the Transactions for the accounting System. */
    private Map<Integer, Transaction> transactions;
    /** Start ID of Transactions */
    private static final int TRANSACTION_ID_START = 1;
    /** Maximum ID for Transactions */
    private static final int TRANSACTION_ID_MAX = 99;
    /** Constant containing Months of the Year */
    public static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };
    

    /** Create new Accounting System Object */
    public AccountingSystem() {
        // Initialize Transactions with a Treemap mapping the transaction id to the Transaction object.
        transactions = new TreeMap<Integer, Transaction>();
    }

    /**
     * Add a Transaction and its ID to the Accounting system.
     * @param date
     * @param car
     * @param salesPerson
     * @param type
     * @param salePrice
     * @return
     */
    public String add(Calendar date, Car car, String salesPerson, Transaction.Type type, double salePrice) {
        // Generate Random Transaction id between 1 and 99
        int id = (int) (Math.random() * TRANSACTION_ID_MAX + TRANSACTION_ID_START);
        while (transactions.containsKey(id)) {
            // Regenerate key if collision exists.
            id = (int) (Math.random() * TRANSACTION_ID_MAX + TRANSACTION_ID_START);
        }
        // Create new Transaction object and add to TreeMap
        Transaction newTrans = new Transaction(id, date, car, salesPerson, type, salePrice);
        transactions.put(id, newTrans);
        // Return transaction's string representation.
        return newTrans.display();
    }

    /**
     * Returns the transaction specified via id or null if no transaction exists
     * @param id of the transaction to retrieve.
     * @return
     */
    public Transaction getTransaction(int id) {
        // Return the transaction should one exist by the id specified.
        if (transactions.containsKey(id)) {
            return transactions.get(id);
        }
        return null;
    }

    /**
     * Determines whether a Car has already been returned to the dealership.
     * Used to prevent duplicate returns.
     * @param VIN number of the Car that is being attempted to return
     * @return whether the car has been already returned.
     */
    public boolean carAlreadyReturned(int VIN) {
        // If no transactions have been made yet, it is impossible for it to have been previously returned.
        if (transactions.size() < 1) {
            return false;
        }
        // Set up Iterator to iterate through Map Keys.
        Iterator<Integer> idIterator = transactions.keySet().iterator();
        // Transaction Object variable to be retrieved and used to later check VIN against.
        Transaction transaction;
        while (idIterator.hasNext()) {
            // Retrieve the transaction
            transaction = transactions.get(idIterator.next());
            // Check if the transaction is of type Return
            if (transaction.getTransactionType().equals("RET")) {
                // Retrieve the associated Car Object's VIN and return true if it's a match
                if (transaction.getCar().getVIN() == VIN) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Display the Sales statistics for the year.
     */
    public void displayStatistics() {
        // Ensure Transactons Exist.
        if (transactions.size() > 0) {
            // All the statistics are grouped together to iterate through all once instead of separate iterations of O(n) each time.
            double totalSales = 0.0;
            // Total number of Cars Sold/Returned during the year.
            int totalCarsSold = 0;
            int totalCarsReturned = 0;
            // Int Array containing the number of Cars sold per month
            ArrayList<Integer> carsPerMonth = new ArrayList<Integer>();
            // Initialize the Array List with 0 cars sold per month
            for (int i = 0; i < 12; i++) {
                carsPerMonth.add(0);
            }
            // The maximum # of Cars sold, used to find the Best Month.
            int maxCarsPerMonth = 0;
            // Create a set containng the keys of the transactions objects to iterate through
            Set<Integer> idSet = transactions.keySet();
            for (int id : idSet) {
                // Retreive the Transaction via valid key from set
                Transaction transaction = transactions.get(id);
                // Retreive the Month of the transaction
                int monthIndex = transaction.getDate().get(Calendar.MONTH);
                // If the Transaction is a purchase, add its sale price to the totalSales
                if (transaction.getTransactionType().equals("BUY")) {
                    totalSales += transaction.getSalePrice();
                    // Increment the number of cars sold. (Even if later returned, as they were still sold to someone initially)
                    totalCarsSold++;
                    // Increment the number of cars sold within that month by 1.
                    carsPerMonth.set(monthIndex, carsPerMonth.get(monthIndex) + 1);
                    continue;
                }
                if (transaction.getTransactionType().equals("RET")) {
                    // Decrease the total sales by the Sale amount returned.
                    totalSales -= transaction.getSalePrice();
                    // Increment the number of Cars returned
                    totalCarsReturned++;
                    continue;
                }
            }
            // Calculate the average amount of sales per month.
            double averageSalesPerMonth = totalSales / 12;
            // Calculate the month with the most sales.
            for (int cars : carsPerMonth) {
                // If the number of cars in the month, exceeds the current maximum, it becomes the nex max.
                if (cars > maxCarsPerMonth) {
                    maxCarsPerMonth = cars;
                }
            }
            // Initiaiize Best Month as an empty String.
            String bestMonth = "";
            // Get the first month where the maximum occurs.
            int month = carsPerMonth.indexOf(maxCarsPerMonth);
            while(month > -1) {
                // Add the best selling month to the best month String.
                bestMonth += MONTHS[month] + " - Cars Sold: " + maxCarsPerMonth + ". ";
                // Invalidate the month's data by setting to -1 to find any other months with the same date
                carsPerMonth.set(month, -1);
                // Get the next occurence of the maximum month.
                month = carsPerMonth.indexOf(maxCarsPerMonth);
            }
            // Display information as follows: TOTAL_SALES, AVG_SALES, TOTAL_SOLD, TOTAL_RETURNED, BEST_MONTHS
            System.out.printf("Total Sales: %.1f$, Average Sales: %.1f$, Total Sold: %d, Total Returned: %d, Best Month(s): %s\n\n", totalSales, averageSalesPerMonth, totalCarsSold, totalCarsReturned, bestMonth);
        } else {
            // Prompt user that Cars haven't yet been purchased or returned.
            System.out.println("Cars have yet to be purchased or returned. Please make a transaction to see stats.\n");
        } 
    }

    /** 
     * Display all the sales/returns for the year.
     */
    public void displayTransactions() {
        // Ensure that there are transactions to display.
        if (transactions.size() > 0) {
            // Create a set containng the keys of the transactions objects to iterate through
            Set<Integer> idSet = transactions.keySet();
            for (int id : idSet) {
                // Retreive the Transaction via valid key from set and display it.
                System.out.println(transactions.get(id).display());
            }
            // Add a new line for padding.
            System.out.println();
        } else {
            System.out.println("Cars have not been purchased or returned yet.\n");
        }
    }

    /**
     * Get the Sales Person who sold the highest amount of Cars.
     * @return
     */
    public String getTopSalesPerson() {
        String topSalesPerson = "Top Sales: ";
        if (transactions.size() < 1) {
            return "No sales yet";
        }
        // Maximum number of Cars sold.
        int maxCarsSold = 0;
        // Maps a Sales Person to the amount of cars they sold.
        Map<String, Integer> salesPersonMap = new TreeMap<String, Integer>();
        // The Current Sales Person of the transaction being reviewed
        String salesPerson;
        // This loop is used to retreive and sort transactions via Sales People
        for (int id : transactions.keySet()) {
            Transaction transaction = transactions.get(id);
            // Skip the Transaction if it is not a purchase.
            if (!transaction.getTransactionType().equals("BUY")) {
                continue;
            }
            // Otherwise Retreive the Transaction via valid key from set
            salesPerson = transaction.getSalesPerson();
            // If the sales Person already exists in the Map, increment it
            if (salesPersonMap.containsKey(salesPerson)) {
                salesPersonMap.put(salesPerson, (salesPersonMap.get(salesPerson) +1));
            } else {
                // Otherise initialize the SalesPerson with 1 Car sold
                salesPersonMap.put(salesPerson, 1);
            }
        }
        // Create an iterator of the keys of the Map.
        Iterator<String> iterator = salesPersonMap.keySet().iterator();
        // Initialize the number of cars sold by each employee initially as 0.
        int numCarsSold = 0;
        while (iterator.hasNext()) {
            salesPerson = iterator.next();
            numCarsSold = salesPersonMap.get(salesPerson);
            if (numCarsSold > maxCarsSold) {
                maxCarsSold = numCarsSold;
            }
        }
        // reinitialize iterator to now display only those that are equal to the maximum.
        iterator = salesPersonMap.keySet().iterator();
        // Add the top sales people to the topSalesPerson String.
        while (iterator.hasNext()) {
            salesPerson = iterator.next();
            numCarsSold = salesPersonMap.get(salesPerson);
            // If the salesPerson sold less than the maximum, remove them from the top salesPerson Map.
            if (numCarsSold == maxCarsSold) {
                // Add the Top Sales Person and their Car count to the top sales person string.
                topSalesPerson += salesPerson + " - Cars Sold: " + salesPersonMap.get(salesPerson) + ". ";
            }
        }
        return topSalesPerson;
    }

    /**
     * Displays all the sales/BUY transactions for a month.
     * @param month Takes the number of a month (0-11) 0-January, 11- December
     */
    public void displayMonthlySales(int month) {
        if (transactions.size() > 0) {
            // Create iterator for transactions via ID.
            Iterator<Integer> iterator = transactions.keySet().iterator();
            // int and Transaction variables to hold the ID and transaction object.
            int transactionID;
            Transaction transaction;
            // Initialize the monthly sales as 0. Used to display a custom message to user.
            int monthlySales = 0;
            while (iterator.hasNext()) {
                // Fetch the next ID
                transactionID = iterator.next();
                // Retrieve the transaction
                transaction = transactions.get(transactionID);
                // Display the transaction if the month matches the transaction date and it is a sale, display it and increase count
                if (transaction.getTransactionType().equals("BUY")) {
                    if (transaction.getDate().get(Calendar.MONTH) == month) {
                        // Display the transaction
                        System.out.println(transaction.display());
                        // Increase the number of monthly sales
                        monthlySales++;
                    }
                }
            }
            // If there are no sales print special message to use.
            if (monthlySales == 0) {
                System.out.println("There have been no sales yet for " + MONTHS[month] + ".\n");
            } else {
                // Pring Empty line for padding/aesthetics.
                System.out.println();
            }
        } else {
            System.out.println("No transactions have been made yet.\n");
        }
    }
}