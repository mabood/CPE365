import java.util.HashMap;
import java.util.NoSuchElementException;

/** CustomerCollection defines the data structures to hold collections of Customer objects.
 * @Author Mike G. Abood
 * CPE 365 Winter 17
 */

public class CustomerCollection {
    private HashMap<Long, Customer> customerSSNMap;
    private HashMap<Integer, Customer> customerIdMap;

    public CustomerCollection() {
        customerSSNMap = new HashMap<Long, Customer>();
        customerIdMap = new HashMap<Integer, Customer>();
    }

    public Customer addCustomer(Customer customer) {
        customerSSNMap.put(customer.getSSN(), customer);
        customerIdMap.put(customer.getCustomerId(), customer);
        return customer;
    }

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

    public Customer findCustomerBySSN(long ssn) {
        if (customerSSNMap.containsKey(ssn)) {
            return customerSSNMap.get(ssn);
        }
        else {
            throw new NoSuchElementException("Customer not found by SSN");
        }
    }

    public Customer findCustomerById(int id) {
        if (customerIdMap.containsKey(id)) {
            return customerIdMap.get(id);
        }
        else {
            throw new NoSuchElementException("Customer not found by Id");
        }
    }
}
