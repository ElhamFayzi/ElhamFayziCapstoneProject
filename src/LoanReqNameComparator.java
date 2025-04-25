// LenderNameComparator.java
public class LoanReqNameComparator implements NameComparator<Loan> {
    @Override
    public int compare(Loan a, Loan b) {
        return a.getFullName().compareToIgnoreCase(b.getFullName());
    }
}