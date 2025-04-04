import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class LenderService {                                           // SELF NOTE: Might want to create an interface which UserService and LenderService can implement
    private static ArrayList<Lender> listOfLenders = new ArrayList<>();
    private static boolean lendersLoaded = false;
    private static boolean loanRequestsLoaded = false;

    private static LinkedList loanRequests = null;


    public static boolean loadLenders() {
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("LenderDirectory.csv");
        } catch (FileNotFoundException e) {
            return false;
        }

        Scanner reader = new Scanner(fis);
        reader.nextLine();

        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            String category = line[0].toLowerCase().trim();

            try {
                switch (category) {
                    case "personal":
                        PersonalLoanLender p1 = new PersonalLoanLender(line);
                        listOfLenders.add(p1);
                        break;
                    case "home":
                        HomeLoanLender h1 = new HomeLoanLender(line);
                        listOfLenders.add(h1);
                        break;
                    case "business":
                        BusinessLoanLender bl = new BusinessLoanLender(line);
                        listOfLenders.add(bl);
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        reader.close();
        SearchAndSort.recursiveBubbleSort(listOfLenders, listOfLenders.size());                 // Keeps the list of the lenders sorted for binary search later

        return !listOfLenders.isEmpty();
    }

    public static boolean registerNewLender (Scanner scnr) throws Exception {
        String[] fields = {"Category", "Name of the Lender", "MIN APR", "MAX APR", "MAX Loan Term", "MIN Credit Score", "Time to Fund (Days)", "MIN Loan Amount", "MAX Loan Amount", "Origination Fee(%)", "Official BBB Rating", "States Of Operation", "MIN Down Payment(%)"};
        String[] responses = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i] + ": ");
            responses[i] = scnr.nextLine().toLowerCase();
        }

        switch (responses[0]) {
            case "personal":
                PersonalLoanLender p1 = new PersonalLoanLender(responses);
                listOfLenders.add(p1);
                break;
            case "home":
                HomeLoanLender h1 = new HomeLoanLender(responses);
                listOfLenders.add(h1);
                break;
            case "business":
                BusinessLoanLender bl = new BusinessLoanLender(responses);
                listOfLenders.add(bl);
                break;
            default:
                throw new Exception ("Invalid Category entered");
        }

        return writeLenderToFile(responses);
    }

    public static Lender handleLogin (Scanner scnr) throws Exception {
        if (!lendersLoaded) {
            lendersLoaded = loadLenders();
            if (!lendersLoaded) {
                throw new Exception ("The program cannot access the Lenders Directory at this time. Please try again!");
            }
        }

        // ------------------------------------------------
        // WILL WORK ON FINDING A MORE SECURE WAY FOR LENDERS LOGIN IN NEXT MILESTONES
        // ------------------------------------------------
        System.out.println("------------------------------------");
        System.out.print("Enter the Category Name: ");
        String category = scnr.nextLine().toLowerCase();
        System.out.print("Enter the Name of the Lender: ");
        String lenderName = scnr.nextLine().toLowerCase();

        Lender lender = searchLender(lenderName);

        if (lender == null || !lender.matchLogin(category, lenderName)) {
            System.out.println("Lender does not exist");
        }
        else {
            return lender;
        }

        return handleLogin(scnr);
    }

    private static boolean writeLenderToFile (String[] responses) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("LenderDirectory.csv", true);
        } catch (FileNotFoundException e) {
            System.out.println("The program cannot register new lenders at this time. Please try again later!");
            return false;
        }
        PrintWriter writer = new PrintWriter(fos);
        for (String response : responses) {
            writer.print(response + ",");
        }
        writer.println();
        writer.flush();
        writer.close();

        return true;
    }

    public static Lender searchLender (String lenderName) {
        int ind = SearchAndSort.recursiveBinarySearch(listOfLenders, lenderName, 0, listOfLenders.size() - 1);
        return (ind != -1) ? listOfLenders.get(ind) : null;
    }

    public static boolean loadLoanRequests() {
        FileInputStream fis;
        try {
            fis = new FileInputStream("loan_applications.csv");
        } catch (FileNotFoundException e) {
            System.out.println("No loan application file found.");
            return false;
        }

        Scanner reader = new Scanner(fis);

        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(",");

            try {
                String fullName = data[0];
                String businessName = data[1];
                String businessType = data[2];
                double loanAmount = Double.parseDouble(data[3]);
                String loanPurpose = data[4];
                LocalDate currentDate = LocalDate.parse(data[5]);
                int yearsInOperation = Integer.parseInt(data[6]);
                double annualRevenue = Double.parseDouble(data[7]);
                double netProfit = Double.parseDouble(data[8]);
                double avgMonthlySales = Double.parseDouble(data[9]);

                Loan loan = new Loan(
                        fullName, businessName, businessType, loanAmount,
                        loanPurpose, currentDate, yearsInOperation,
                        annualRevenue, netProfit, avgMonthlySales
                );

                if (loanRequests == null) {
                    loanRequests = new LinkedList(loan);
                }
                else {
                    loanRequests.append(loan);
                }

            } catch (Exception e) {
                System.out.println("Error parsing loan entry: " + e.getMessage());
            }
        }

        reader.close();
        return true;
    }

    public static void postLogin(Scanner scnr) {
        boolean isRunning = true;

        if (!loadLoanRequests()) {
            System.out.println("No loan applications found.");
            return;
        }

        while (isRunning) {
            System.out.println("========= Lender Dashboard =========\n" +
                    "1. View Loan Applications\n" +
                    "2. Logout\n" +
                    "------------------------------------");

            int choice;
            try {
                choice = scnr.nextInt();
                scnr.nextLine(); // consume leftover newline
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scnr.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    if (loanRequests == null || loanRequests.isEmpty()) {
                        System.out.println("There are no loan applications at the moment.");
                    } else {
                        System.out.println("------ Loan Applications ------");
                        int index = 1;
                        Node current = loanRequests.head;

                        while (current != null) {
                            Loan loan = current.obj;
                            System.out.println("Application #" + index++);
                            System.out.println("Name: " + loan.getFullName());
                            System.out.println("Business: " + loan.getBusinessName() + " (" + loan.getBusinessType() + ")");
                            System.out.println("Requested Amount: $" + loan.getLoanAmount());
                            System.out.println("Purpose: " + loan.getLoanPurpose());
                            System.out.println("Submitted on: " + loan.getCurrentDate());
                            System.out.println("Years in Operation: " + loan.getYearsInOperation());
                            System.out.println("Annual Revenue: $" + loan.getAnnualRevenue());
                            System.out.println("Net Profit: $" + loan.getNetProfit());
                            System.out.println("Average Monthly Sales: $" + loan.getAvgMonthlySales());
                            System.out.println("-----------------------------------");

                            current = current.next;
                        }
                    }
                    break;

                case 2:
                    System.out.println("Logging out ...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
}