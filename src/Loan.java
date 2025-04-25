import java.time.LocalDate;
import java.util.UUID;


public class Loan {
    // To be completed in the next milestones
    private String fullName;
    private String businessName;
    private String businessType;
    private double loanAmount;
    private String loanPurpose;

    private LocalDate currentDate;
    private int yearsInOperation;
    private double annualRevenue;
    private double netProfit;
    private double avgMonthlySales;

    private String status;
    private String applicationID;

    public Loan (String fullName, String businessName, String businessType, double loanAmount, String loanPurpose,
                 LocalDate currentDate, int yearsInOperation, double annualRevenue, double netProfit, double avgMonthlySales, String status, String applicationID) {
        this.fullName = fullName;
        this.businessName = businessName;
        this.businessType = businessType;
        this.loanAmount = loanAmount;
        this.loanPurpose = loanPurpose;
        this.currentDate = currentDate;
        this.yearsInOperation = yearsInOperation;
        this.annualRevenue = annualRevenue;
        this.netProfit = netProfit;
        this.avgMonthlySales = avgMonthlySales;
        this.status = status;

        if (applicationID == null) {
            this.applicationID = generateApplicationID();
        }
        else {
            this.applicationID = applicationID;
        }
    }

    public Loan (String[] data) {
        this.fullName = data[0];
        this.businessName = data[1];
        this.businessType = data[2];
        this.loanAmount = Double.parseDouble(data[3]);
        this.loanPurpose = data[4];
        this.currentDate = LocalDate.parse(data[5]);
        this.yearsInOperation = Integer.parseInt(data[6]);
        this.annualRevenue = Double.parseDouble(data[7]);
        this.netProfit = Double.parseDouble(data[8]);
        this.avgMonthlySales = Double.parseDouble(data[9]);
        this.status = data[10];
        this.applicationID = data[11];
    }

    public String getFullName() { return this.fullName; }

    public String getBusinessName() { return this.businessName; }

    public String getBusinessType() { return this.businessType; }

    public double getLoanAmount() { return this.loanAmount; }

    public String getLoanPurpose() { return this.loanPurpose; }

    public LocalDate getCurrentDate() { return this.currentDate; }

    public int getYearsInOperation() { return this.yearsInOperation; }

    public double getAnnualRevenue() { return this.annualRevenue; }

    public double getNetProfit() { return this.netProfit; }

    public double getAvgMonthlySales() { return this.avgMonthlySales; }

    public String getApplicationID() { return this.applicationID; }

    public void approve() { this.status = "Approved"; }

    public void reject() { this.status = "Rejected"; }

    private String generateApplicationID () {
        return UUID.randomUUID().toString();
    }

    public String toFormattedString() {
        String output = "Full Name: " + this.fullName + "\n" +
                "Business: " + this.getBusinessName() + " (" + this.getBusinessType() + ")\n" +
                "Requested Amount: $" + this.getLoanAmount() + "\n" +
                "Purpose: " + this.getLoanPurpose() + "\n" +
                "Submitted on: " + this.getCurrentDate() + "\n" +
                "Years in Operation: " + this.getYearsInOperation() + "\n" +
                "Annual Revenue: $" + this.getAnnualRevenue() + "\n" +
                "Net Profit: $" + this.getNetProfit() + "\n" +
                "Average Monthly Sales: $" + this.getAvgMonthlySales() + "\n" +
                "Status: " + this.status +"\n" +
                "Application ID: " + this.applicationID + "\n";
        return output;

    }

    @Override
    public String toString() {
        String output = this.fullName + "," +
                    businessName + "," +
                    businessType + "," +
                    loanAmount + "," +
                    loanPurpose + "," +
                    currentDate + "," +
                    yearsInOperation + "," +
                    annualRevenue + "," +
                    netProfit + "," +
                    avgMonthlySales + "," +
                    this.status + "," +
                    this.applicationID;
        return output;

    }
}
