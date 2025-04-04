import java.time.LocalDate;
import java.util.LinkedList;

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

    public Loan (String fullName, String businessName, String businessType, double loanAmount, String loanPurpose,
                 LocalDate currentDate, int yearsInOperation, double annualRevenue, double netProfit, double avgMonthlySales) {
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
}
