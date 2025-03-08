import java.util.ArrayList;

public class SearchAndSort {
    public static void recursiveBubbleSort(ArrayList<Lender> lenders, int n) {
        if (n == 1) return;

        ComparatorsUtil comparator = new ComparatorsUtil();
        boolean swapped = false;

        for (int i = 0 ; i < n - 1; i++) {
            if (comparator.compare(lenders.get(i), lenders.get(i + 1)) > 0) {
                swap(lenders, i, i + 1);
                swapped = true;
            }
        }

        if (!swapped) return;

        recursiveBubbleSort(lenders, n - 1);
    }

    public static void swap (ArrayList<Lender> lenders, int ind1, int ind2) {
        Lender temp = lenders.get(ind1);
        lenders.set(ind1, lenders.get(ind2));
        lenders.set(ind2, temp);
    }

    public static int recursiveBinarySearch(ArrayList<Lender> lenders, String target, int left, int right) {
        if (right < left) return -1;

        int m = left + (right - left) / 2;

        if (lenders.get(m).getName().equalsIgnoreCase(target)) return m;
        else if (lenders.get(m).getName().compareToIgnoreCase(target) < 0) return recursiveBinarySearch(lenders, target, m + 1, right);
        else return recursiveBinarySearch(lenders, target, left, m - 1);
    }

    // ----------------------------
    // SELF NOTES: CAN ADD A RECURSIVE LINEAR SEARCH, TOO, IF NEEDED TO FIND AN ENTRY BASED ON DIFFERENT ASPECT THAN NAME
    // ----------------------------
}
