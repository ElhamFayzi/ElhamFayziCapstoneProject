// LenderNameComparator.java
public class LenderNameComparator implements NameComparator<Lender> {
    @Override
    public int compare(Lender l1, Lender l2) {
        return l1.getName().compareToIgnoreCase(l2.getName());
    }
}