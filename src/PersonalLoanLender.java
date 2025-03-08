public class PersonalLoanLender extends Lender {
    private int avgTimeToFund;
    private int originationFee;


    public PersonalLoanLender(String[] data) {
        super("Personal", data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]),
                Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), data[10]);
        avgTimeToFund = Integer.parseInt(data[6]);
        originationFee = Integer.parseInt(data[9]);
    }

    public int getOriginationFee () { return originationFee; }

    public int getAvgTimeToFund() { return avgTimeToFund; }
}
