import java.util.ArrayList;

public class BankAccount {
    ArrayList<Transaction> transactionsHistory = new ArrayList<Transaction>();

    //Account Details
    private String accountType;
    private int accountNumber;
    private int routingNumbers;
    private String accountCreationDate;

    // Financial Info
    private double accountFee;
    private double balance;

    public BankAccount () {
        // Create the Constructor
        // assign the accountCreationDate based on the time an instance of this class is created
    }

    public void setAccountType (String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType () {
        return this.accountType;
    }

}
