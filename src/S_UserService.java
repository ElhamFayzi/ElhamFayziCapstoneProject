import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class S_UserService {
    private static ArrayList<User> listOfUsers = new ArrayList<User>();
    private static boolean usersLoaded = false;

    private static ArrayList<Loan> requestedLoans = new ArrayList<Loan>();
    private static boolean loanRequestsLoaded = false;

    public static boolean loadUsers() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("../Directories/PersonalBankingUsers.txt");
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

    public static boolean registerNewUser (Scanner scnr) {
        String[] fields = {"First Name", "Last Name", "Date of Birth (MM/DD/YYYY)", "Gender", "Physical Address", "Mailing Address", "SSN", "Phone Number", "Email Address", "Occupation", "Username", "Password", "Initial Deposit"};
        String[] responses = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            if (i == fields.length - 1) {
                System.out.print(fields[i] + ": $");
            }
            else {
                System.out.print(fields[i] + ": ");
            }
            responses[i] = scnr.nextLine();
        }

        User newUser = new User(responses);
        listOfUsers.add(newUser);

        return writeUserToFile(responses);
    }

    public static User handleLogin (Scanner scnr) throws Exception{
        if (!usersLoaded) {                                 // Load the users if they have not yet been loaded
            usersLoaded = loadUsers();
            if (!usersLoaded) {                     // If the program could not load the list of existing users, it would throw an exception to the method call
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
            fos = new FileOutputStream("../Directories/PersonalBankingUsers.txt", true);
        } catch (FileNotFoundException e) {
            System.out.println("The program cannot register new users at this time. Please try again later!");
            return false;
        }
        PrintWriter writer = new PrintWriter(fos);
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
                    "5. Show previous loan applications\n" +
                    "6. Logout\n" +
                    "-----------------------------------------");

            int choice = scnr.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Total Balance as of " + LocalDate.now() + " : ");
                    System.out.printf(" $ %.2f\n", user.getBalance());
                    break;

                case 2:

                    double amountToDeposit = 0;
                    boolean invalidNumber = false;
                    do {
                        try {
                            System.out.print("How much do you want to deposit? $");

                            invalidNumber = false;
                            amountToDeposit = scnr.nextDouble();
                            user.deposit(amountToDeposit);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            invalidNumber = true;
                        }
                    } while (invalidNumber);

                    updateRecord();
                    break;

                case 3:
                    double amountToWithdraw = 0;
                    boolean invalidNum = false;
                    do {
                        try {
                            System.out.print("How much do you want to withdraw? $");

                            invalidNum = false;
                            amountToWithdraw = scnr.nextDouble();
                            user.withdraw(amountToWithdraw);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            invalidNum = true;
                        }
                    } while (invalidNum);

                    updateRecord();
                    break;

                case 4:
                    System.out.println("Redirecting...");
                    loanApplication(scnr, user);
                    break;

                case 5:
                    System.out.println("Loading Previous Loan Applications...");
                    boolean loaded = user.showLoanApplications();
                    if (!loaded) {
                        System.out.println("No previous loan applications found!");
                    }
                    break;

                case 6:
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
        Loan requestedLoan = new Loan(user.getName(), businessName, businessType, loanAmount, loanPurpose, currentDate, yearsInOperation, annualRevenue, netProfit, avgMonthlySales, "Pending", null);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("../Directories/loanApplications.csv", true);
            PrintWriter writer = new PrintWriter(fos);
            writer.println(user.getName() + "," +
                    businessName + "," +
                    businessType + "," +
                    loanAmount + "," +
                    loanPurpose + "," +
                    currentDate + "," +
                    yearsInOperation + "," +
                    annualRevenue + "," +
                    netProfit + "," +
                    avgMonthlySales + "," +
                    "Pending" + "," +
                    requestedLoan.getApplicationID());
            writer.flush();

            // To maintain a record of the loan applications of a business in individual files
            fos = new FileOutputStream("../Users/" + user.getName() + ".csv", true);
            writer = new PrintWriter(fos);

            writer.println(user.getName() + "," +
                    businessName + "," +
                    businessType + "," +
                    loanAmount + "," +
                    loanPurpose + "," +
                    currentDate + "," +
                    yearsInOperation + "," +
                    annualRevenue + "," +
                    netProfit + "," +
                    avgMonthlySales + "," +
                    "Pending" + "," +
                    requestedLoan.getApplicationID() + ",");
            writer.flush();

            System.out.println("Loan application submitted successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Failed to submit the application");
        }
    }

    public static boolean updateRecord() {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream("../Directories/PersonalBankingUsers.txt");
        } catch (FileNotFoundException e) {
            return false;
        }
        PrintWriter writer = new PrintWriter(fos);
        for (int i = 0; i < listOfUsers.size(); i++) {
            writer.println(listOfUsers.get(i).toString());
        }
        writer.flush();
        writer.close();
        return true;
    }
}