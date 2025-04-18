public class HomeLoanLender extends Lender {
    private int numStateOfOperation;
    private int minDownPayment;

    public HomeLoanLender(String[] data) {
        super("home", data[1], Integer.parseInt(data[5]), data[10]);
        numStateOfOperation = Integer.parseInt(data[11]);
        minDownPayment = Integer.parseInt(data[12]);
    }

    public int getNumStateOfOperation() { return numStateOfOperation; }

    public int getMinDownPayment() { return minDownPayment; }
}
