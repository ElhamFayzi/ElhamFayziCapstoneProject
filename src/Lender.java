public class Lender {
    // To be completed in the next milestones
    private String category;                            // Can create some subclasses for each category, since the required values for each category are different
    private String name;

    private int minAPI;                                 // Should probably choose another name to avoid ambiguity
    private int maxAPI;
    private int minCreditScore;
    private int minLoanAmount;
    private int maxLoanAmount;
    private int maxLoanTermMonths;
    private int avgTimeToFund;

    private String BBBRating;

    public Lender (String category, String name, int minAPI, int maxAPI, int minCreditScore, int minLoanAmount,
                   int maxLoanAmount, int maxLoanTermMonths, int avgTimeToFund, String BBBRating) {
        this.category = category;
        this.name = name;
        this.minAPI = minAPI;
        this.maxAPI = maxAPI;
        this.minCreditScore = minCreditScore;
        this.minLoanAmount = minLoanAmount;
        this.maxLoanAmount = maxLoanAmount;
        this.maxLoanTermMonths = maxLoanTermMonths;
        this.avgTimeToFund = avgTimeToFund;
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

    public int getMinAPI() { return minAPI; }

    public int getMaxAPI() { return maxAPI; }

    public int getMinCreditScore() { return minCreditScore; }

    public int getMinLoanAmount() { return minLoanAmount; }

    public int getMaxLoanAmount() { return maxLoanAmount; }

    public int getMaxLoanTermMonths() { return maxLoanTermMonths; }

    public int getAvgTimeToFund() { return avgTimeToFund; }

    public String getBBBRating() { return BBBRating; }



}


