public class BusinessLoanLender extends Lender{
    public BusinessLoanLender(String[] data) {
        super("business", data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), Integer.parseInt(data[4]),
                Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), data[10]);
    }
}
