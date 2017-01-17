import java.util.HashMap;
import java.util.NoSuchElementException;

/** CreditCardCollection class for data structure holding CreditCard objects
 * @Author Mike G. Abood
 * CPE 365 Winter 17
 */
public class CreditCardCollection {
    private HashMap<String, CreditCard> creditCards;

    /** CreditCardCollection constructor
     *  initializes creditCards collection
     */
    public CreditCardCollection() {
        creditCards = new HashMap<String, CreditCard>();
    }

    /** addCreditCard add CreditCard object to collection
     *
     * @param card CreditCard to add
     */
    public void addCreditCard(CreditCard card) {
        creditCards.put(card.getCardNumber(), card);
    }

    /** removeCreditCardByNumber remove a CreditCard from collection
     *
     * @param cardNumber card number key to remove
     * @return CreditCard object removed or null if CreditCard doesn't exist
     */
    public CreditCard removeCreditCardByNumber(String cardNumber) {
        CreditCard card = getCreditCardByNumber(cardNumber);
        if (card != null) {
            creditCards.remove(cardNumber, card);
        }
        return card;
    }

    /** getCreditCardByNumber lookup CreditCard object by card number
     *
     * @param cardNumber String card number
     * @return the CreditCard object or null if it doesn't exist
     */
    public CreditCard getCreditCardByNumber(String cardNumber) {
        if (creditCards.containsKey(cardNumber)) {
            return creditCards.get(cardNumber);
        }
        else {
            throw new NoSuchElementException("Card not found.");
        }
    }
}
