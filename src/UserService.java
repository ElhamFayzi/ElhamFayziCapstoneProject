import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class UserService {
    private static ArrayList<User> listOfUsers = new ArrayList<User>();
    private static boolean usersLoaded = false;

    public static boolean loadUsers() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("PersonalBankingUsers.txt");
        } catch (FileNotFoundException e) {
            return false;
        }

        Scanner reader = new Scanner(fis);
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(", ");
            User existingUser = new User(line);
            listOfUsers.add(existingUser);
        }
        reader.close();

        return !listOfUsers.isEmpty();
    }

    public static boolean registerNewUser (Scanner scnr) {               // Can move this method to User class for better encapsulation
        String[] fields = {"First Name", "Last Name", "Date of Birth (MM/DD/YYYY)", "Gender", "Physical Address", "Mailing Address", "SSN", "Phone Number", "Email Address", "Occupation", "Username", "Password"};
        String[] responses = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            System.out.print(fields[i] + ": ");
            responses[i] = scnr.nextLine();                             // FIX THIS: Add an algorithm to check if the username has already been taken
        }

        User newUser = new User(responses);
        listOfUsers.add(newUser);

        return writeUserToFile(responses);
    }

    public static User handleLogin (Scanner scnr) throws Exception{
        if (!usersLoaded) {                                 // Load the users if they have not yet been loaded
            usersLoaded = loadUsers();
            if (usersLoaded == false) {                     // If the program could not load the list of existing users, it would throw an exception to the method call
                throw new Exception ("The program cannot access the list of existing users at this time. Please try again!");
            }
        }

        System.out.println("------------------------------------");
        System.out.print("Enter your username: ");
        String username = scnr.nextLine();
        System.out.print("Enter your password: ");
        String password = scnr.nextLine();

        User user = searchUser(username);

        if (user == null) {
            System.out.println("Wrong username or password!");
        }
        else if (user.matchLogin(username, password)) {
            return user;                                      // Should break out of the loop if the login information was correct; it would be infinite loop otherwise
        }
        else {
            System.out.println("Wrong username or password!");
        }

        return handleLogin(scnr);
    }

    private static boolean writeUserToFile (String[] responses) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("PersonalBankingUsers.txt", true);
        } catch (FileNotFoundException e) {
            System.out.println("The program cannot register new users at this time. Please try again later!");
            return false;
        }
        PrintWriter writer = new PrintWriter(fos);                      // Can move this to User class
        for (String response: responses) {
            writer.print(response + ", ");
        }
        writer.println();
        writer.flush();
//        writer.close();                       // No need to close the writer explicitly since it would go out of the scope of the method

        return true;
    }

    public static User searchUser (String username) {
        for (User user: listOfUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public static void postLogin (Scanner scnr, User user) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("========= Personal Banking Menu =========\n" +
                    "1. View Account Balance\n" +
                    "2. Deposit Funds\n" +
                    "3. Withdraw Funds\n" +
                    "4. Fill a loan application for your business\n" +
                    "5. Logout\n" +
                    "-----------------------------------------");

            int choice = scnr.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Total Balance as of " + LocalDate.now() + " : ");
                    System.out.printf(" $ %.2f\n", user.getBalance());
                    break;

                case 2:
                    System.out.print("How much do you want to deposit? $");
                    double amountToDeposit = scnr.nextDouble();
                    user.deposit(amountToDeposit);
                    break;

                case 3:
                    System.out.print("How much do you want to withdraw? $");
                    double amountToWithdraw = scnr.nextDouble();
                    try {
                        user.withdraw(amountToWithdraw);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Redirecting...");
                    loanApplication(scnr, user);
                    break;

                case 5:
                    System.out.println("Logging out ...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;

            }
        }
    }
    public static void loanApplication(Scanner scnr, User user) {
        System.out.println("=== Business Loan Application ===");

        scnr.nextLine();
        System.out.print("Enter Business Name: ");
        String businessName = scnr.nextLine();

        System.out.print("Enter Business Type: ");
        String businessType = scnr.nextLine();

        double loanAmount;
        System.out.print("Enter Loan Amount Requested (in USD): ");
        try {
            loanAmount = Double.parseDouble(scnr.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid loan amount entered. Application aborted.");
            return;
        }

        System.out.print("Loan Purpose: ");
        String loanPurpose = scnr.nextLine();

        int yearsInOperation;
        System.out.print("Years in Operation (for businesses only): ");
        try {
            yearsInOperation = Integer.parseInt(scnr.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for years in operation. Application aborted.");
            return;
        }

        double annualRevenue, netProfit, avgMonthlySales;
        try {
            System.out.print("Annual Revenue (for businesses only): ");
            annualRevenue = Double.parseDouble(scnr.nextLine());

            System.out.print("Net Profit Last Year (for businesses only): ");
            netProfit = Double.parseDouble(scnr.nextLine());

            System.out.print("Average Monthly Sales (for businesses only): ");
            avgMonthlySales = Double.parseDouble(scnr.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid performance data. Application aborted.");
            return;
        }

        LocalDate currentDate = LocalDate.now();

        try {
            FileOutputStream fis = new FileOutputStream("loan_applications.csv", true);
            PrintWriter writer = new PrintWriter(fis);
            writer.println(user.getName() + "," +
                    businessName + "," +
                    businessType + "," +
                    loanAmount + "," +
                    loanPurpose + "," +
                    currentDate + "," +
                    yearsInOperation + "," +
                    annualRevenue + "," +
                    netProfit + "," +
                    avgMonthlySales);
            writer.flush();

            Loan req = new Loan(user.getName(), businessName, businessType, loanAmount, loanPurpose, currentDate, yearsInOperation, annualRevenue, netProfit, avgMonthlySales);

            System.out.println("Loan application submitted successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to submit the application");
        }
    }
}