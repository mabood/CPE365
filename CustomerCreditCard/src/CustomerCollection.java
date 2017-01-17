import java.util.HashMap;
import java.util.NoSuchElementException;

/** CustomerCollection defines the data structures to hold collections of Customer objects.
 * @author Mike G. Abood
 * CPE 365 Winter 17
 */

public class CustomerCollection {
    private HashMap<Long, Customer> customerSSNMap;
    private HashMap<Integer, Customer> customerIdMap;

    /** CustomerCollection constructor
     * initializes both data structures for organizing customers
     */
    public CustomerCollection() {
        customerSSNMap = new HashMap<>();
        customerIdMap = new HashMap<>();
    }

    /** addCustomer add Customer to collection
     *
     * @param customer object to add
     * @return the customer object added
     */
    public Customer addCustomer(Customer customer) {
        customerSSNMap.put(customer.getSSN(), customer);
        customerIdMap.put(customer.getCustomerId(), customer);
        return customer;
    }

    /**removeCustomer removes customer from collection
     *
     * @param customer object to remove
     * @return customer if it can be located, else null
     */
    public Customer removeCustomer(Customer customer) {
        if (customerSSNMap.containsValue(customer)) {
            customerSSNMap.remove(customer.getSSN(), customer);
        }
        else {
            throw new NoSuchElementException("Customer not found by SSN");
        }
        if (customerIdMap.containsValue(customer)) {
            customerIdMap.remove(customer.getCustomerId(), customer);
        }
        else {
            throw new NoSuchElementException("Customer not found by Id");
        }
        return customer;
    }

    /**findCustomerBySSN used for queries
     *
     * @param ssn SSN for customer
     * @return Customer object located
     */
    public Customer findCustomerBySSN(long ssn) {
        if (customerSSNMap.containsKey(ssn)) {
            return customerSSNMap.get(ssn);
        }
        else {
            throw new NoSuchElementException("Customer not found by SSN");
        }
    }

    /**findCustomerById used for queries
     *
     * @param id integer for customer id
     * @return Customer object located
     */
    public Customer findCustomerById(int id) {
        if (customerIdMap.containsKey(id)) {
            return customerIdMap.get(id);
        }
        else {
            throw new NoSuchElementException("Customer not found by Id");
        }
    }
}
