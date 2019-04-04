import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * CPS 209 Assignment 2
 * Car.java
 * @author Mohammad Ismail
 */
public class AccountingSystem {
    // TreeMap Containing the Transactions for the accounting System.
    private Map<Integer, Transaction> transactions;
    private static int TRANSACTION_ID_START = 1;
    private static int TRANSACTION_ID_MAX = 99;
    

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

    public void displayStatistics() {
        // All the statistics are grouped together to iterate through all once instead of separate iterations of O(n) each time.
        double totalSales = 0.0;
        Set<Integer> idSet = transactions.keySet();
        for (int id : idSet) {
            Transaction transaction = transactions.get(id);
            if (transaction.getTransactionType().equals(Transaction.Type.BUY)) {
                totalSales += transaction.getSalePrice();
            }
        }
        double averageSalesPerMonth = totalSales / 12;
    }
}