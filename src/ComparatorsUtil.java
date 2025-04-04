import java.util.Comparator;

public class ComparatorsUtil implements Comparator<Lender> {
    @Override
    public int compare(Lender l1, Lender l2) {
        return l1.getName().compareTo(l2.getName());
    }
}

