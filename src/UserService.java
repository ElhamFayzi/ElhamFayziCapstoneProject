import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private static ArrayList<User> listOfUsers = new ArrayList<User>();

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

    public static boolean writeUserToFile (String[] responses) {
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
}
