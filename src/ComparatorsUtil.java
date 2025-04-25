import java.util.Comparator;

public class ComparatorsUtil implements Comparator<Lender> {                   //****** MIGHT WANT TO MAKE THIS CLASS GENERIC
    @Override
    public int compare(Lender l1, Lender l2) {
        return l1.getName().compareToIgnoreCase(l2.getName());
    }
}

