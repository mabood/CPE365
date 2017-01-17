/** Vendor
 * @author Mike G. Abood
 * CPE 365 Winter 17
 *
 * Attributes: id, name, location of main office.
 * Example: vendor with id 23 has name “Best Buy” and main office location “123 Main St., Austin, TX”.
 * The id of the vender is unique and assigned by the system.
 */

public class Vendor {
    private static int vendorIdCount = 1;
    private int vendorId;
    private String vendorName;
    private String address;

    /** Vendor Constructor - assigns unique id and info fields
     *
     * @param name name of vendor
     * @param address primary office address
     */
    public Vendor(String name, String address) {
        this.vendorName = name;
        this.address = address;
        this.vendorId = vendorIdCount;
        vendorIdCount++;
    }

    /** getVendorId returns the id of this vendor
     *
     * @return unique Id for this vendor
     */
    public int getVendorId() {
        return this.vendorId;
    }

    /** getName returns name of this vendor
     *
     * @return Vendor name as a String
     */
    public String getName() {
        return vendorName;
    }

    /** getAddress returns the address of this Vendor
     *
     * @return Vendor address as a String
     */
    public String getAddress() {
        return address;
    }

    /** printableVendorInfo returns relevant info as String
     *
     * @return String formatted summary of Vendor info
     */
    public String printableVendorInfo() {
        String info = "Vendor Id:\t" + vendorId;
        info += "\nName:\t\t" + vendorName;
        info += "\nAddress:\t" + address;
        return info;
    }
}
