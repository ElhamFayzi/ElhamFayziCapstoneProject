import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LenderService {                                           // SELF NOTE: Might want to create an interface which UserService and LenderService can implement
    private static ArrayList<Lender> listOfLenders = new ArrayList<>();
    private static boolean lendersLoaded = false;

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

    public static boolean handleLogin (Scanner scnr) throws Exception {
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
            return true;
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
        for (Lender lender : listOfLenders) {
            if (lender.getName().equalsIgnoreCase(lenderName)) {
                return lender;
            }
        }

        return null;
    }
}