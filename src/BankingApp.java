import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApp {
    public static void printPersonalBanking () {
        System.out.println("===========Banking System===========");
        System.out.println("1. Register a new User");
        System.out.println("2. Login existing users");
        System.out.println("3. Exit");
        System.out.println("------------------------------------");
    }

    public static boolean openPersonalBanking(Scanner scnr) {
        boolean isRunning = true;

        while (isRunning) {
            printPersonalBanking();
            int choice;

            try {
                choice = scnr.nextInt();
                scnr.nextLine();            // Flush the newline character left in the previous line
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please try again.\n");
                scnr.nextLine();                        // *****Consume the invalid input to prevent infinite loop
                continue;
            }

            switch (choice){
                case 1:
                    boolean registered = UserService.registerNewUser(scnr);
                    if (registered) {
                        System.out.println("New User has been successfully registered!");
                    }
                    else {
                        System.out.println("The program could not register you at this time. Please try again later!");
                    }
                    break;

                case 2:                                                  // BEGIN CHECKING FORM THIS CASE ONWARD --> Checkpoint
                    try {
                        if (UserService.handleLogin(scnr)) {
                            System.out.println("You have successfully logged in!");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;                                              // This breaks out of the switch statement if the previous conditions was not true.

                case 3:
                    System.out.println("Exiting ...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid Input. Please try again.");
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
                System.out.println("Accessing Personal Banking ...");
                portalClosed = openPersonalBanking(scnr);

            }
            else if (Integer.parseInt(args[0]) == 2) {
                System.out.println("Accessing Business Banking ...");
                portalClosed = openBusinessBanking(scnr);
            }
            else {
                throw new Exception ("Invalid argument passed: Please pass '1' for Personal Banking or '2' for Business Banking.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No arguments passed: Please pass '1' for Personal Banking or '2' for Business Banking.");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please pass '1' for Personal Banking or '2' for Business Banking.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
