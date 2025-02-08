import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private ArrayList<BankAccount> activeAccounts = new ArrayList<BankAccount>();
    private ArrayList<Loan> userOutstandingLoans = new ArrayList<Loan>();

    //User's information
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String physicalAddress;
    private String mailingAddress;
    private String SSN;
    private String phoneNumber;
    private String email;
    private String occupation;

    //User's login information
    private int userID;
    private String userName;
    private String password;

    //User's Financial Information
    private int creditScore;
    private int annualIncome;
    private Loan userLoan;
    private BankAccount account;

    //------------------------
    // CAN ADD SOME MORE INFORMATION REGARDING USER'S PREFERENCE FOR RECEIVING NOTIFICATIONS, i.e. email, sms, etc.
    //------------------------

    public User (String firstName, String lastName, String dateOfBirth, String gender, String physicalAddress, String mailingAddress, String SSN, String phoneNumber, String email, String occupation, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.physicalAddress = physicalAddress;
        this.mailingAddress = mailingAddress;
        this.SSN = SSN;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.userName = userName;
        this.password = password;

    }

    public String getUsername() {
        return userName;
    }

    public boolean matchLogin (String username, String password) {
        return username.equals(this.firstName) && password.equals(this.password);
    }

}
