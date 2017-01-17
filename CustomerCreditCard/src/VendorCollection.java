import java.util.HashMap;
import java.util.NoSuchElementException;

/** VendorCollection defines the data structures to hold collections of Vendor objects.
 * @Author Mike G. Abood
 * CPE 365 Winter 17
 */

public class VendorCollection {
    private HashMap<Integer, Vendor> vendorIdMap;

    public VendorCollection() {
        vendorIdMap = new HashMap<Integer, Vendor>();
    }

    public Vendor addVendor(Vendor vendor) {
        vendorIdMap.put(vendor.getVendorId(), vendor);
        return vendor;
    }

    public Vendor removeVendor(Vendor vendor) {
        if (vendorIdMap.containsValue(vendor)) {
            vendorIdMap.remove(vendor.getVendorId(), vendor);
        }
        else {
            throw new NoSuchElementException("Vendor not found by Id");
        }
        return vendor;
    }

    public Vendor findVendorById(int id) {
        if (vendorIdMap.containsKey(id)) {
            return vendorIdMap.get(id);
        }
        else {
            throw new NoSuchElementException("Vendor not found by Id");
        }
    }
}
