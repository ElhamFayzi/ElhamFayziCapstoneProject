import java.util.Comparator;

public class LenderNameComparator implements Comparator<Loan> {
    @Override
    public int compare(Loan l1, Loan l2) {
        return l1.getFullName().compareTo(l2.getFullName());
    }
}
