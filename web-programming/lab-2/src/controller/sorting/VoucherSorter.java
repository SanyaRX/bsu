package controller.sorting;

import model.TravelVoucher;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class represents sorting by particular field.
 * @autor Alexander Rai
 * @version 1.0
 */

public class VoucherSorter {

    /** Comparator for sorting */
    Comparator<TravelVoucher> comparator;


    /** Creates a VoucherSorter object
     * @param comparator - comparator to sort with
     * */
    public VoucherSorter(Comparator<TravelVoucher> comparator){
        this.comparator = comparator;
    }

    /** Sorts list of voucher
     * @param vouchers - objects to sort
     * @param ascending - is ascending
     * */
    public void sort(List<TravelVoucher> vouchers, boolean ascending) {
        Collections.sort(vouchers, ascending ? comparator : comparator.reversed());
    }
}
