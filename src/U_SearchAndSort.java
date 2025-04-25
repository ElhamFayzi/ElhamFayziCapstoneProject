import java.util.ArrayList;

public class U_SearchAndSort {
    public static void recursiveBubbleSort(ArrayList<Lender> lenders, int n) {
        if (n == 1) return;

        C_LenderNameComparator comparator = new C_LenderNameComparator();
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

    public static void mergeSort(ArrayList<Lender> a, ArrayList<Lender> tmp, int left, int right){
        if (left == right) return;

        int middle = left + (right - left) / 2;
        mergeSort(a, tmp, left, middle);
        mergeSort(a, tmp, middle + 1, right);

        mergeSortedLists(a, tmp, left, middle, right);
    }
    public static void mergeSortedLists(ArrayList<Lender> a, ArrayList<Lender> tmp, int left, int middle, int right){
        C_LenderNameComparator comparator = new C_LenderNameComparator();
        tmp.clear();

        int i = left;
        int j = middle + 1;

        while (i <= middle && j <= right) {
            if (comparator.compare(a.get(i), a.get(j)) < 0) {
                tmp.add(a.get(i));
                i++;
            }
            else {
                tmp.add(a.get(j));
                j++;
            }
        }

        while (i <= middle) {
            tmp.add(a.get(i));
            i++;
        }

        while (j <= right) {
            tmp.add(a.get(j));
            j++;
        }

        for (int k = 0; k < tmp.size(); k++) {
            a.set(left + k, tmp.get(k));
        }
    }
    // ----------------------------
    // SELF NOTES: CAN ADD A RECURSIVE LINEAR SEARCH, TOO, IF NEEDED TO FIND AN ENTRY BASED ON DIFFERENT ASPECT THAN NAME
    // ----------------------------
}
