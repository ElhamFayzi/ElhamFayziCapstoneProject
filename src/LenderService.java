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

    private static LinkedList loanRequests = null;                                  // **** CAN MAKE THIS A QUEUE
    private static boolean loanRequestsLoaded = false;

    private static Queue<Loan> approvalQueue = new Queue<>();


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
        SearchAndSort.mergeSort(listOfLenders, new ArrayList<Lender>(), 0, listOfLenders.size() - 1);                 // Keeps the list of the lenders sorted for binary search later

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
        int ind = SearchAndSort.recursiveBinarySearch(listOfLenders, lenderName, 0, listOfLenders.size() - 1);
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
                        approvalQueue = new Queue<>();

                        int index = 1;
                        Node curr = loanRequests.head;

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
                    System.out.print("Enter application #: ");
                    int target = scnr.nextInt();
                    scnr.nextLine();

                    // 1) Quick sanity check
                    if (approvalQueue == null || approvalQueue.isEmpty()) {
                        System.out.println("Please view the applications first (option 1).");
                        break;
                    }
                    if (target < 1 || target > approvalQueue.getSize()) {
                        System.out.println("Invalid application number.");
                        break;
                    }

                    // 2) Delegate removal + file updates to your existing method:
                    //    approveLoan(target) will remove from loanRequests and rewrite loanApplications.csv
                    Loan approved = approveLoan(target);
                    if (approved == null) {
                        System.out.println("Application #" + target + " could not be approved.");
                    } else {
                        // 3) Update the user’s record (adds the loan amount to their balance)
                        updateUserRecord(approved);
                        System.out.println("Application #" + target + " approved.");
                    }

                    // 4) Clear out the temp queue so next “view” rebuilds it fresh
                    approvalQueue = new Queue<>();
                    break;

                case 3:
                    //FIXME!!! COMPLETE THIS
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

        Node curr = loanRequests.head;
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

//    public static boolean rejectLoan(int appNum) {
//        if (appNum < 0 || appNum >= listOfLenders.size()) { return false; }
//
//        Node curr = loanRequests.head;
//        int counter = 1;
//        while (curr.next != null && counter <= appNum) {
//            curr = curr.next;
//        }
//
//        Loan l = curr.obj;
//        l.reject();
//
//        loanRequests.remove(curr);
//        updateFile();                                 //FIXME!!! Change the method signature and whether to write "Approved" or "Rejected" in the file if u wanted to use this commented snippet
//
//        return true;
//    }

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
}