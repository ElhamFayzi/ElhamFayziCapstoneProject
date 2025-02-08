public class Transaction {

    private int transactionID;
    private String transactionType;
    private double amount;
    private String date;

    private int senderAccountNumber;
    private int recipientAccountNumber;
    private Boolean transactionStatus;          // Used the wrapper class Boolean because there would be 3 types of status, pending (null), completed (true), or failed (false)
    private String transactionDescription;
}
