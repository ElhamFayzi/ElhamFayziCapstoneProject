public class C_LenderNameComparator implements C_UtilNameComparator<Lender> {
    @Override
    public int compare(Lender l1, Lender l2) {
        return l1.getName().compareToIgnoreCase(l2.getName());
    }
}