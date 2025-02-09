import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApp {
    public static ArrayList<User> listOfUsers = new ArrayList<User>();

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
            User existingUser = new User(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8], line[9], line[10], line[11]);
            listOfUsers.add(existingUser);
        }
        reader.close();

        return !listOfUsers.isEmpty();
    }

    public static boolean registerNewUser (Scanner scnr) {
        String[] fields = {"First Name", "Last Name", "Date of Birth (MM/DD/YYYY)", "Gender", "Physical Address", "Mailing Address", "SSN", "Phone Number", "Email Address", "Occupation", "Username", "Password"};
        String[] responses = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            System.out.print(fields[i] + ": ");
            responses[i] = scnr.nextLine();                             // FIX THIS: Add an algorithm to check if the username has already been taken
        }

        User newUser = new User(responses[0], responses[1], responses[2], responses[3], responses[4], responses[5], responses[6], responses[7], responses[8], responses[9], responses[10], responses[11]);
        listOfUsers.add(newUser);

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
        writer.close();

        return true;
    }


    public static void printPersonalBanking () {
        System.out.println("===========Banking System===========");
        System.out.println("1. Register a new User");
        System.out.println("2. Login existing users");
        System.out.println("3. Exit");
        System.out.println("------------------------------------");
    }

    public static User searchUser (String username) {
        for (User user: listOfUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public static boolean openPersonalBanking(Scanner scnr) {
        boolean isRunning = true;

        while (isRunning) {
            printPersonalBanking();
            try {
                int choice = scnr.nextInt();
                scnr.nextLine();            // Flush the newline character left in the previous line

                switch (choice){
                    case 1:
                        boolean registered = registerNewUser(scnr);
                        if (!registered) {
                            System.out.println("The program could not register you at this time. Please try again later!");
                        }
                        break;
                    case 2:
                        boolean usersLoaded = loadUsers();
                        if (usersLoaded) {
                            while (true) {
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
                                    System.out.println("You have successfully logged in!");
                                    break;                                      // Should break out of the loop if the login information was correct; it would be infinite loop otherwise
                                }
                                else {
                                    System.out.println("Wrong username or password!");
                                }
                            }
                        }
                        else {
                            System.out.println("The program cannot access the list of existing users at this time. Please try again!");
                        }
                        break;
                    case 3:
                        System.out.println("Exiting ...");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid Input. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please try again.\n");
                scnr.nextLine();                        // *****Consume the invalid input to prevent infinite loop
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
