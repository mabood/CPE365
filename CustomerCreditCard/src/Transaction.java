import java.math.BigDecimal;
import java.util.Date;

/** Transaction
 * @author Mike G. Abood
 * CPE 365 Winter 17
 *
 * Attributes: date, customer id, cc number, and vendor id.
 * Contains information that a customer purchased something with a credit card.
 * Example: On January 5th, 2015, customer with id 255 used CC with credit card number 2342343432 at vendor id 233
 * for $654.23 worth of purchases.
 */

public class Transaction implements Comparable<Transaction>{
    private Date transactTime;
    private Customer customer;
    private CreditCard card;
    private Vendor vendor;
    private BigDecimal amount;

    /** Transaction constructor
     *
     * @param customer Customer object whom this Transaction belongs to
     * @param card Credit card used for this Transaction
     * @param vendor Vendor at which this Transaction was performed
     * @param value Numeric value of this Transaction
     */
    public Transaction(Customer customer, CreditCard card, Vendor vendor, BigDecimal value) {
        this.transactTime = new Date();
        this.customer = customer;
        this.card = card;
        this.vendor = vendor;
        this.amount = value;
    }

    /** getTransactionTime return Date object created for this Transaction
     *
     * @return Date object transaction time
     */
    public Date getTransactionTime() {
        return transactTime;
    }

    /** compareTo compares Transaction Date objects
     *
     * @param o Transaction to compare to
     * @return integer representing result of comparison
     */
    @Override
    public int compareTo(Transaction o) {
        return this.transactTime.compareTo(o.getTransactionTime());
    }

    /** getCustomer returns Customer object for this Transaction
     *
     * @return Customer object
     */
    public Customer getCustomer() {
        return customer;
    }

    /** getCard returns CreditCard used for this Transaction
     *
     * @return CreditCard object
     */
    public CreditCard getCard() {
        return card;
    }

    /** getVendor returns Vendor for this Transaction
     *
     * @return Vendor object
     */
    public Vendor getVendor() {
        return vendor;
    }

    /** getValue returns the dollar value for Transaction
     *
     * @return BigDecimal dollar value
     */
    public BigDecimal getTransactionValue() {
        return amount;
    }
}
