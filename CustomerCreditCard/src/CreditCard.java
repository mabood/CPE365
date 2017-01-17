import java.math.*;
import java.util.*;

/** CreditCard class defines the attributes and use of CreditCard objects
 * @author Mike G. Abood
 * CPE 365 Winter 17
 *
 * Attributes: number, type (enum value, can be Visa, MC, American Express, or Discover), credit limit, current balance
 * (must be less than the credit limit).
 * For example, credit card with number 2432143232 is Visa, credit limit of $10,000 and current balance of $3444.23.
 * The credit card number is unique. You cannot have two credit cards with the same number.
 * Add an additional bit that represents if the credit card is active. Credit cards are initially inactive.
 */

public class CreditCard {
    public enum CardType {
        AMEX, DISCOVER, MC, VISA;
        private static CardType[] allValues = values();
        public static CardType fromOrdinal(int n) {return allValues[n];}
    }

    private static HashSet<String> cardsCreated;
    private String cardNumber;
    private int creditLimit;
    private CardType type;
    private BigDecimal balance;
    private boolean activeStatus;
    private ArrayList<Customer> cardHolders;
    private TreeMap<Date, Transaction> transactions;
    private TreeMap<Date, Payment> payments;

    /** CreditCard constructor generates card number and assigns limit.
     * Initializes collections for payments and transactions
     *
     * @param cardType enum for CreditCard type
     * @param creditLimit integer for CreditCard limit
     */
    public CreditCard(CardType cardType, int creditLimit) {
        cardsCreated = new HashSet<>();
        this.cardHolders = new ArrayList<>();
        this.type = cardType;
        this.creditLimit = creditLimit;
        this.cardNumber = createUniqueCard();
        this.activeStatus = false;
        this.balance = new BigDecimal(0);

        transactions = new TreeMap<>();
        payments = new TreeMap<>();
    }

    /** generateCardNumber generate random CreditCard number
     *
     * @return String for 16-digit card number
     */
    private String generateCardNumber() {
        //generate random 4-digit sequence four times
        String cNum = "";
        for (int i = 1; i <= 16; i++) {
            cNum += (int)(Math.random() * 10);
            if (i % 4 == 0 && i < 16) {
                cNum += " ";
            }
        }
        return cNum;
    }

    /** createUniqueCard Create a unique credit card by testing randomly generated
     *  card number for uniqueness and storing new cards in a HashSet
     *
     * @return a unique String 16-digit number
     */
    private String createUniqueCard() {
        String gen = generateCardNumber();
        while(cardsCreated.contains(gen)) {
            gen = generateCardNumber();
        }
        cardsCreated.add(gen);
        return gen;
    }

    /** addCardHolder add a Customer to this card
     *
     * @param customer return the customer object added
     */
    public void addCardHolder(Customer customer) {
        if (!this.cardHolders.contains(customer)) {
            this.cardHolders.add(customer);
            customer.addCustomerCard(this);
        }
    }

    /** removeCardHolder remove a Customer from this card if it exists
     *
     * @param customer Customer object to remove
     * @return Customer object removed or null if NoSuchElement
     */
    public Customer removeCardHolder(Customer customer) {
        if (this.cardHolders.contains(customer)) {
            this.cardHolders.remove(customer);
            if (cardHolders.isEmpty()) {
                this.changeCardActiveStatus(false);
            }
            customer.cancelCustomerCard(this);
            return customer;
        }
        else {
            return null;
        }
    }

    /** getCardHolders return all Customers on this card
     *
     * @return ArrayList of card holding Cusotmers
     */
    public ArrayList<Customer> getCardHolders() {
        return this.cardHolders;
    }

    /** getCardNumber get credit card number
     *
     * @return String for CreditCard number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /** getCreditLimit get credit limit on CreditCard
     *
     * @return int creditLimit
     */
    public int getCreditLimit() {
        return creditLimit;
    }

    /** changeCreditLimit change the creditLimit
     *
     * @param newLimit new limit for CreditCard
     * @return int creditLimit after call to changeCreditLimit
     */
    public int changeCreditLimit(int newLimit) {
        if (newLimit > 0) {
            creditLimit = newLimit;
        }
        return creditLimit;
    }

    /** getCardType get the type for this card
     *
     * @return enum CardType
     */
    public CardType getCardType() {
        return type;
    }

