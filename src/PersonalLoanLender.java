public class PersonalLoanLender extends Lender {
    private int avgTimeToFund;
    private double originationFee;


    public PersonalLoanLender(String[] data) {
        super("personal", data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), Integer.parseInt(data[4]),
                Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), data[10]);
        avgTimeToFund = Integer.parseInt(data[6]);
        originationFee = Integer.parseInt(data[9]);
    }

    public double getOriginationFee () { return originationFee; }

    public int getAvgTimeToFund() { return avgTimeToFund; }
}
