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


}
