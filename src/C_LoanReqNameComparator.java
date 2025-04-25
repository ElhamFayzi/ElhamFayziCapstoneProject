// LenderNameComparator.java
public class C_LoanReqNameComparator implements C_UtilNameComparator<Loan> {
    @Override
    public int compare(Loan a, Loan b) {
        return a.getFullName().compareToIgnoreCase(b.getFullName());
    }
}