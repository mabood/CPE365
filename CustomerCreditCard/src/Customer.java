import java.util.ArrayList;

/**
 * @Author Mike G. Abood
 * CPE 365 Winter 17
 */

/*
: SSN, id, name, address, phone number.
Example: John Smith with SSN 2342342341 and id 45 lives on 123 Main Street and has phone number 234241423.
SSN is of course unique. The value of id is also unique and is automatically assigned by the system
(e.g., use a static variable that increments by one to assign it).



 */
public class Customer {
    static int idCounter = 1;
    private long ssn;
    private int id;
    private String name;
    private String address;
    private long phone;
    private ArrayList<CreditCard> customerCards;

    /** Customer constructor
     *
     * @param name String name for this Customer
     * @param ssn Social Security Number
     * @param address String address for this Customer
     * @param phone Customer's phone number
     */
    public Customer(String name, long ssn, String address, long phone) {
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.id = idCounter;
        idCounter++;
        this.customerCards = new ArrayList<CreditCard>();
    }

    /** getCustomerId return unique ID for Customer
     *
     * @return unique int for customerId
     */
    public int getCustomerId() {
        return id;
    }

    /** getSSN return Customer's social security number
     *
     * @return integer for social security
     */
    public long getSSN() {
        return ssn;
    }

    /** getName return Customer's Name
     *
     * @return String for name
     */
    public String getName() {
        return name;
    }

    /** getAddress return Address
     *
     * @return String for address
     */
    public String getAddress() {
        return address;
    }

    /** getPhoneNumber return phone number
     *
     * @return integer for phone number
     */
    public long getPhoneNumber() {
        return phone;
    }

    public void addCustomerCard(CreditCard card) {
        if (!this.customerCards.contains(card)) {
            this.customerCards.add(card);
        }
    }

    public boolean removeCustomerCard(CreditCard card) {
        if (this.customerCards.contains(card)) {
            return this.customerCards.remove(card);
        }
        return false;
    }

    public ArrayList<CreditCard> getCustomerCards() {
        return this.customerCards;
    }

    public String printableCustomerInfo() {
        String info = "Customer Id:\t" + id + "\nName:\t\t" + name + "\nSSN:\t\t"
                + ssn + "\nAddress:\t" + address + "\nPhone:\t\t" + phone;
        return info;
    }

}
