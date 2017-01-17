import java.math.BigDecimal;
import java.util.Date;

/** Payment
 * @author Mike G. Abood
 * CPE 365 Winter 17
 *
 * Contains information that a payment was made on the balance of a credit card.
 * For example, on June 2, 2015, $400.34 was payed on credit card 23424123432.
 */

public class Payment implements Comparable<Payment>{
    private Date paymentTime;
    private BigDecimal amount;
    private CreditCard card;
    private Customer payee;

    /** Payment constructor
     *
     * @param value the dollar value for the payment
     * @param card the CreditCard payed
     * @param customer the Customer who made the payment
     */
    public Payment(BigDecimal value, CreditCard card, Customer customer) {
        this.paymentTime = new Date();
        this.amount = value;
        this.card = card;
        this.payee = customer;
    }

    /** getPaymentTime return Date object created for this payment
     *
     * @return Date object
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /** compareTo compares Date object for two Payments
     *
     * @param o Payment object to compare
     * @return integer result of comparison
     */
    @Override
    public int compareTo(Payment o) {
        return this.paymentTime.compareTo(o.getPaymentTime());
    }

    /** getPaymentValue return amount of Payment
     *
     * @return BigDecimal value of Payment
     */
    public BigDecimal getPaymentValue() {
        return amount;
    }

    /** getCard get Card payed with this Payment
     *
     * @return CreditCard object
     */
    public CreditCard getCard(){
        return card;
    }

    /** getCustomer return Customer who made the Payment
     *
     * @return Customer object
     */
    public Customer getCustomer() {
        return payee;
    }

}
