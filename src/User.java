import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class User {
    private ArrayList<BankAccount> activeAccounts = new ArrayList<BankAccount>();
    private ArrayList<Loan> userOutstandingLoans = new ArrayList<Loan>();

    //User's information
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String SSN;

    private String gender;
    private String physicalAddress;
    private String mailingAddress;
    private String phoneNumber;
    private String email;
    private String occupation;

    //User's login information
    private final String userID;
    private String username;
    private String password;

    //User's Financial Information
    private int creditScore;
    private int annualIncome;
    private Loan userLoan;
    private BankAccount account;

    //------------------------
    // CAN ADD SOME MORE INFORMATION REGARDING USER'S PREFERENCE FOR RECEIVING NOTIFICATIONS, i.e. email, sms, etc.
    //------------------------

    public User (String[] data) {
        this.firstName = data[0];
        this.lastName = data[1];
        this.dateOfBirth = data[2];
        this.gender = data[3];
        this.physicalAddress = data[3];
        this.mailingAddress = data[5];
        this.SSN = data[6];
        this.phoneNumber = data[7];
        this.email = data[8];
        this.occupation = data[9];
        this.username = data[10];
        this.password = data[11];

        this.userID = generateUserID();
    }

    public void setPhysicalAddress (String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public void setMailingAddress (String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setOccupation (String occupation) {
        this.occupation = occupation;
    }

    public void setUsername (String userName) {
        this.username = userName;
    }

    public void setPassword (String password) {                         // Can improve this to get two arguments and change the password only if they both match
        this.password = password;                                       // No getter for password
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getUsername() {
        return username;
    }

    public boolean matchLogin (String username, String password) {
        return username.equals(this.username) && password.equals(this.password);
    }

    private String generateUserID () {
        return UUID.randomUUID().toString();
    }

}
