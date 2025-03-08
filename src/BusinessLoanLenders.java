public class BusinessLoanLenders extends Lender{
    public BusinessLoanLenders(String[] data) {
        super("Business", data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]),
                Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), data[10]);
    }
}