    /** getBalance get the balance on this card
     *
     * @return BigDecimal balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /** getCardStatus get active status of this card
     *
     * @return boolean status active (true) or inactive (false)
     */
    public boolean getCardActiveStatus() {
        return activeStatus;
    }

    /** changeCardActiveStatus change the active status of this card
     *
     * @param newStatus new active status for card
     * @return boolean status after call to changeCardActiveStatus
     */
    public boolean changeCardActiveStatus(boolean newStatus) {
        activeStatus = newStatus;
        return activeStatus;
    }

    /** createTransaction makes the transaction if the customer is valid and card is active
     *
     * @param customer customer who makes this transaction. Must be a cardholder
     * @param vendor vendor who is receiving this transaction
     * @param value the dollar value of this transaction
     * @return the Transaction object
     * @throws CreditCardException if Customer is not a card holder or card is not active
     */
    public Transaction createTransaction(Customer customer, Vendor vendor, BigDecimal value) throws CreditCardException {
        if (!this.cardHolders.contains(customer)) {
            throw new CreditCardException("Customer is not a card holder of this card.");
        }
        if (!this.activeStatus) {
            throw new CreditCardException("Credit card is not active");
        }
        if (this.balance.add(value).compareTo(new BigDecimal(creditLimit)) > 0) {
            throw new CreditCardException("Transaction value exceeds credit limit.");
        }

        Transaction transaction = new Transaction(customer, this, vendor, value);
        this.balance = this.balance.add(transaction.getTransactionValue());
        transactions.put(transaction.getTransactionTime(), transaction);
        return transaction;
    }

    /** createPayment makes a payment if the customer is valid and card is active
     *
     * @param customer customer making the payment. must be a card holder
     * @param value the value of this card payment
     * @return the Payment object
     * @throws CreditCardException if Customer is not a card holder or card is not active
     */
    public Payment createPayment(Customer customer, BigDecimal value) throws CreditCardException {
        if (!this.cardHolders.contains(customer)) {
            throw new CreditCardException("Customer is not a card holder of this card.");
        }
        if (!this.activeStatus) {
            throw new CreditCardException("Credit card is not active");
        }

        Payment payment = new Payment(value, this, customer);
        this.balance = this.balance.subtract(payment.getPaymentValue());
        payments.put(payment.getPaymentTime(), payment);
        return payment;
    }

    /** getPaymentsOnDateRange returns a subMap of payments made within specified date range
     *
     * @param start lower bound date
     * @param end upper bound date
     * @return a SortedMap of matching Payment entries, or null if invalid arguments
     */
    public SortedMap<Date, Payment> getPaymentsOnDateRange(Date start, Date end) {
        return payments.subMap(start, end);
    }

    /** getTransactionsOnDateRange returns a subMap of transactions made within specified date range
     *
     * @param start lower bound date
     * @param end upper bound date
     * @return a SortedMap of matching Transcation entries, or null if invalid arguments
     */
    public SortedMap<Date, Transaction> getTransactionsOnDateRange(Date start, Date end) {
        return transactions.subMap(start, end);
    }

    /** cardTypeToString convert enum to String
     *
     * @param type CardType enum
     * @return String for card type
     */
    public static String cardTypeToString(CardType type) {
        String toStr = "Unidentified";
        switch(type) {
            case AMEX:
                toStr = "American Express";
                break;
            case MC:
                toStr = "Mastercard";
                break;
            case VISA:
                toStr = "Visa";
                break;
            case DISCOVER:
                toStr = "Discover";
                break;
        }
        return toStr;
    }

    /** printableCardInfo combines all relevant card info into printable String
     *
     * @return String card info summary
     */
    public String printableCardInfo() {
        //(i.e., credit card number, credit limit, and balance for each credit card)
        String info = "Card Number:\t" + this.cardNumber + "\n";
        info += "Card Type:\t\t" + cardTypeToString(this.type) + "\n";
        info += "Card Holder(s):\t";
        for (Customer customer: getCardHolders()) {
            info += customer.getName();
            info += ", ";
        }
        info = info.substring(0, info.length() - 2);
        info+= "\nBalance:\t\t$" + balance.setScale(2, BigDecimal.ROUND_CEILING);
        return info;
    }
}
