import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class S_LenderService {
    private static ArrayList<Lender> listOfLenders = new ArrayList<>();
    private static boolean lendersLoaded = false;

    private static U_LinkedList loanRequests = null;
    private static boolean loanRequestsLoaded = false;

    private static U_Queue<Loan> approvalQueue = new U_Queue<>();


    public static boolean loadLenders() {
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("../Directories/LenderDirectory.csv");
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
        U_SearchAndSort.mergeSort(listOfLenders, new ArrayList<Lender>(), 0, listOfLenders.size() - 1);                 // Keeps the list of the lenders sorted for binary search later

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
            fos = new FileOutputStream("../Directories/LenderDirectory.csv", true);
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
        int ind = U_SearchAndSort.recursiveBinarySearch(listOfLenders, lenderName, 0, listOfLenders.size() - 1);
        return (ind != -1) ? listOfLenders.get(ind) : null;
    }

    public static boolean loadLoanRequests() {
        FileInputStream fis;
        try {
            fis = new FileInputStream("../Directories/loanApplications.csv");
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
                String status = data[10];
                String applicationID = data[11];

                Loan loan = new Loan(
                        fullName, businessName, businessType, loanAmount,
                        loanPurpose, currentDate, yearsInOperation,
                        annualRevenue, netProfit, avgMonthlySales, status, applicationID
                );

                if (loanRequests == null) {
                    loanRequests = new U_LinkedList(loan);
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

        if (!loanRequestsLoaded) {
            loanRequestsLoaded = loadLoanRequests();
            if (!loanRequestsLoaded) {
                System.out.println("No loan applications found.");
                return;
            }
        }

        while (isRunning) {
            System.out.println("========= Lender Dashboard =========\n" +
                    "1. View Loan Applications\n" +
                    "2. Approve Loan\n" +
                    "3. Show Loans Requests based on Categories\n" +
                    "4. Logout\n" +
                    "------------------------------------");

            int choice;
            try {
                choice = scnr.nextInt();
                scnr.nextLine();                 // consume leftover newline
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
                        approvalQueue = new U_Queue<>();

                        int index = 1;
                        U_LinkedListNode curr = loanRequests.head;

                        while (curr != null) {
                            Loan loan = curr.obj;
                            System.out.println("Application #" + index++);
                            System.out.println(loan.toFormattedString());
                            approvalQueue.enqueue(loan);
                            curr = curr.next;
                        }
                    }
                    break;

                case 2:
                    System.out.println("Approve by:");
                    System.out.println(" 1) Application number in the list");
                    System.out.println(" 2) Application ID (UUID)");
                    System.out.print("Choose option (1 or 2): ");
                    int mode = scnr.nextInt();
                    scnr.nextLine();

                    Loan approved = null;
                    if (mode == 1) {
                        // by position in the last–printed approvalQueue
                        if (approvalQueue == null || approvalQueue.isEmpty()) {
                            System.out.println("Please view the applications first (option 1 or 3).");
                            break;
                        }
                        System.out.print("Enter application #: ");
                        int target = scnr.nextInt();
                        scnr.nextLine();
                        if (target < 1 || target > approvalQueue.getSize()) {
                            System.out.println("Invalid application number.");
                            break;
                        }
                        approved = approveLoan(target);
                    }
                    else if (mode == 2) {
                        // by applicationID
                        System.out.print("Enter Application ID: ");
                        String targetId = scnr.nextLine().trim();
                        approved = approveLoanByID(targetId);
                    }
                    else {
                        System.out.println("Invalid choice.");
                        break;
                    }

                    if (approved == null) {
                        System.out.println("No such application.");
                    } else {
                        updateUserRecord(approved);
                        System.out.println("Approved: " + approved.getApplicationID());
                    }

                    // clear temp queue so next view rebuilds it
                    approvalQueue = new U_Queue<>();
                    break;

                case 3:
                    // Ask which category the lender wants to see
                    System.out.print("Enter a business category to filter by: ");
                    String filter = scnr.nextLine().trim().toLowerCase();

                    if (loanRequests == null || loanRequests.isEmpty()) {
                        System.out.println("There are no loan applications at the moment.");
                        break;
                    }

                    System.out.println("---- Applications in category: " + filter + " ----");
                    // Reset the temp‐queue to only hold the filtered apps
                    approvalQueue = new U_Queue<>();

                    int idx = 1;
                    U_LinkedListNode curr3 = loanRequests.head;
                    while (curr3 != null) {
                        Loan loan3 = curr3.obj;
                        if (loan3.getBusinessType().equalsIgnoreCase(filter)) {
                            System.out.println("Application #" + idx++);
                            System.out.println(loan3.toFormattedString());
                            approvalQueue.enqueue(loan3);
                        }
                        curr3 = curr3.next;
                    }

                    if (idx == 1) {
                        System.out.println("No applications found for category \"" + filter + "\".");
                    }
                    // Now approvalQueue holds only the apps just printed
                    break;

                case 4:
                    System.out.println("Logging out ...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    public static Loan approveLoan(int appNum) {
        if (appNum < 0 || appNum >= listOfLenders.size()) { return null; }

        U_LinkedListNode curr = loanRequests.head;
        int counter = 1;
        while (curr.next != null && counter <= appNum) {
            curr = curr.next;
            counter++;
        }

        if (curr.next == null && counter < appNum) { return null; }

        Loan l = curr.obj;
        l.approve();

        loanRequests.remove(curr);
        updateFile(l.getFullName(), l.getApplicationID());

        return l;
    }

    public static void updateFile(String fullName, String applicationID) {
        FileOutputStream fos = null;
        PrintWriter writer = null;
        try {
            fos = new FileOutputStream("../Directories/loanApplications.csv");
            writer = new PrintWriter(fos);
            loanRequests.writeToPrintWriter(writer);

            writer.flush();

            FileInputStream fis = new FileInputStream("../Users/" + fullName + ".csv");
            Scanner reader = new Scanner(fis);

            ArrayList<String[]> temp = new ArrayList<String[]>();

            while (reader.hasNextLine()) {
                String[] line = reader.nextLine().split(",");
                if (line[11].equalsIgnoreCase(applicationID)) {
                    line[10] = "Approved";
                }

                temp.add(line);
            }
            reader.close();

            fos = new FileOutputStream("../Users/" + fullName + ".csv");
            writer = new PrintWriter(fos);
            for (String[] s : temp) {
                for (String str : s) {
                    writer.print(str + ",");
                }
                writer.println();
                writer.flush();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Loan Directory not found.");
        }
    }

    public static boolean updateUserRecord(Loan l) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("../Directories/PersonalBankingUsers.txt");
        } catch (FileNotFoundException e) {
            return false;
        }
        Scanner reader = new Scanner(fis);

        ArrayList<String[]> temp = new ArrayList<String[]>();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            if ((line[0] + line[1]).equalsIgnoreCase(l.getFullName())) {
                line[12] = " " + (Double.parseDouble(line[12]) + l.getLoanAmount());
            }
            temp.add(line);
        }

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream("../Directories/PersonalBankingUsers.txt");
        } catch (FileNotFoundException e) {
            return false;
        }
        PrintWriter writer = new PrintWriter(fos);

        for (String[] s : temp) {
            for(int i = 0; i < s.length; i++) {
                if (i == s.length - 1) {
                    writer.println(s[i]);
                }
                else {
                    writer.print(s[i] + ",");

                }
            }
            writer.flush();
        }
        writer.close();

        return true;
    }

    public static Loan approveLoanByID(String applicationID) {
        if (loanRequests == null || loanRequests.isEmpty()) return null;

        U_LinkedListNode curr = loanRequests.head, prev = null;
        while (curr != null && !curr.obj.getApplicationID().equals(applicationID)) {
            prev = curr;
            curr = curr.next;
        }
        if (curr == null) return null;

        curr.obj.approve();
        if (prev == null) {
            loanRequests.head = curr.next;
        } else {
            prev.next = curr.next;
        }
        updateFile(curr.obj.getFullName(), applicationID);
        return curr.obj;
    }
}