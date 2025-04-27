public class Lender {
    private String category;
    private String name;

    private double minAPR;
    private double maxAPR;
    private int maxLoanTermMonths;
    private int minCreditScore;
    private int minLoanAmount;
    private int maxLoanAmount;
    private String BBBRating;

    public Lender (String category, String name, double minAPR, double maxAPR, int maxLoanTermMonths, int minCreditScore,
                   int minLoanAmount, int maxLoanAmount, String BBBRating) {
        this.category = category;
        this.name = name;
        this.minAPR = minAPR;
        this.maxAPR = maxAPR;
        this.minCreditScore = minCreditScore;
        this.minLoanAmount = minLoanAmount;
        this.maxLoanAmount = maxLoanAmount;
        this.maxLoanTermMonths = maxLoanTermMonths;
        this.BBBRating = BBBRating;
    }

    // Constructor for the subclass HomeLoanLenders
    public Lender (String category, String name, int minCreditScore, String BBBRating) {
        this.category = category;
        this.name = name;
        this.minCreditScore = minCreditScore;
        this.BBBRating = BBBRating;
    }

    public String getCategory() { return category; }

    public String getName() { return name; }

    public double getMinAPI() { return minAPR; }

    public double getMaxAPI() { return maxAPR; }

    public int getMinCreditScore() { return minCreditScore; }

    public int getMinLoanAmount() { return minLoanAmount; }

    public int getMaxLoanAmount() { return maxLoanAmount; }

    public int getMaxLoanTermMonths() { return maxLoanTermMonths; }

    public String getBBBRating() { return BBBRating; }

    public boolean matchLogin (String category, String name) {
        return this.category.equalsIgnoreCase(category) && this.name.equalsIgnoreCase(name);
    }

}


