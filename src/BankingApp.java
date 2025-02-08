import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.OutputStream;

public class BankingApp {
    public static ArrayList<User> listOfUsers = new ArrayList<User>();

    public static boolean registerNewUser (Scanner scnr) {
        System.out.print("First Name: ");
        String firstName = scnr.nextLine();

        System.out.print("Last Name: ");
        String lastName = scnr.nextLine();

        System.out.print("Date of Birth: ");
        String dateOfBirth = scnr.nextLine();

        System.out.print("Gender: ");
        String gender = scnr.nextLine();

        System.out.print("Physical Address: ");
        String physicalAddress = scnr.nextLine();

        System.out.print("Mailing Address: ");
        String mailingAddress = scnr.nextLine();

        System.out.print("SSN: ");
        String ssn = scnr.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scnr.nextLine();

        System.out.print("Email Address: ");
        String emailAddress = scnr.nextLine();

        System.out.print("Occupation: ");
        String occupation = scnr.nextLine();

        System.out.print("Username: ");                         // FIX THIS: Add an algorithm to check if the username has already been taken
        String username = scnr.nextLine();

        System.out.print("Password: ");
        String password = scnr.nextLine();

        User newUser = new User(firstName, lastName, dateOfBirth, gender, physicalAddress, mailingAddress, ssn, phoneNumber, emailAddress, occupation, username, password);
        listOfUsers.add(newUser);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("PersonalBankingUsers.txt", true);
        } catch (FileNotFoundException e) {
            System.out.println("The program cannot register new users at this time. Please try again later!");
            return false;
        }
        PrintWriter writer = new PrintWriter(fos);
        writer.println(listOfUsers.size() + "-->" + firstName + ", " + lastName + ", " + dateOfBirth + ", " + gender + ", " + physicalAddress + ", " + mailingAddress + ", " + ssn + ", " + phoneNumber + ", " + emailAddress + ", " + occupation + ", " + username + ", " + password);
        writer.flush();
        writer.close();

        return true;
    }


    public static void printPersonalBanking () {
        System.out.println("===========Banking System===========");
        System.out.println("1. Register a new User");
        System.out.println("2. Login existing users");
        System.out.println("3. Exit");
    }


    public static boolean openPersonalBanking(Scanner scnr) {
        boolean isRunning = true;
        
        while (isRunning) {
            printPersonalBanking();
            int choice = scnr.nextInt();
            scnr.nextLine();                            // Flush the newline character left in the previous line

            switch (choice){
                case 1:
                    boolean registered = registerNewUser(scnr);
                    break;
                case 2:
                    System.out.println("Not implemented yet");
                    break;
                case 3:
                    System.out.println("Exiting ...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }

        return true;
    }

    public static boolean openBusinessBanking (Scanner scnr) {
        System.out.println("TO BE IMPLEMENTED SOON");

        return true;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        boolean portalClosed = false;

        try {
            if (Integer.parseInt(args[0]) == 1) {
                System.out.println("Accessing Personal Banking");
                portalClosed = openPersonalBanking(scnr);

            }
            else if (Integer.parseInt(args[0]) == 2) {
                System.out.println("Accessing Business Banking");
                portalClosed = openBusinessBanking(scnr);
            }
            else {
                System.out.println("Invalid argument passed: Please pass '1' for Personal Banking or '2' for Business Banking.");
                System.exit(1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No arguments passed: Please pass '1' for Personal Banking or '2' for Business Banking.");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please pass '1' for Personal Banking or '2' for Business Banking.");
            System.exit(1);
        }
    }
}
