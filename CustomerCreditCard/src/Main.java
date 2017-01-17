import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * @Author Mike G. Abood
 * CPE 365 Winter 17
 */

/*

Your program should support the following updates. Identify object by their IDs.
1.	Create a new customer.
2.	Create a new credit card for an existing customer (will affect both the credit card and ownership data). Initially, the credit card will not be active.
3.	Issue a credit card duplicate for additional customer (will affect only the ownership data)
4.	Cancel a credit card.
5.	Active a credit card.
6.	Add a new vendor.
7.	Create a new transaction. This will affect the balance of the credit card.
8.	Allow a customer to pay off credit card. This will affect both the payment data and credit card balance.

Your program should support the following queries.
1.	Locate customer by ID or SSN.
2.	For a given customer (specified ID or SSN), print credit card information (i.e., credit card number, credit limit, and balance for each credit card).
3.	For a given credit card (specified by CC number), print credit card information (e.g., balance, credit limit, card holders).
4.	For a given credit card, print all transactions that are in a specified date range.

 */
public class Main {
    private static boolean exit;
    static Scanner scan = new Scanner(System.in);
    private static CustomerCollection customers = new CustomerCollection();
    private static CreditCardCollection cards = new CreditCardCollection();
    private static VendorCollection vendors = new VendorCollection();

    public static void main(String[] args) {
        exit = false;
        String input;

        while(!exit) {
            printMainMenu();
            input = scan.nextLine();
            input += " ";
            switch(input.charAt(0)) {
                case '1':
                    printNewCustomerMenu();
                    break;
                case '2':
                    printNewCreditCardMenu();
                    break;
                case '3':
                    printDuplicateCardMenu();
                    break;
                case '4':
                    printCancelCardMenu();
                    break;
                case '5':
                    printActivateCardMenu();
                    break;
                case '6':
                    printNewVendorMenu();
                    break;
                case '7':
                    printCreateTransactionMenu();
                    break;
                case '8':
                    printCreatePaymentMenu();
                    break;
                case '9':
                    printQueryMenu();
                    break;
                case 'x':
                    printExit();
                    break;
                case 'X':
                    printExit();
                    break;
                default:
                    System.out.println("Unrecognized input. enter a single integer.");
            }

        }

    }

