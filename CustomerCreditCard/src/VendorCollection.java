import java.util.HashMap;
import java.util.NoSuchElementException;

/** VendorCollection defines the data structures to hold collections of Vendor objects.
 * @author Mike G. Abood
 * CPE 365 Winter 17
 */

public class VendorCollection {
    private HashMap<Integer, Vendor> vendorIdMap;

    /**VendorCollection constructor
     * initializes data structure for collection
     */
    public VendorCollection() {
        vendorIdMap = new HashMap<>();
    }

    /** addVendor
     *
     * @param vendor object to add to collection
     * @return Vendor object added
     */
    public Vendor addVendor(Vendor vendor) {
        vendorIdMap.put(vendor.getVendorId(), vendor);
        return vendor;
    }

    /** removeVendor
     *
     * @param vendor object to remove from collection
     * @return Vendor object removed
     */
    public Vendor removeVendor(Vendor vendor) {
        if (vendorIdMap.containsValue(vendor)) {
            vendorIdMap.remove(vendor.getVendorId(), vendor);
        }
        else {
            throw new NoSuchElementException("Vendor not found by Id");
        }
        return vendor;
    }

    /** findVendorById used to locate Vendor object
     *
     * @param id integer ID of Vendor
     * @return Vendor object located
     */
    public Vendor findVendorById(int id) {
        if (vendorIdMap.containsKey(id)) {
            return vendorIdMap.get(id);
        }
        else {
            throw new NoSuchElementException("Vendor not found by Id");
        }
    }
}
