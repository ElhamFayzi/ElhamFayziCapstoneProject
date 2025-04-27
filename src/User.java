import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
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
    private String username;
    private String password;

    //User's Financial Information
    private double balance;

    public User (String[] data) {
        this.firstName = data[0];
        this.lastName = data[1];
        this.dateOfBirth = data[2];
        this.gender = data[3];
        this.physicalAddress = data[4];
        this.mailingAddress = data[5];
        this.SSN = data[6];
        this.phoneNumber = data[7];
        this.email = data[8];
        this.occupation = data[9];
        this.username = data[10];
        this.password = data[11];

        this.balance = Double.parseDouble(data[12]);
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

    public void setPassword (String password) {
        this.password = password;
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

    public double getBalance() { return this.balance; }

    public String getName() { return this.firstName + " " + this.lastName; }

    public String getDateOfBirth() { return this.dateOfBirth; }

    public String getSSN() { return this.SSN; }

    public void deposit(double amount) throws Exception{
        if (amount <= 0) {
            throw new Exception ("Invalid Amount");
        }
        else {
            this.balance += amount;
        }
    }

    public void withdraw(double amount) throws Exception {
        if (amount > this.balance || amount <= 0) {
            throw new Exception("Invalid Amount");
        }
        else {
            this.balance -= amount;
        }
    }

    public boolean matchLogin (String username, String password) {
        return username.equals(this.username) && password.equals(this.password);
    }

    public boolean showLoanApplications () {
        FileInputStream fis = null;
        Scanner reader = null;
        try {
            fis = new FileInputStream("../Users/" + this.getName() + ".csv");
            reader = new Scanner(fis);

            int i = 0;
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",");
                Loan l = new Loan (data);
                System.out.println("-----------------------------------");
                System.out.println("Application# " + ++i);
                System.out.println(l.toFormattedString());
                System.out.println("-----------------------------------");
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.firstName + ", " + this.lastName + ", " + this.dateOfBirth + ", " + this.gender + ", " + this.physicalAddress + ", "
                + this.mailingAddress + ", " + this.SSN + ", " + this.phoneNumber + ", " + this.email + ", " + this.occupation + ", "
                + this.username + ", " + this.password + ", " + this.balance + ", ";
    }

}