    public static void printMainMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Main Menu\n";
        menu += "============================================================\n";
        menu += "[1]  Create a new customer\n";
        menu += "[2]  Create a new credit card for an existing customer\n";
        menu += "[3]  Issue a credit card duplicate for additional customer\n";
        menu += "[4]  Cancel a credit card\n";
        menu += "[5]  Activate a credit card\n";
        menu += "[6]  Add a new vendor\n";
        menu += "[7]  Create a new transaction\n";
        menu += "[8]  Make a payment for a customer to a credit card\n";
        menu += "[9]  Make a query\n";
        menu += "[X]  Exit\n";
        menu += "\nPlease enter the number of the above menu options: ";
        System.out.print(menu);
    }

    public static void printNewCustomerMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Create a Customer\n";
        menu += "============================================================\n";
        menu += "\n";
        System.out.print(menu);

        String input;
        String name;
        String address;
        long phone;
        long ssn;

        System.out.print("\nFirst name: ");
        input = scan.nextLine();
        name = input;
        System.out.print("\nLast Name: ");
        input = scan.nextLine();
        name += " " + input;

        while(true) {
            System.out.print("\nSSN: ");
            try {
                input = scan.nextLine();
                ssn = new Long(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter 10-digit SSN.");
            }
        }
        System.out.print("\nAddress: ");
        input = scan.nextLine();
        address = input;

        while(true) {
            System.out.print("\nPhone: ");
            try {
                input = scan.nextLine();
                phone = new Long(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter numeric phone number.");
            }
        }

        Customer customer = new Customer(name, ssn, address, phone);
        customers.addCustomer(customer);

        String result = "\nCustomer Created.\n";
        result += "\n____________________________________________________________\n";
        result += customer.printableCustomerInfo();
        result += "\n____________________________________________________________\n";
        System.out.println(result);
    }

    public static void printNewCreditCardMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Create a new Credit Card\n";
        menu += "============================================================\n";
        menu += "\n";
        System.out.print(menu);

        String input;
        int typeInt;
        int creditLimit;
        CreditCard.CardType type;
        long ssn = 0;
        Customer customer;

        menu = "\nCard Type:\n[1]  American Express\n[2]  Discover\n[3]  Mastercard\n";
        menu += "[4]  Visa\n";
        System.out.print(menu);

        while(true) {
            System.out.print("\nEnter the number for one of the above cards: ");
            try {
                input = scan.nextLine();
                typeInt = new Integer(input);
                if (typeInt > 0 && typeInt < 5) {
                    break;
                }
                else {
                    System.out.println(typeInt + " is out of range.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }

        type = CreditCard.CardType.fromOrdinal(typeInt - 1);
        System.out.println(CreditCard.cardTypeToString(type) + " selected.");

        customer = locateCustomerBySSN("Enter Customer SSN for which to assign this card.");
        System.out.println("Customer: " + customer.getName() + " selected.");

        while(true) {
            System.out.print("\nCredit Limit: ");
            try {
                input = scan.nextLine();
                creditLimit = new Integer(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter integer credit limit.");
            }
        }

        CreditCard card = new CreditCard(type, creditLimit);
        card.addCardHolder(customer);
        cards.addCreditCard(card);

        String result = "\nCredit card created:";
        result += "\n____________________________________________________________\n";
        result += card.printableCardInfo();
        result += "\n____________________________________________________________\n";
        System.out.println(result);
    }

    public static void printDuplicateCardMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Issue a Duplicate Credit Card\n";
        menu += "============================================================\n";
        System.out.print(menu);

        String cardNumber;
        CreditCard card;
        Customer customer;
        String input;

        System.out.println("Add a customer to an existing card.");

        card = locateCreditCardByNumber("Enter the 16-digit card number to duplicate: ");

        customer = locateCustomerBySSN("Enter Customer SSN for which to assign this card.");
        System.out.println("Customer: " + customer.getName() + " selected.");

        card.addCardHolder(customer);
        String result = "\nNew credit card information:";
        result += "\n____________________________________________________________\n";
        result += card.printableCardInfo();
        result += "\n____________________________________________________________\n";
        System.out.println(result);


    }

    public static void printCancelCardMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Cancel a Credit Card\n";
        menu += "============================================================\n";
        System.out.print(menu);

        String input;
        CreditCard card;

        card = locateCreditCardByNumber("Enter the 16-digit card number to CANCEL: ");
        card.changeCardActiveStatus(false);
        System.out.println("Card: " + card.getCardNumber() + " CANCELED.");
    }

    public static void printActivateCardMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Activate a Credit Card\n";
        menu += "============================================================\n";
        System.out.print(menu);

        String input;
        CreditCard card;

        card = locateCreditCardByNumber("Enter the 16-digit card number to activate: ");
        card.changeCardActiveStatus(true);
        System.out.println("Card: " + card.getCardNumber() + " ACTIVATED.");
    }

    public static void printNewVendorMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Add a New Vendor\n";
        menu += "============================================================\n";
        menu += "\n";
        System.out.print(menu);

        String input;
        String name;
        String address;

        System.out.print("\nVendor name: ");
        input = scan.nextLine();
        name = input;

        System.out.print("\nVendor address: ");
        input = scan.nextLine();
        address = input;

        Vendor vendor = new Vendor(name, address);
        vendors.addVendor(vendor);

        String result = "\nVendor Created:";
        result += "\n____________________________________________________________\n";
        result += vendor.printableVendorInfo();
        result += "\n____________________________________________________________\n";
        System.out.println(result);
    }

    public static void printCreateTransactionMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Create a New Transaction\n";
        menu += "============================================================\n";
        menu += "\n";
        System.out.print(menu);

        String input;
        int id = -1;
        CreditCard card;
        Vendor vendor;
        Customer customer;
        BigDecimal value;

        customer = locateCustomerBySSN("Enter customer SSN for this transaction: ");
        System.out.println("Customer: " + customer.getName() + " selected.");

        card = locateCreditCardByNumber("Enter the 16-digit card number to use in Transaction: ");

        while(true) {
            System.out.print("\nEnter vendor ID: ");
            try {
                input = scan.nextLine();
                id = new Integer(input);
                vendor = vendors.findVendorById(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter integer credit limit.");
            } catch (NoSuchElementException ne) {
                System.out.println("Vendor with ID: " + id + "not found.");
            }
        }
        System.out.println("Vendor identified as: " + vendor.getName());

        while(true) {
            System.out.print("\nValue of this transaction: ");
            try {
                input = scan.nextLine();
                value = new BigDecimal(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter decimal dollar amount.");
            }
        }

        try {
            card.createTransaction(customer, vendor, value);
            String info = "$" + card.getBalance().setScale(2, BigDecimal.ROUND_CEILING);
            System.out.println("Transaction completed. New card balance: " + info);
        }
        catch (CreditCardException ce) {
            System.out.println("Card declined. Reason for decline:");
            System.out.println(ce.getMessage());
        }

    }

    public static void printCreatePaymentMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Make a Credit Card Payment\n";
        menu += "============================================================\n";
        System.out.print(menu);

        String input;
        long ssn = 0;
        CreditCard card;
        Customer customer;
        BigDecimal value;

        customer = locateCustomerBySSN("Enter customer SSN for this payment:");
        System.out.println("Customer: " + customer.getName() + " selected.");

        card = locateCreditCardByNumber("Enter the 16-digit card number to make payment to: ");

        String balance = "$" + card.getBalance().setScale(2, BigDecimal.ROUND_CEILING);
        System.out.println("Current balance on this card: " + balance);

        while(true) {
            System.out.print("\nEnter dollar amount for this payment: ");
            try {
                input = scan.nextLine();
                value = new BigDecimal(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter decimal dollar amount.");
            }
        }

        try {
            card.createPayment(customer, value);
            String info = "$" + card.getBalance().setScale(2, BigDecimal.ROUND_CEILING);
            System.out.println("Payment completed. New card balance: " + info);
        }
        catch (CreditCardException ce) {
            System.out.println("Card declined. Reason for decline:");
            System.out.println(ce.getMessage());
        }
    }

    public static void printQueryMenu() {
        String menu = "\n============================================================\n";
        menu += "Credit Bank 1.0 | Make a Query\n";
        menu += "============================================================\n";
        menu += "Available queries:\n";
        menu += "[1]  Locate customer by ID.\n";
        menu += "[2]  Locate customer by SSN.\n";
        menu += "[3]  print credit card information for a given customer ID.\n";
        menu += "[4]  print credit card information for a given customer SSN.\n";
        menu += "[5]  For a given credit card, print credit card information.\n";
        menu += "[6]  For a given credit card, print all transactions that \n" +
                "     are in a specified date range.\n";
        System.out.print(menu);

        String input = scan.nextLine();
        String result;
        Date start;
        Date end;
        SimpleDateFormat printFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        SimpleDateFormat scanFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Customer customer;
        CreditCard card;

        input += " ";
        switch(input.charAt(0)) {
            case '1':
                System.out.println("Locate customer by ID.");
                customer = locateCustomerById("Enter customer ID: ");
                result = "\nCustomer located:";
                result += "\n____________________________________________________________\n";
                result += customer.printableCustomerInfo();
                result += "\n____________________________________________________________\n";
                System.out.println(result);
                break;

            case '2':
                System.out.println("Locate customer by SSN.");
                customer = locateCustomerBySSN("Enter customer SSN: ");
                result = "\nCustomer located:";
                result += "\n____________________________________________________________\n";
                result += customer.printableCustomerInfo();
                result += "\n____________________________________________________________\n";
                System.out.println(result);
                break;

            case '3':
                System.out.println("print credit card information for a given customer ID.");
                customer = locateCustomerById("Enter customer ID: ");
                result = "\nCustomer located:";
                result += "\n____________________________________________________________\n";
                result += customer.printableCustomerInfo();
                for (CreditCard crd: customer.getCustomerCards()) {
                    result += "\n\n" + crd.printableCardInfo();
                }

                result += "\n____________________________________________________________\n";

                System.out.println(result);
                break;

            case '4':
                System.out.println("print credit card information for a given customer SSN.");
                customer = locateCustomerBySSN("Enter customer SSN: ");
                result = "\nCustomer located:";
                result += "\n____________________________________________________________\n";
                result += customer.printableCustomerInfo();
                for (CreditCard crd: customer.getCustomerCards()) {
                    result += "\n\n" + crd.printableCardInfo();
                }
                result += "\n____________________________________________________________\n";

                System.out.println(result);
                break;

            case '5':
                System.out.println("For a given credit card, print credit card information.");
                card = locateCreditCardByNumber("Enter the 16-digit card number to look up: ");
                result = "\n____________________________________________________________\n";
                result += card.printableCardInfo();
                result += "\n____________________________________________________________\n";
                System.out.println(result);
                break;

            case '6':
                System.out.println("For a given credit card, print all transactions that\n" +
                        "are in a specified date range.");
                card = locateCreditCardByNumber("Enter the 16-digit card number to look up: ");

                while(true) {
                    System.out.println("\nEnter transaction start date (mm/dd/yyyy HH:mm:ss)");
                    try {
                        input = scan.nextLine();
                        start = scanFormat.parse(input);
                        break;
                    } catch (ParseException pe) {
                        System.out.println("\nInvalid input. Follow the date format (MM/DD/YYYY HH:mm:ss).");
                    }
                }
                System.out.println("Start date recognized: " +  printFormat.format(start));

                while(true) {
                    System.out.println("\nEnter transaction end date (mm/dd/yyyy HH:mm:ss)");
                    try {
                        input = scan.nextLine();
                        end = scanFormat.parse(input);
                        break;
                    } catch (ParseException pe) {
                        System.out.println("\nInvalid input. Follow the date format (MM/DD/YYYY HH:mm:ss).");
                    }
                }
                System.out.println("End date recognized: " +  printFormat.format(end));
                SortedMap<Date, Transaction> transQuery = card.getTransactionsOnDateRange(start, end);
                if (transQuery.isEmpty()) {
                    System.out.println("Query returned no transactions.");
                }
                else {
                    result = "\n____________________________________________________________";
                    while (!transQuery.isEmpty()) {
                        Date key = transQuery.firstKey();
                        Transaction trans = transQuery.get(key);
                        result += "\n";
                        result += "Transaction date: " + printFormat.format(key) + "\n";
                        result += "Card Number:\t" + trans.getCard().getCardNumber() + "\n";
                        result += "Customer:\t\t" + trans.getCustomer().getName() + "\n";
                        result += "Customer ID:\t" + trans.getCustomer().getCustomerId() + "\n";
                        result += "Vendor:\t\t\t" + trans.getVendor().getName() + "\n";
                        result += "Vendor ID:\t\t" + trans.getVendor().getVendorId() + "\n";
                        String amount = "$" + trans.getTransactionValue().setScale(2, BigDecimal.ROUND_CEILING);
                        result += "Transaction Amount: \t\t\t" + amount + "\n";
                        transQuery.remove(key);
                    }
                    result+= "____________________________________________________________\n";
                    System.out.println(result);
                }

                break;

            default:
                System.out.println("Unrecognized input. enter a single integer.");
        }

    }

    public static void printExit() {
        String menu = "\n============================================================\n";
        menu += "Exiting...\nThank you for banking with Credit Bank 1.0!\n";
        menu += "============================================================\n";
        System.out.print(menu);
        exit = true;
    }

    public static Customer locateCustomerById(String loopMessage) {
        String input;
        int id = -1;
        Customer customer;

        while(true) {
            System.out.print(loopMessage);
            try {
                input = scan.nextLine();
                id = new Integer(input);
                customer = customers.findCustomerById(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter valid customer ID");
            } catch (NoSuchElementException e) {
                System.out.println("\nNo Customer found with ID: " + id);
            }
        }
        return customer;
    }

    public static Customer locateCustomerBySSN(String loopMessage) {
        String input;
        long ssn = 0;
        Customer customer;

        while(true) {
            System.out.print("\n" + loopMessage);
            try {
                input = scan.nextLine();
                ssn = new Long(input);
                customer = customers.findCustomerBySSN(ssn);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Enter 10-digit SSN.");
            } catch (NoSuchElementException e) {
                System.out.println("\nNo Customer found with SSN: " + ssn);
            }
        }
        return customer;
    }

    public static CreditCard locateCreditCardByNumber(String loopMessage) {
        String input;
        CreditCard card;
        while(true) {
            System.out.println(loopMessage);
            try {
                input = scan.nextLine();
                card = cards.getCreditCardByNumber(input);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("\nCredit card not found.");
            }
        }
        System.out.println("Requested card identified.");
        return card;
    }
}
